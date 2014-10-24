package com.example.cache

import com.example.config.AppConfig
import com.thimbleware.jmemcached.{CacheImpl, Key, MemCacheDaemon, LocalCacheElement}
import com.thimbleware.jmemcached.storage.CacheStorage
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap

import java.net.InetSocketAddress

/**
 * Configures and wraps a single `MemCacheDaemon`
 * 
 * The host and port information are configured in `application.conf` under `memcached.hosts`
 * 
 * Although the value of `memcached.hosts` is a List, `MemcachedProcess` will simply retrieve
 * the first value from that List
 * 
 * `MemCacheDaemon` is provided by the [[http://code.google.com/p/jmemcache-daemon/ jmemcached]] library 
 * -- a Java implementation of the daemon (server) side of the Memcached protocol.
 */
object MemcachedProcess {
  
  private val memcachedDaemon = {
    
    val hosts = AppConfig.Memcached.hosts
    val addresses = hosts map { 
      _.split(":") match {
	    case Array(h,p) => new InetSocketAddress(h, p.toInt)
  	  }
    }

    val daemon = new MemCacheDaemon[LocalCacheElement]
    val storage: CacheStorage[Key, LocalCacheElement] = ConcurrentLinkedHashMap.create(ConcurrentLinkedHashMap.EvictionPolicy.FIFO, 100, 1000)
    daemon.setCache(new CacheImpl(storage))
    daemon.setBinary(false)
    daemon.setAddr(addresses(0))
    daemon.setIdleTime(300)
    daemon.setVerbose(true)
    daemon.start() 
    daemon
  }
   

}