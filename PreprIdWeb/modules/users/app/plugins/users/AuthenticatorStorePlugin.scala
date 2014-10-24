package plugins.users

import play.api.Application
import securesocial.core.{IdentityId, Authenticator, AuthenticatorStore}
import org.joda.time.DateTime
import models.users.AuthenticatorDb

case class AuthenticatorEntity(key: String, identityId: IdentityId, creationDate: DateTime,
                               lastUsed: DateTime, expirationDate: DateTime)

abstract class AuthenticatorStorePlugin(app: Application) extends AuthenticatorStore(app) {
  val authenticatorDb: AuthenticatorDb

  def save(authenticator: Authenticator): Either[Error, Unit] = authenticatorDb.save(authenticator)

  def find(key: String): Either[Error, Option[Authenticator]] = authenticatorDb.find(key)

  def findEntity(key: String): Option[AuthenticatorEntity] = authenticatorDb.findEntity(key)

  def delete(key: String): Either[Error, Unit] = authenticatorDb.delete(key)
}
