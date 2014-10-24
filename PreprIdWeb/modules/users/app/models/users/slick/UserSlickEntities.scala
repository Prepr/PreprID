package models.users.slick


import securesocial.core._
import securesocial.core.IdentityId
import securesocial.core.OAuth1Info
import securesocial.core.OAuth2Info
import securesocial.core.PasswordInfo
import securesocial.core.providers.Token
import so.paws.db.slick.SlickEntities
import scala.slick.driver.H2Driver.simple._

object UserSlickEntities extends SlickEntities {
//  def get: Set[Entity] = {
//    Set(
//      Entity[Authority](unique = Set() + Seq("identityId", "name")),
//      Entity[AuthenticatorEntity](),
//      Entity[UserProfile](unique = Set() + Seq("identityId") + Seq("username")),
//      /**
//       * Secure Social Entities
//       */
//      Entity[AuthenticationMethod](indexed = Set() + Seq("method")),
//      Entity[IdentityId](
//        indexed = Set() + Seq("providerId", "userId"),
//        unique = Set() + Seq("providerId", "userId")
//      ),
//      Entity[OAuth1Info](),
//      Entity[OAuth2Info](),
//      Entity[PasswordInfo](indexed = Set() + Seq("password")),
//      Entity[SocialUser](indexed = Set() + Seq("email")),
//      Entity[Token](indexed = Set() + Seq("uuid") + Seq("email"))
//    )
//  }

//  class LocalAuthority(tag: Tag) extends Table[(Int)](tag, "AUTHORITY") {
//
//    def * = ()
//  }

  // userId: String, providerId: String
  class IdentityIds(tag: Tag) extends Table[IdentityId](tag, "IDENTITY_ID") {
    def userId = column[String]("USER_ID")
    def providerId = column[String]("PROVIDER_ID")

    def * = (userId, providerId) <> (IdentityId.tupled, IdentityId.unapply)
  }

  class SocialUsers(tag: Tag) extends Table[SocialUser](tag, "SOCIAL_USER") {
    //def identityId: IdentityId,
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def fullName = column[String]("fullName")
    def email = column[String]("email")
    def avatarUrl = column[String]("avatarUrl")
//    def authMethod = AuthenticationMethod,
//    oAuth1Info: Option[OAuth1Info] = None,
//    oAuth2Info: Option[OAuth2Info] = None,
//    passwordInfo: Option[PasswordInfo] = None

    def * = ???
  }

  class AuthenticationMethods(tag: Tag) extends Table[AuthenticationMethod](tag, "AUTHENTICATION_METHOD") {
    def method = column[String]("method")

    def * = (method) <> (AuthenticationMethod.apply, AuthenticationMethod.unapply)
  }

  class OAuth1Infos(tag: Tag) extends Table[OAuth1Info](tag, "OAUTH1_INFO") {
    def token = column[String]("token")
    def secret = column[String]("secret")

    def * = (token, secret) <> (OAuth1Info.tupled, OAuth1Info.unapply)
  }

  class OAuth2Infos(tag: Tag) extends Table[OAuth2Info](tag, "OAUTH2_INFO") {

    def accessToken = column[String]("accessToken")
    def tokenType = column[String]("tokenType")
    def expiresIn = column[Int]("expiresIn")
    def refreshToken = column[String]("refreshToken")

    def * = (accessToken, tokenType.?, expiresIn.?, refreshToken.?) <> (OAuth2Info.tupled, OAuth2Info.unapply)
  }

  class PasswordInfos(tag: Tag) extends Table[PasswordInfo](tag, "PASSWORD_INFO") {
    def hasher = column[String]("hasher")
    def password = column[String]("password")
    def salt = column[String]("salt")

    def * = (hasher, password, salt.?) <> (PasswordInfo.tupled, PasswordInfo.unapply)
  }

  class Tokens(tag: Tag) extends Table[Token](tag, "TOKEN") {
    //uuid: String, email: String, creationTime: DateTime, expirationTime: DateTime, isSignUp: Boolean
    def uuid = column[String]("uuid")
    def email = column[String]("email")

    def * = ???
  }
}