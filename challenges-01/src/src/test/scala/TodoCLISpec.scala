package todo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach
import java.io.File
import java.util.UUID

class TodoCLISpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  private var testDataFile: String = _

  override def beforeEach(): Unit = {
    // Create unique test file for each test  
    testDataFile = s"cli_test_tasks_${UUID.randomUUID().toString.take(8)}.txt"
    
    // Clean up any existing test file
    val file = new File(testDataFile)
    if (file.exists()) file.delete()
    
    // Also clean up the default tasks.txt that CLI uses
    val defaultFile = new File("tasks.txt")
    if (defaultFile.exists()) defaultFile.delete()
  }

  override def afterEach(): Unit = {
    // Clean up test file
    val file = new File(testDataFile)
    if (file.exists()) file.delete()
    
    // Clean up default file
    val defaultFile = new File("tasks.txt")
    if (defaultFile.exists()) defaultFile.delete()
  }

  "TodoCLI" should "integrate with TodoManager correctly for add command" in {
    // Test the integration by checking the resulting file state
    TodoCLI.main(Array("add", "Integration test task"))
    
    // Verify task was added by loading with TodoManager
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    
    tasks should have size 1
    tasks.head.description shouldBe "Integration test task"
    tasks.head.isCompleted shouldBe false
  }

  it should "integrate correctly for list command with empty task list" in {
    // This test verifies that list command doesn't crash with empty task list
    // We can't easily test output, but we can ensure it doesn't throw exceptions
    noException should be thrownBy {
      TodoCLI.main(Array("list"))
    }
  }

  it should "integrate correctly for add and list commands" in {
    // Add a task
    TodoCLI.main(Array("add", "First task"))
    TodoCLI.main(Array("add", "Second task"))
    
    // Verify tasks were added
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    
    tasks should have size 2
    tasks.map(_.description) should contain allOf ("First task", "Second task")
  }

  it should "integrate correctly for complete command" in {
    // Add a task first
    TodoCLI.main(Array("add", "Task to complete"))
    
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    val taskId = tasks.head.id
    
    // Complete the task using partial ID
    TodoCLI.main(Array("complete", taskId.take(8)))
    
    // Verify task was completed
    val updatedManager = new TodoManager()
    val updatedTasks = updatedManager.listTasks()
    updatedTasks.head.isCompleted shouldBe true
  }

  it should "integrate correctly for delete command" in {
    // Add a task first
    TodoCLI.main(Array("add", "Task to delete"))
    
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    val taskId = tasks.head.id
    
    // Delete the task
    TodoCLI.main(Array("delete", taskId.take(8)))
    
    // Verify task was deleted
    val updatedManager = new TodoManager()
    val updatedTasks = updatedManager.listTasks()
    updatedTasks shouldBe empty
  }

  it should "integrate correctly for incomplete command" in {
    // Add and complete a task first
    TodoCLI.main(Array("add", "Task to incomplete"))
    
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    val taskId = tasks.head.id
    
    TodoCLI.main(Array("complete", taskId.take(8)))
    
    // Mark as incomplete
    TodoCLI.main(Array("incomplete", taskId.take(8)))
    
    // Verify task is incomplete
    val updatedManager = new TodoManager()
    val updatedTasks = updatedManager.listTasks()
    updatedTasks.head.isCompleted shouldBe false
  }

  it should "handle command parsing correctly" in {
    // Test that invalid commands don't crash the application
    noException should be thrownBy {
      TodoCLI.main(Array("invalid-command"))
    }
  }

  it should "handle help command without crashing" in {
    // The help command should not throw exceptions
    noException should be thrownBy {
      TodoCLI.main(Array("--help"))
    }
  }

  it should "handle empty arguments without crashing" in {
    // Empty arguments should show help
    noException should be thrownBy {
      TodoCLI.main(Array())
    }
  }

  it should "maintain data persistence across command invocations" in {
    // Add tasks in separate invocations
    TodoCLI.main(Array("add", "Persistent task 1"))
    TodoCLI.main(Array("add", "Persistent task 2"))
    
    // Complete one task
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    val firstTaskId = tasks.head.id
    
    TodoCLI.main(Array("complete", firstTaskId.take(8)))
    
    // Verify persistence
    val finalManager = new TodoManager()
    val finalTasks = finalManager.listTasks()
    
    finalTasks should have size 2
    finalTasks.count(_.isCompleted) shouldBe 1
    finalTasks.count(!_.isCompleted) shouldBe 1
  }

  it should "handle workflow with multiple operations" in {
    // Complex workflow test
    TodoCLI.main(Array("add", "Buy groceries"))
    TodoCLI.main(Array("add", "Write report"))
    TodoCLI.main(Array("add", "Call client"))
    
    val manager = new TodoManager()
    val tasks = manager.listTasks()
    val secondTaskId = tasks(1).id
    
    // Complete second task
    TodoCLI.main(Array("complete", secondTaskId.take(8)))
    
    // Delete third task
    val thirdTaskId = tasks(2).id
    TodoCLI.main(Array("delete", thirdTaskId.take(8)))
    
    // Verify final state
    val finalManager = new TodoManager()
    val finalTasks = finalManager.listTasks()
    
    finalTasks should have size 2
    finalTasks.find(_.description == "Buy groceries").get.isCompleted shouldBe false
    finalTasks.find(_.description == "Write report").get.isCompleted shouldBe true
    finalTasks.find(_.description == "Call client") shouldBe None
  }
}
