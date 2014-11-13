package models.users

import securesocial.core.Authenticator
import plugins.users.AuthenticatorEntity

abstract class AuthenticatorDb {
  def save(authenticator: Authenticator): Either[Error, Unit]

  def find(key: String): Either[Error, Option[Authenticator]]
  
  def findEntity(key: String): Option[AuthenticatorEntity]

  def delete(key: String): Either[Error, Unit]
  
}
