package plugins.users.sorm

import play.api.Application
import plugins.users.AuthenticatorStorePlugin
import models.users.sorm.SormAuthenticatorDb

/**
 * Created by honaink on 1/20/14.
 */
class SormAuthenticatorStorePlugin(app: Application) extends AuthenticatorStorePlugin(app) {
  val authenticatorDb = SormAuthenticatorDb
}
