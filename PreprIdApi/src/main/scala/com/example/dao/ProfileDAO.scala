package com.example.dao

import com.example.dto.Profile
import com.example.exceptions.ProfileNotFoundException
import com.example.database.ProfilesDatabase._
import scala.slick.driver.H2Driver.simple._
import grizzled.slf4j.Logger

/**
 * Provides the API for managing `Profiles` in the database
 */
class ProfileDAO

object ProfileDAO {
  private val logger = Logger(classOf[ProfileDAO])

  /**
   * Get all `Profile`s from the data store
   * @return A List of all `Profile`s currently in the data store
   */
  def getProfiles: List[Profile] = {
    logger.debug("Getting all Profiles from the database")
    Profiles.list
  }
      
  /**
   * Queries a single `Profile`
   * 
   * @param id The id of the `Profile` to query (i.e. id == profile.id)
   * @return An `Option[Profile]` based on the supplied `id` argument
   */  
  def getProfile(id: Int): Option[Profile] = {
    logger.debug(s"Getting Profile with id [$id] from the database")
    Profiles.filter(_.id === id).firstOption
  }
    
  /**
   * Creates and stores a new `Profile`.  The `Profile` id is auto-generated for the client
   * @param p The `Profile` to store
   */
  def insertProfile(p: Profile) = {
    val profileId = (Profiles returning Profiles.map(_.id)) += Profile(None, p.firstName, p.lastName, p.email)
    logger.debug(s"Inserted Profile with id [${profileId.getOrElse(None)}] into the database")
    p.copy(id = profileId)
  }
    
  /**
   * Updates a single `Profile` in the data store
   * 
   * @param id The `id` of the `Profile` to update
   * @param p  Contains the updated view of the `Profile`
   * @throws ProfileNotFoundException if the supplied `id` argument refers to a nonexistent `Profile`
   */  
  def updateProfile(id: Int, p: Profile) = {  
    val profilesToUpdate = Profiles.filter(_.id === id)
    val qtyUpdated = profilesToUpdate map(r => (r.firstName, r.lastName, r.email)) update (p.firstName, p.lastName, p.email)
    if(qtyUpdated == 0) throw ProfileNotFoundException(id)
    logger.debug(s"Updated Profile with id [$id] in the database")
  }
    
  /**
   * Deletes a single `Profile` in the data store
   * 
   * @param id The `id` of the `Profile` to delete
   * @throws ProfileNotFoundException if the supplied `id` argument refers to a nonexistent `Profile`
   */    
  def deleteProfile(id: Int) = {
    val qtyDeleted = Profiles.filter(_.id === id).delete
    if(qtyDeleted == 0) throw ProfileNotFoundException(id)
   logger.debug(s"Deleted Profile with id [$id] from the database")
  }
    
}

