package com.example.boot

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.example.cache.{HazelcastProcess, MemcachedProcess}
import com.example.database.ProfilesDatabase
import com.example.rest.ProfileActor
import org.preprid.api.rest.PreprIdActor
import spray.can.Http

/**
 * Creates the `MemcachedProcess` (or optionally `HazelcastProcess`), `ProfilesDatabase`,
 * and the `ActorSystem` used by the spray-can HTTP server.
 *  
 * To use Hazelcast instead of jmemcached (an JVM-based implementation of Memcached), 
 * simply uncomment `HazelcastProcess` and comment out `MemcachedProcess` like so:
 * 
 * {{{
 *  HazelcastProcess
 *  //MemcachedProcess
 * }}}
 * 
 * @see [[com.example.cache.MemcachedProcess]], [[com.example.cache.HazelcastProcess]], [[com.example.database.ProfilesDatabase]]
 */
object Boot extends App {

  //HazelcastProcess 
  //MemcachedProcess
  //ProfilesDatabase

  implicit val system = ActorSystem("spray-can")
  val service = system.actorOf(Props[PreprIdActor], "PreprId-service")

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)

}
