package todo

import org.scalatest.Suites

/**
 * Test suite that runs all tests in a specific order.
 * This ensures that unit tests run before integration tests.
 */
class AllTestSuite extends Suites(
  new TaskSpec,
  new TodoManagerSpec,
  new TodoManagerIntegrationSpec,
  new TodoCLISpec
)
