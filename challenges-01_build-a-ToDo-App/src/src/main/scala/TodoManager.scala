package todo

import scala.collection.mutable.ListBuffer
import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Try, Success, Failure}

class TodoManager(dataFileName: String = "tasks.txt") {
  private val tasks = ListBuffer[Task]()
  private val dataFile = dataFileName
  
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
  
  // Returns the first task whose ID starts with the given prefix.
  def findTask(id: String): Option[Task] = {
    tasks.find(_.id.startsWith(id))
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
            val parts = line.split("\\|")
            if (parts.length >= 4) {
              val task = Task(
                id = parts(0),
                description = parts(1),
                isCompleted = parts(2).toBoolean,
                createdAt = java.time.LocalDateTime.parse(parts(3)),
                completedAt = if (parts.length > 4 && parts(4).nonEmpty) 
                  Some(java.time.LocalDateTime.parse(parts(4))) else None
              )
              tasks += task
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
