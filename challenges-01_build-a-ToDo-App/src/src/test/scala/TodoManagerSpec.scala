package todo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterEach
import java.io.File
import java.time.LocalDateTime
import java.util.UUID

class TodoManagerSpec extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  private var testDataFile: String = _
  private var todoManager: TodoManager = _

  override def beforeEach(): Unit = {
    // Create unique test file for each test
    testDataFile = s"test_tasks_${UUID.randomUUID().toString.take(8)}.txt"
    
    // Clean up any existing test file
    val file = new File(testDataFile)
    if (file.exists()) file.delete()
    
    // Create a fresh TodoManager instance with test file
    todoManager = new TodoManager(testDataFile)
  }

  override def afterEach(): Unit = {
    // Clean up test file after each test
    val file = new File(testDataFile)
    if (file.exists()) file.delete()
  }

  "TodoManager" should "start with empty task list" in {
    todoManager.listTasks() shouldBe empty
  }

  it should "add a task successfully" in {
    val description = "Test task"
    val task = todoManager.addTask(description)
    
    task.description shouldBe description
    task.isCompleted shouldBe false
    task.id should not be empty
    
    val tasks = todoManager.listTasks()
    tasks should have size 1
    tasks.head shouldBe task
  }

  it should "add multiple tasks" in {
    val task1 = todoManager.addTask("Task 1")
    val task2 = todoManager.addTask("Task 2")
    val task3 = todoManager.addTask("Task 3")
    
    val tasks = todoManager.listTasks()
    tasks should have size 3
    tasks should contain allOf (task1, task2, task3)
  }

  it should "delete a task by full id" in {
    val task = todoManager.addTask("Task to delete")
    val taskId = task.id
    
    todoManager.deleteTask(taskId) shouldBe true
    todoManager.listTasks() shouldBe empty
  }

  it should "return false when deleting non-existent task" in {
    todoManager.addTask("Existing task")
    todoManager.deleteTask("non-existent-id") shouldBe false
    todoManager.listTasks() should have size 1
  }

  it should "complete a task successfully" in {
    val task = todoManager.addTask("Task to complete")
    val result = todoManager.completeTask(task.id)
    
    result should not be None
    result.get.isCompleted shouldBe true
    result.get.completedAt should not be None
    result.get.id shouldBe task.id
    result.get.description shouldBe task.description
    
    val tasks = todoManager.listTasks()
    tasks.head.isCompleted shouldBe true
  }

  it should "return None when completing non-existent task" in {
    val result = todoManager.completeTask("non-existent-id")
    result shouldBe None
  }

  it should "mark a completed task as incomplete" in {
    val task = todoManager.addTask("Task to mark incomplete")
    todoManager.completeTask(task.id)
    
    val result = todoManager.incompleteTask(task.id)
    
    result should not be None
    result.get.isCompleted shouldBe false
    result.get.completedAt shouldBe None
    result.get.id shouldBe task.id
    result.get.description shouldBe task.description
    
    val tasks = todoManager.listTasks()
    tasks.head.isCompleted shouldBe false
  }

  it should "return None when marking non-existent task as incomplete" in {
    val result = todoManager.incompleteTask("non-existent-id")
    result shouldBe None
  }

  it should "list all tasks including completed ones" in {
    val task1 = todoManager.addTask("Active task")
    val task2 = todoManager.addTask("Task to complete")
    todoManager.completeTask(task2.id)
    
    val allTasks = todoManager.listTasks(showCompleted = true)
    allTasks should have size 2
    allTasks should contain (task1)
    allTasks.find(_.id == task2.id).get.isCompleted shouldBe true
  }

  it should "list only active tasks when requested" in {
    val task1 = todoManager.addTask("Active task")
    val task2 = todoManager.addTask("Task to complete")
    todoManager.completeTask(task2.id)
    
    val activeTasks = todoManager.listTasks(showCompleted = false)
    activeTasks should have size 1
    activeTasks.head shouldBe task1
  }

  it should "find task by full id" in {
    val task = todoManager.addTask("Findable task")
    val found = todoManager.findTask(task.id)
    
    found should not be None
    found.get shouldBe task
  }

  it should "find task by partial id" in {
    val task = todoManager.addTask("Findable task")
    val partialId = task.id.take(8)
    val found = todoManager.findTask(partialId)
    
    found should not be None
    found.get shouldBe task
  }

  it should "return None when finding non-existent task" in {
    todoManager.addTask("Existing task")
    val found = todoManager.findTask("non-existent")
    
    found shouldBe None
  }

  it should "handle multiple tasks with similar id prefixes" in {
    // This test assumes UUID generation creates unique prefixes
    val task1 = todoManager.addTask("Task 1")
    val task2 = todoManager.addTask("Task 2")
    
    // Should find the first task that starts with the prefix
    val found1 = todoManager.findTask(task1.id.take(8))
    val found2 = todoManager.findTask(task2.id.take(8))
    
    found1.get.id shouldBe task1.id
    found2.get.id shouldBe task2.id
  }

  it should "maintain task order" in {
    val task1 = todoManager.addTask("First task")
    val task2 = todoManager.addTask("Second task")
    val task3 = todoManager.addTask("Third task")
    
    val tasks = todoManager.listTasks()
    tasks should have size 3
    tasks(0) shouldBe task1
    tasks(1) shouldBe task2
    tasks(2) shouldBe task3
  }

  it should "handle empty description" in {
    val task = todoManager.addTask("")
    
    task.description shouldBe ""
    todoManager.listTasks() should have size 1
  }

  it should "handle special characters in description" in {
    val specialDescription = "Task with special chars: !@#$%^&*()_+-=[]{}|;':\",./<>?"
    val task = todoManager.addTask(specialDescription)
    
    task.description shouldBe specialDescription
    val found = todoManager.findTask(task.id)
    found.get.description shouldBe specialDescription
  }

  it should "persist state across multiple operations" in {
    // Add tasks
    val task1 = todoManager.addTask("Persistent task 1")
    val task2 = todoManager.addTask("Persistent task 2")
    
    // Complete one task
    todoManager.completeTask(task1.id)
    
    // Delete one task
    todoManager.deleteTask(task2.id)
    
    // Verify final state
    val tasks = todoManager.listTasks()
    tasks should have size 1
    tasks.head.id shouldBe task1.id
    tasks.head.isCompleted shouldBe true
  }
}
