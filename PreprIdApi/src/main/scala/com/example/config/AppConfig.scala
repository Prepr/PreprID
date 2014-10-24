package com.example.config

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

/**
 * Loads the `application.conf` and creates the `Memcached` and `JDBC` objects,
 * each of which contain configuration information utilized throughout the application
 */
object AppConfig {

  private val config = ConfigFactory.load()

  /**
   * Contains configuration information used by Memcached and the spymemcached library
   */
  object Memcached {
    private val memcachedConfig = config.getConfig("memcached")
    lazy val hosts = memcachedConfig.getStringList("hosts").asScala.toList
    lazy val timeToLive = memcachedConfig.getInt("timeToLive") 

  }

  /**
   * Contains configuration information for connecting to the database
   */  
  object JDBC {
    private val jdbcConfig = config.getConfig("jdbc")
    lazy val host = jdbcConfig.getString("host")
    lazy val driver = jdbcConfig.getString("driver")
  }

}
