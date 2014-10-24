package controllers

import models.web.{Navigation, NavigationItem, NavigationMenu}
import play.api.mvc.{Action, Controller}


object Application extends Controller {
  def index = Action {
    Ok(views.html.index())
  }

  def navigation() = Action { implicit request =>
    val menus =
      Seq(
        NavigationMenu(
          Seq(
            NavigationItem("Sign Up", "#/signup"),
            NavigationItem("Sign In", "#/login")
          ),
          position = "left"
        )
      )

    val navigation = Navigation("default", menus)

    Ok(Navigation.toJson(navigation))
  }
}
