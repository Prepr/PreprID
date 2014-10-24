package models.users

import securesocial.core.{Identity, SocialUser, IdentityId}
import securesocial.core.providers.Token

case class Authority(identityId: IdentityId, name: String)

case class UserProfile(identityId: IdentityId, username: String)

abstract class UserDb {
  def find(identityId: IdentityId): Option[SocialUser]

  def findByEmailAndProvider(email: String, providerId: String): Option[SocialUser]

  def save(identity: Identity): SocialUser

  def save(token: Token)

  def findToken(uuid: String): Option[Token]

  def deleteToken(uuid: String)

  def deleteExpiredTokens()

  def findAll: List[SocialUser]

  def findEmailByTokenUuid(tokenUuid: String): Option[String]

  def findUserProfileByUsername(username: String): Option[UserProfile]
}
