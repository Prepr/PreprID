package plugins.users.sorm

import plugins.users.UserServicePlugin

import play.api.Application
import models.users.sorm.SormUserDb

/**
 * Created by honaink on 1/19/14.
 */
class SormUserServicePlugin(application: Application) extends UserServicePlugin(application) {
  val userDb = SormUserDb
}
