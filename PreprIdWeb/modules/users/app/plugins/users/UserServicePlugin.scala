package plugins.users

import play.api.Application
import securesocial.core.providers.Token
import securesocial.core.{IdentityId, Identity}
import models.users.UserDb


abstract class UserServicePlugin(application: Application) extends securesocial.core.UserServicePlugin(application) {
  val userDb: UserDb

  def find(id: IdentityId): Option[Identity] = userDb.find(id)

  def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = userDb.findByEmailAndProvider(email, providerId)

  def save(identity: Identity): Identity = userDb.save(identity: Identity)

  def save(token: Token): Unit = userDb.save(token)

  def findToken(uuid: String): Option[Token] = userDb.findToken(uuid)

  def deleteToken(uuid: String): Unit = userDb.deleteToken(uuid)

  def deleteExpiredTokens(): Unit = userDb.deleteExpiredTokens()
}
