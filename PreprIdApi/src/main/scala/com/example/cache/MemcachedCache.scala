package com.example.cache

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.Promise
import org.slf4j.LoggerFactory
import net.spy.memcached.AddrUtil
import net.spy.memcached.MemcachedClient
import spray.caching.Cache
import spray.routing.directives.CachingDirectives.RouteResponse
import spray.caching.ExpiringLruCache
import scala.concurrent.duration.Duration

/** 
 *  Factory for `MemcachedCache` instances.
 */
object MemcachedCache {
  
  /**
   * Creates a `MemcachedCache`
   * 
   * @param memcachedHosts The Memcached hosts.  Each entry should be in the format of `ip:port`
   * @param timeToLiveSeconds The configured time to live in seconds for each cache entry
   */
  def apply[V](memcachedHosts: List[String], timeToLiveSeconds: Int): MemcachedCache[V] = {
      new MemcachedCache[V](memcachedHosts, timeToLiveSeconds)
  }

  /**
   * Creates a `MemcachedCache` to store `RouteResponse`s
   * 
   * @param memcachedHosts The Memcached hosts.  Each entry should be in the format of `ip:port`
   * @param timeToLiveSeconds The configured time to live in seconds for each cache entry
   */
  def routeCache(memcachedHosts: List[String], timeToLiveSeconds: Int): MemcachedCache[RouteResponse] = {
      MemcachedCache[RouteResponse](memcachedHosts, timeToLiveSeconds)
  }

}

/**
 *  An implementation of `spray.caching.Cache`
 *  
 *  Uses the [[https://code.google.com/p/spymemcached/ spymemcached]] library to store
 *  items in Memcached, or other caches such as Hazelcast that work with Memcached clients
 */
final class MemcachedCache[V](memcachedHosts: List[String], timeToLiveSeconds: Int) extends Cache[V] {

  private val log = LoggerFactory.getLogger(classOf[MemcachedCache[V]])

  import scala.collection.JavaConversions._
  private val memcachedClient = new MemcachedClient(AddrUtil.getAddresses(memcachedHosts))

  def get(key: Any) = {
    memcachedClient.get(key.toString) match {
      case null => {
        log.debug("cache miss - key: {} ", key.toString)
        None
      }
      case o: Object =>{
        log.debug("cache hit - key: {}", key.toString)
        log.trace("cache hit - value: {}", o.asInstanceOf[V])
        Some(Promise.successful(o.asInstanceOf[V]).future)
      }
    }
  }

  def apply(key: Any, genValue: () => Future[V])(implicit ec: ExecutionContext): Future[V] = {
    val optObj = Option(memcachedClient.get(key.toString))
    optObj match {
      case None =>
        log.debug("cache miss - key: {} ", key.toString)
        val future = genValue()
        future.onComplete { value =>
          log.debug("add cache entry - key: {} ", key.toString)
          log.trace("add cache entry - value: {}", value.get)
          memcachedClient.add(key.toString, timeToLiveSeconds, value.get)
        }
        future
      case Some(obj) => {
        log.debug("cache hit - key: {}", key.toString)
        log.trace("cache hit - value: {}", obj.asInstanceOf[V])
        Future(obj.asInstanceOf[V])
      }
    }

  }

  def remove(key: Any) = {
    log.debug("remove cache entry - key: {}", key.toString)
    val obj = memcachedClient.get(key.toString)
    obj match {
      case null => None
      case o: Object => {
        memcachedClient.delete(key.toString)
        Some(Promise.successful(o.asInstanceOf[V]).future)
      }
    }
  }

  def clear(): Unit = memcachedClient.flush() // Be careful!  Are you sure you want to evict EVERYTHING?
		  									  // Perhaps the cache is being used by other clients that wouldn't appreciate that.

  def size = 0 // Spymemcached doesn't provide an API to provide this information.
  			   // Returning 0 just to fulfill the contract of the Cache trait
}

