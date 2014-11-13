package so.paws.db.slick

import so.paws.db.DbPlugin
import play.api.Application

class SlickDbPlugin(application: Application) extends DbPlugin[Any] {

  override def db: Any = {}

//  def getEntities(application: Application, ref: String): Set[Entity] = {
//    val runtimeMirror = universe.runtimeMirror(application.classloader)
//
//    val module = runtimeMirror.staticModule(ref)
//
//    val obj = runtimeMirror.reflectModule(module)
//
//    obj.instance match {
//      case sormEntities: SormEntities => sormEntities.get
//      case _ => throw new ClassCastException
//    }
//  }
}