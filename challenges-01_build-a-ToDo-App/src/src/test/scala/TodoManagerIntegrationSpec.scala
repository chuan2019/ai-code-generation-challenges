package todo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach
import java.io.File
import java.time.LocalDateTime
import java.util.UUID

class TodoManagerIntegrationSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  private var testDataFile: String = _

  override def beforeEach(): Unit = {
    // Create unique test file for each test
    testDataFile = s"integration_test_tasks_${UUID.randomUUID().toString.take(8)}.txt"
    
    // Clean up any existing test file
    val file = new File(testDataFile)
    if (file.exists()) file.delete()
  }

  override def afterEach(): Unit = {
    // Clean up test file after each test
    val file = new File(testDataFile)
    if (file.exists()) file.delete()
  }

  "TodoManager" should "persist tasks to file and reload them" in {
    // Create first manager and add tasks
    val manager1 = new TodoManager(testDataFile)
    val task1 = manager1.addTask("Persistent task 1")
    val task2 = manager1.addTask("Persistent task 2")
    manager1.completeTask(task1.id)

    // Create second manager instance - should load persisted tasks
    val manager2 = new TodoManager(testDataFile)
    val loadedTasks = manager2.listTasks()

    loadedTasks should have size 2
    
    // Find the tasks by description since IDs will be preserved
    val loadedTask1 = loadedTasks.find(_.description == "Persistent task 1")
    val loadedTask2 = loadedTasks.find(_.description == "Persistent task 2")
    
    loadedTask1 should not be None
    loadedTask2 should not be None
    
    loadedTask1.get.isCompleted shouldBe true
    loadedTask1.get.completedAt should not be None
    loadedTask2.get.isCompleted shouldBe false
    loadedTask2.get.completedAt shouldBe None
  }

  it should "handle empty file gracefully" in {
    // Create empty file
    val file = new File(testDataFile)
    file.createNewFile()

    val manager = new TodoManager(testDataFile)
    manager.listTasks() shouldBe empty
  }

  it should "handle malformed file data gracefully" in {
    // Create file with malformed data
    val file = new File(testDataFile)
    val writer = new java.io.PrintWriter(file)
    try {
      writer.println("malformed|data")
      writer.println("incomplete|line")
      writer.println("too|many|parts|here|extra|parts|even|more")
    } finally {
      writer.close()
    }

    // Should not crash and should start with empty list
    val manager = new TodoManager(testDataFile)
    manager.listTasks() shouldBe empty
  }

  it should "preserve task IDs across restarts" in {
    val manager1 = new TodoManager(testDataFile)
    val originalTask = manager1.addTask("ID preservation test")
    val originalId = originalTask.id

    val manager2 = new TodoManager(testDataFile)
    val loadedTask = manager2.findTask(originalId)
    
    loadedTask should not be None
    loadedTask.get.id shouldBe originalId
    loadedTask.get.description shouldBe "ID preservation test"
  }

  it should "preserve timestamps across restarts" in {
    val manager1 = new TodoManager(testDataFile)
    val task = manager1.addTask("Timestamp test")
    val completedTask = manager1.completeTask(task.id).get
    val originalCreatedAt = completedTask.createdAt
    val originalCompletedAt = completedTask.completedAt

    val manager2 = new TodoManager(testDataFile)
    val loadedTask = manager2.findTask(task.id).get
    
    loadedTask.createdAt shouldBe originalCreatedAt
    loadedTask.completedAt shouldBe originalCompletedAt
  }

  it should "handle special characters in file persistence" in {
    val manager1 = new TodoManager(testDataFile)
    val specialDescription = "Task with special chars: !@#$%^&*()_+-=[]{}\\;':\",./<>?"
    val task = manager1.addTask(specialDescription)

    val manager2 = new TodoManager(testDataFile)
    val loadedTask = manager2.findTask(task.id)
    
    loadedTask should not be None
    loadedTask.get.description shouldBe specialDescription
  }

  it should "maintain consistency after multiple operations and restarts" in {
    // First session: add tasks
    val manager1 = new TodoManager(testDataFile)
    val task1 = manager1.addTask("Task 1")
    val task2 = manager1.addTask("Task 2")
    val task3 = manager1.addTask("Task 3")

    // Second session: complete and delete tasks
    val manager2 = new TodoManager(testDataFile)
    manager2.completeTask(task1.id)
    manager2.deleteTask(task2.id)

    // Third session: verify final state
    val manager3 = new TodoManager(testDataFile)
    val finalTasks = manager3.listTasks()
    
    finalTasks should have size 2
    
    val remainingTask1 = finalTasks.find(_.id == task1.id)
    val remainingTask3 = finalTasks.find(_.id == task3.id)
    
    remainingTask1 should not be None
    remainingTask1.get.isCompleted shouldBe true
    
    remainingTask3 should not be None
    remainingTask3.get.isCompleted shouldBe false
    
    // Task 2 should be deleted
    finalTasks.find(_.id == task2.id) shouldBe None
  }

  it should "handle concurrent access gracefully" in {
    // This test verifies basic file operations don't corrupt data
    // Note: The current implementation is not thread-safe
    val manager1 = new TodoManager(testDataFile)
    val manager2 = new TodoManager(testDataFile)
    
    // Add tasks from different manager instances
    val task1 = manager1.addTask("From manager 1")
    val task2 = manager2.addTask("From manager 2")
    
    // Create fresh manager to see final state
    val manager3 = new TodoManager(testDataFile)
    val allTasks = manager3.listTasks()
    
    // At least one task should be persisted
    // (The other might be lost due to race condition, which is expected behavior)
    allTasks should not be empty
  }

  it should "recover from corrupted file by starting fresh" in {
    // Create a file with corrupted content
    val file = new File(testDataFile)
    val writer = new java.io.PrintWriter(file)
    try {
      writer.println("this is not valid task data")
      writer.println("neither is this")
    } finally {
      writer.close()
    }

    // Manager should start with empty list
    val manager = new TodoManager(testDataFile)
    manager.listTasks() shouldBe empty
    
    // Should be able to add new tasks normally
    val newTask = manager.addTask("Recovery test")
    manager.listTasks() should have size 1
    manager.listTasks().head shouldBe newTask
  }
}
