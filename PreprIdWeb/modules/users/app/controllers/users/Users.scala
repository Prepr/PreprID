package controllers.users

import play.api.mvc._
import scala.io.Source
import scala.util.Random
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models.users.UserDb
import securesocial.core._
import securesocial.core.OAuth1Info
import securesocial.core.IdentityId
import models.users.UserProfile
import models.web.{Navigation, NavigationItem, NavigationMenu}

abstract class Users extends Controller with SecureSocial {
  val userDb: UserDb  // plugin appropriate implementation

  def navigation() = SecuredAction { implicit request =>
    val menus =
      Seq(
        NavigationMenu(
          Seq(
            NavigationItem("Change Password", "#/password/change")
          ),
          position = "left"
        ),
        NavigationMenu(
          Seq(
            NavigationItem("Sign Out", "#/signout")
          ),
          position = "right"
        )
      )

    val navigation = Navigation("default", menus)

    Ok(Navigation.toJson(navigation))
  }

  def generateName() = Action {
    implicit request =>
      Ok(Json.stringify(Json.obj(
        "firstName" -> getRandomName(1133, "/names/firstname.txt"),
        "lastName" -> getRandomName(979, "/names/lastname.txt")
      )))
  }

  def findEmailByTokenUuid(token: String) = Action {
    implicit request =>
      Ok(Json.stringify(Json.obj(
        "email" -> userDb.findEmailByTokenUuid(token)
      )))
  }

  def getRandomName(count: Int, path: String): String = {
    val nameIndex = Random.nextInt(count)
    var index = 0

    Source.fromInputStream(getClass.getResourceAsStream(path)).getLines().foreach(
      (name: String) => {
        if (nameIndex == index) return name.capitalize
        index = index + 1
      })
    ""
  }

  implicit val authenticationMethodFormat = Json.format[AuthenticationMethod]
  implicit val identityIdFormat = Json.format[IdentityId]
  implicit val oAuth1InfoFormat = Json.format[OAuth1Info]
  implicit val oAuth2InfoFormat = Json.format[OAuth2Info]
  implicit val passwordInfoFormat = Json.format[PasswordInfo]
  implicit val socialUserJsonFormat = (
    (__ \ 'identityId).format[IdentityId] and
      (__ \ 'firstName).format[String] and
      (__ \ 'lastName).format[String] and
      (__ \ 'fullName).format[String] and
      (__ \ 'email).formatNullable[String] and
      (__ \ 'avatarUrl).formatNullable[String] and
      (__ \ 'authenticationMethod).format[AuthenticationMethod] and
      (__ \ 'oAuth1Info).formatNullable[OAuth1Info] and
      (__ \ 'oAuth2Info).formatNullable[OAuth2Info] and
      (__ \ 'passwordInfo).formatNullable[PasswordInfo]
    )(SocialUser.apply(
    _: IdentityId,
    _: String,
    _: String,
    _: String,
    _: Option[String],
    _: Option[String],
    _: AuthenticationMethod,
    _: Option[OAuth1Info],
    _: Option[OAuth2Info],
    _: Option[PasswordInfo]
  ), unlift(SocialUser.unapply))
  implicit val userProfileFormat = Json.format[UserProfile]

  def getUsers = SecuredAction {
    implicit request => {
      val allUsers: List[SocialUser] = userDb.findAll
      Ok(Json.toJson(allUsers))
    }
  }

  def home(username: String) = Action {
    implicit request =>
      userDb.findUserProfileByUsername(username)
        .map(userProfile =>
          Ok(Json.toJson(userProfile))
        ).getOrElse(
          Ok("")
        )
  }
}
