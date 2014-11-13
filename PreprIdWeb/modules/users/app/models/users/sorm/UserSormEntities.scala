package models.users.sorm

import models.users.{UserProfile, Authority}
import plugins.users.AuthenticatorEntity
import securesocial.core._
import securesocial.core.IdentityId
import securesocial.core.OAuth1Info
import securesocial.core.OAuth2Info
import securesocial.core.PasswordInfo
import securesocial.core.providers.Token
import so.paws.db.sorm.SormEntities
import sorm.Entity

object UserSormEntities extends SormEntities {
  def get: Set[Entity] = {
    Set(
      Entity[Authority](unique = Set() + Seq("identityId", "name")),
      Entity[AuthenticatorEntity](),
      Entity[UserProfile](unique = Set() + Seq("identityId") + Seq("username")),
      /**
       * Secure Social Entities
       */
      Entity[AuthenticationMethod](indexed = Set() + Seq("method")),
      Entity[IdentityId](
        indexed = Set() + Seq("providerId", "userId"),
        unique = Set() + Seq("providerId", "userId")
      ),
      Entity[OAuth1Info](),
      Entity[OAuth2Info](),
      Entity[PasswordInfo](indexed = Set() + Seq("password")),
      Entity[SocialUser](indexed = Set() + Seq("email")),
      Entity[Token](indexed = Set() + Seq("uuid") + Seq("email"))
    )
  }
}