package models.users.sorm

import securesocial.core.{Authenticator, IdentityId}
import models.users.AuthenticatorDb
import sorm.Instance
import com.typesafe.plugin._
import plugins.users.AuthenticatorEntity
import so.paws.db.DbPlugin
import play.api.Play.current


object SormAuthenticatorDb extends AuthenticatorDb {
  val db: Instance = use[DbPlugin[Instance]].db
  
  def save(authenticator: Authenticator): Either[Error, Unit] = {
    val identityId = db.query[IdentityId]
      .whereEqual("providerId", authenticator.identityId.providerId)
      .whereEqual("userId", authenticator.identityId.userId)
      .fetchOne().getOrElse(db.save(authenticator.identityId))

    db.save(AuthenticatorEntity(
      authenticator.id,
      identityId,
      authenticator.creationDate,
      authenticator.lastUsed,
      authenticator.expirationDate
    ))
    Right(())
  }

  def find(key: String): Either[Error, Option[Authenticator]] = {
    Right(db.query[AuthenticatorEntity].whereEqual("key", key).fetchOne().map(e =>
      Authenticator(e.key, e.identityId, e.creationDate, e.lastUsed, e.expirationDate)
    ))
  }

  def findEntity(key: String): Option[AuthenticatorEntity] = {
    db.query[AuthenticatorEntity].whereEqual("key", key).fetchOne()
  }

  def delete(key: String): Either[Error, Unit] = {
    db.query[AuthenticatorEntity].whereEqual("key", key).fetchOne().map(e => db.delete(e))
    Right(())
  }
}
