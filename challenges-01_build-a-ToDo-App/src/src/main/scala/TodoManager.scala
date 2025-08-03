package todo

import scala.collection.mutable.ListBuffer
import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Try, Success, Failure}

class TodoManager {
  private val tasks = ListBuffer[Task]()
  private val dataFile = "tasks.txt"
  
  // Load tasks from file on initialization
  loadTasks()
  
  def addTask(description: String): Task = {
    val task = Task(description = description)
    tasks += task
    saveTasks()
    task
  }
  
  def deleteTask(id: String): Boolean = {
    val initialSize = tasks.size
    tasks.filterInPlace(_.id != id)
    val deleted = tasks.size < initialSize
    if (deleted) saveTasks()
    deleted
  }
  
  def completeTask(id: String): Option[Task] = {
    tasks.find(_.id == id) match {
      case Some(task) =>
        val index = tasks.indexOf(task)
        val completedTask = task.complete()
        tasks(index) = completedTask
        saveTasks()
        Some(completedTask)
      case None => None
    }
  }
  
  def incompleteTask(id: String): Option[Task] = {
    tasks.find(_.id == id) match {
      case Some(task) =>
        val index = tasks.indexOf(task)
        val incompleteTask = task.incomplete()
        tasks(index) = incompleteTask
        saveTasks()
        Some(incompleteTask)
      case None => None
    }
  }
  
  def listTasks(showCompleted: Boolean = true): List[Task] = {
    if (showCompleted) tasks.toList
    else tasks.filter(!_.isCompleted).toList
  }
  
  // Returns all tasks whose IDs start with the given prefix.
  def findTask(id: String): List[Task] = {
    tasks.filter(_.id.startsWith(id)).toList
  }
  
  private def saveTasks(): Unit = {
    Try {
      val writer = new PrintWriter(new File(dataFile))
      try {
        tasks.foreach { task =>
          writer.println(s"${task.id}|${task.description}|${task.isCompleted}|${task.createdAt}|${task.completedAt.getOrElse("")}")
        }
      } finally {
        writer.close()
      }
    } match {
      case Success(_) => // Successfully saved
      case Failure(e) => println(s"Warning: Could not save tasks: ${e.getMessage}")
    }
  }
  
  private def loadTasks(): Unit = {
    Try {
      if (new File(dataFile).exists()) {
        val source = Source.fromFile(dataFile)
        try {
          source.getLines().foreach { line =>
            JSON.parseFull(line) match {
              case Some(data: Map[String, Any]) =>
                val id = data.get("id").collect { case s: String => s }.getOrElse("")
                val description = data.get("description").collect { case s: String => s }.getOrElse("")
                val isCompleted = data.get("isCompleted") match {
                  case Some(b: Boolean) => b
                  case Some(s: String) => s.toBoolean
                  case Some(n: Double) => n != 0
                  case _ => false
                }
                val createdAtStr = data.get("createdAt").collect { case s: String => s }.getOrElse("")
                val completedAtStr = data.get("completedAt").collect { case s: String => s }.getOrElse("")
                val createdAt = if (createdAtStr.nonEmpty) java.time.LocalDateTime.parse(createdAtStr) else java.time.LocalDateTime.now()
                val completedAt = if (completedAtStr.nonEmpty) Some(java.time.LocalDateTime.parse(completedAtStr)) else None
                val task = Task(
                  id = id,
                  description = description,
                  isCompleted = isCompleted,
                  createdAt = createdAt,
                  completedAt = completedAt
                )
                tasks += task
              case _ => // skip malformed lines
            }
          }
        } finally {
          source.close()
        }
      }
    } match {
      case Success(_) => // Successfully loaded
      case Failure(e) => println(s"Warning: Could not load tasks: ${e.getMessage}")
    }
  }
}
