package so.paws.db.sorm

import sorm.Entity

trait SormEntities {
  def get: Set[Entity]
}