package controllers.users.sorm

import models.users.sorm.SormUserDb
import controllers.users.Users

/**
 * Created by honaink on 1/19/14.
 */
object SormUsers extends Users {
  val userDb = SormUserDb
}
