package todo

import scopt.OParser

sealed trait Command
case class AddCommand(description: String) extends Command
case class DeleteCommand(id: String) extends Command
case class CompleteCommand(id: String) extends Command
case class IncompleteCommand(id: String) extends Command
case class ListCommand(showCompleted: Boolean = true) extends Command
case object HelpCommand extends Command

case class Config(
  command: Command = HelpCommand
)

object TodoCLI {
  
  def main(args: Array[String]): Unit = {
    val builder = OParser.builder[Config]
    val parser = {
      import builder._
      OParser.sequence(
        programName("todo"),
        head("Scala CLI ToDo Application", "0.1.0"),
        
        cmd("add")
          .action((_, c) => c.copy(command = AddCommand("")))
          .text("Add a new task")
          .children(
            arg[String]("description")
              .action((x, c) => c.copy(command = AddCommand(x)))
              .text("Task description")
          ),
          
        cmd("delete")
          .action((_, c) => c.copy(command = DeleteCommand("")))
          .text("Delete a task")
          .children(
            arg[String]("id")
              .action((x, c) => c.copy(command = DeleteCommand(x)))
              .text("Task ID (can be partial)")
          ),
          
        cmd("complete")
          .action((_, c) => c.copy(command = CompleteCommand("")))
          .text("Mark a task as complete")
          .children(
            arg[String]("id")
              .action((x, c) => c.copy(command = CompleteCommand(x)))
              .text("Task ID (can be partial)")
          ),
          
        cmd("incomplete")
          .action((_, c) => c.copy(command = IncompleteCommand("")))
          .text("Mark a task as incomplete")
          .children(
            arg[String]("id")
              .action((x, c) => c.copy(command = IncompleteCommand(x)))
              .text("Task ID (can be partial)")
          ),
          
        cmd("list")
          .action((_, c) => c.copy(command = ListCommand()))
          .text("List all tasks")
          .children(
            opt[Unit]("active-only")
              .action((_, c) => c.copy(command = ListCommand(showCompleted = false)))
              .text("Show only active (incomplete) tasks")
          ),
          
        help("help").text("Show this help message")
      )
    }
    
    OParser.parse(parser, args, Config()) match {
      case Some(config) => runCommand(config.command)
      case _ => // Error occurred, help message already printed
    }
  }
  
  private def runCommand(command: Command): Unit = {
    val todoManager = new TodoManager()
    
    command match {
      case AddCommand(description) =>
        if (description.nonEmpty) {
          val task = todoManager.addTask(description)
          println(s"✓ Added task: ${task.description}")
          println(s"  ID: ${task.id.take(8)}...")
        } else {
          println("Error: Task description cannot be empty")
        }
        
      case DeleteCommand(id) =>
        if (id.nonEmpty) {
          todoManager.findTask(id) match {
            case Some(task) =>
              if (todoManager.deleteTask(task.id)) {
                println(s"✓ Deleted task: ${task.description}")
              } else {
                println("Error: Failed to delete task")
              }
            case None =>
              println(s"Error: No task found with ID starting with '$id'")
          }
        } else {
          println("Error: Task ID cannot be empty")
        }
        
      case CompleteCommand(id) =>
        if (id.nonEmpty) {
          todoManager.findTask(id) match {
            case Some(task) =>
              todoManager.completeTask(task.id) match {
                case Some(completedTask) =>
                  println(s"✓ Marked as complete: ${completedTask.description}")
                case None =>
                  println("Error: Failed to complete task")
              }
            case None =>
              println(s"Error: No task found with ID starting with '$id'")
          }
        } else {
          println("Error: Task ID cannot be empty")
        }
        
      case IncompleteCommand(id) =>
        if (id.nonEmpty) {
          todoManager.findTask(id) match {
            case Some(task) =>
              todoManager.incompleteTask(task.id) match {
                case Some(incompleteTask) =>
                  println(s"✓ Marked as incomplete: ${incompleteTask.description}")
                case None =>
                  println("Error: Failed to mark task as incomplete")
              }
            case None =>
              println(s"Error: No task found with ID starting with '$id'")
          }
        } else {
          println("Error: Task ID cannot be empty")
        }
        
      case ListCommand(showCompleted) =>
        val tasks = todoManager.listTasks(showCompleted)
        if (tasks.isEmpty) {
          if (showCompleted) {
            println("No tasks found.")
          } else {
            println("No active tasks found.")
          }
        } else {
          val title = if (showCompleted) "All Tasks:" else "Active Tasks:"
          println(title)
          println("=" * title.length)
          tasks.zipWithIndex.foreach { case (task, index) =>
            println(s"${index + 1}. $task")
            println(s"   ID: ${task.id.take(8)}...")
          }
        }
        
      case HelpCommand =>
        println("""
Usage: todo <command> [options]

Commands:
  add <description>     Add a new task
  delete <id>          Delete a task (use partial ID)
  complete <id>        Mark a task as complete (use partial ID)
  incomplete <id>      Mark a task as incomplete (use partial ID)
  list                 List all tasks
  list --active-only   List only active (incomplete) tasks
  help                 Show this help message

Examples:
  todo add "Buy groceries"
  todo list
  todo complete abc123
  todo delete abc123
        """)
    }
  }
}
