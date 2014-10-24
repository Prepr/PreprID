package models.users.slick

import securesocial.core._
import org.joda.time.DateTime
import securesocial.core.IdentityId
import securesocial.core.providers.Token
import models.users.{UserProfile, UserDb}
import so.paws.db.DbPlugin
import com.typesafe.plugin._
import sorm.{Persisted, Instance}
import play.api.Play.current


object SlickUserDb extends UserDb {
  val db: Instance = use[DbPlugin[Instance]].db

  def find(identityId: IdentityId): Option[SocialUser] = {
    db.query[SocialUser]
      .whereEqual("identityId.userId", identityId.userId)
      .whereEqual("identityId.providerId", identityId.providerId)
      .fetchOne()
  }

  def findByEmailAndProvider(email: String, providerId: String): Option[SocialUser] = {
    db.query[SocialUser]
      .whereEqual("email", Option(email))
      .whereEqual("identityId.providerId", providerId)
      .fetchOne()
  }

  def save(identity: Identity): SocialUser = {
    val socialUser: Option[SocialUser with Persisted] = db.query[SocialUser].whereEqual("email", identity.email).fetchOne()
    socialUser match {
      case None => val newUser = SocialUser(
          db.save(identity.identityId),
          identity.firstName,
          identity.lastName,
          identity.fullName,
          identity.email,
          identity.avatarUrl,
          db.save(identity.authMethod),
          identity.oAuth1Info.map(e => db.save(e)),
          identity.oAuth2Info.map(e => db.save(e)),
          identity.passwordInfo.map(e => db.save(e))
        )
        db.transaction {
          db.save(newUser)
        }
        newUser
      case Some(existingUser) =>
        val passInfo = Some(db.save(identity.passwordInfo.get))
        val updatedUser = existingUser.copy(identityId = identity.identityId, firstName = identity.firstName,
          lastName = identity.lastName, fullName = identity.fullName, email = identity.email, avatarUrl = identity.avatarUrl, authMethod = identity.authMethod,
          oAuth1Info = identity.oAuth1Info, oAuth2Info = identity.oAuth2Info, passwordInfo = passInfo)
        db.transaction {
          db.save(updatedUser)
        }
        updatedUser
    }
  }

  def save(token: Token) = {
    db.save(token)
    ()
  }

  def findToken(uuid: String): Option[Token] = {
    db.query[Token].whereEqual("uuid", uuid).fetchOne()
  }

  def deleteToken(uuid: String) {
    findToken(uuid).map(e => db.delete(e))
    ()
  }

  def deleteExpiredTokens() {
    db.query[Token].whereSmallerOrEqual("expirationTime", DateTime.now()).fetch() foreach { db.delete(_) }
  }

  def findAll:List[SocialUser] = {
    db.query[SocialUser].fetch().toList
  }

  def findEmailByTokenUuid(tokenUuid: String): Option[String] = {
    db.query[Token].whereEqual("uuid", tokenUuid).fetchOne().map(t => t.email)
  }

  override def findUserProfileByUsername(username: String): Option[UserProfile] = {
    db.query[UserProfile].whereEqual("username", username).fetchOne()
  }
}