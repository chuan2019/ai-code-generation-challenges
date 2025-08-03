package todo

import java.time.LocalDateTime
import java.util.UUID

case class Task(
  id: String = UUID.randomUUID().toString,
  description: String,
  isCompleted: Boolean = false,
  createdAt: LocalDateTime = LocalDateTime.now(),
  completedAt: Option[LocalDateTime] = None
) {
  def complete(): Task = this.copy(
    isCompleted = true, 
    completedAt = Some(LocalDateTime.now())
  )
  
  def incomplete(): Task = this.copy(
    isCompleted = false, 
    completedAt = None
  )
  
  override def toString: String = {
    val status = if (isCompleted) "✓" else "○"
    val dateStr = createdAt.toLocalDate.toString
    s"[$status] $description (created: $dateStr)"
  }
}
