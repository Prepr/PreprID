package com.example.database

import com.example.config.AppConfig
import com.example.dto.Profile
import com.mchange.v2.c3p0.ComboPooledDataSource

import scala.slick.driver.H2Driver.simple._

/**
 * Creates the H2 database and pre-loads the PROFILES table with a couple of `Profile`s
 */
object ProfilesDatabase {

  val Profiles = TableQuery[Profiles]
  
  private val cpds = new ComboPooledDataSource

  private val db: Database = {
    val ds = new ComboPooledDataSource
    ds.setDriverClass(AppConfig.JDBC.host)
    ds.setJdbcUrl(AppConfig.JDBC.driver)
    Database.forDataSource(ds)
  }
  
  implicit val session = db.createSession();

  Profiles.ddl.create
  Profiles += (Profile(Some(1), "Jon", "Doe", "jj@gmail.com"))
  Profiles += (Profile(Some(2), "Jane", "Doe", "jd@gmail.com"))    

}

/**
 * Defines the PROFILES table
 */
class Profiles(tag: Tag) extends Table[Profile](tag, "PROFILES") {
  def id = column[Option[Int]]("User_ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("First_Name__ID")
  def lastName = column[String]("Last_Name_ID")
  def email = column[String]("Email_ID")

  def * = (id, firstName, lastName, email).<>(Profile.tupled, Profile.unapply _)
}
