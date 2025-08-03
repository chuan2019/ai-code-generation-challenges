package todo

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.time.LocalDateTime

class TaskSpec extends AnyFlatSpec with Matchers {

  "A Task" should "be created with default values" in {
    val task = Task(description = "Test task")
    
    task.id should not be empty
    task.description shouldBe "Test task"
    task.isCompleted shouldBe false
    task.createdAt should not be null
    task.completedAt shouldBe None
  }

  it should "be created with custom id" in {
    val customId = "custom-123"
    val task = Task(id = customId, description = "Test task")
    
    task.id shouldBe customId
  }

  it should "be created as completed" in {
    val task = Task(description = "Test task", isCompleted = true)
    
    task.isCompleted shouldBe true
  }

  it should "be completed correctly" in {
    val task = Task(description = "Test task")
    val completedTask = task.complete()
    
    completedTask.isCompleted shouldBe true
    completedTask.completedAt should not be None
    completedTask.id shouldBe task.id
    completedTask.description shouldBe task.description
    completedTask.createdAt shouldBe task.createdAt
  }

  it should "be marked as incomplete correctly" in {
    val completedTask = Task(description = "Test task", isCompleted = true, 
                           completedAt = Some(LocalDateTime.now()))
    val incompleteTask = completedTask.incomplete()
    
    incompleteTask.isCompleted shouldBe false
    incompleteTask.completedAt shouldBe None
    incompleteTask.id shouldBe completedTask.id
    incompleteTask.description shouldBe completedTask.description
    incompleteTask.createdAt shouldBe completedTask.createdAt
  }

  it should "generate correct string representation for incomplete task" in {
    val task = Task(description = "Test task")
    val taskString = task.toString
    
    taskString should include("○")
    taskString should include("Test task")
    taskString should include("created:")
  }

  it should "generate correct string representation for completed task" in {
    val task = Task(description = "Test task").complete()
    val taskString = task.toString
    
    taskString should include("✓")
    taskString should include("Test task")
    taskString should include("created:")
  }

  it should "preserve immutability when completing" in {
    val originalTask = Task(description = "Test task")
    val completedTask = originalTask.complete()
    
    // Original task should remain unchanged
    originalTask.isCompleted shouldBe false
    originalTask.completedAt shouldBe None
    
    // New task should be completed
    completedTask.isCompleted shouldBe true
    completedTask.completedAt should not be None
  }

  it should "preserve immutability when marking incomplete" in {
    val originalTask = Task(description = "Test task", isCompleted = true, 
                           completedAt = Some(LocalDateTime.now()))
    val incompleteTask = originalTask.incomplete()
    
    // Original task should remain unchanged
    originalTask.isCompleted shouldBe true
    originalTask.completedAt should not be None
    
    // New task should be incomplete
    incompleteTask.isCompleted shouldBe false
    incompleteTask.completedAt shouldBe None
  }

  it should "have different ids for different instances" in {
    val task1 = Task(description = "Task 1")
    val task2 = Task(description = "Task 2")
    
    task1.id should not equal task2.id
  }

  it should "handle empty description" in {
    val task = Task(description = "")
    
    task.description shouldBe ""
    task.toString should include("")
  }
}
