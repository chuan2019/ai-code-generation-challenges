# Todo CLI - Testing Guide

This document explains the comprehensive test suite for the Scala Todo CLI application.

## Test Structure

The test suite is organized into four main categories:

### 1. Unit Tests

#### `TaskSpec.scala`
- **Purpose**: Tests the `Task` case class functionality
- **Coverage**: 
  - Task creation with default and custom values
  - Task completion and incompletion operations
  - String representation formatting
  - Immutability verification
  - Edge cases (empty descriptions, special characters)

#### `TodoManagerSpec.scala`
- **Purpose**: Tests the core business logic of `TodoManager`
- **Coverage**:
  - Task CRUD operations (Create, Read, Update, Delete)
  - Task state management (complete/incomplete)
  - Task listing with filtering
  - Task finding by ID (full and partial)
  - Error handling for non-existent tasks
  - Edge cases and special scenarios

### 2. Integration Tests

#### `TodoManagerIntegrationSpec.scala`
- **Purpose**: Tests file persistence and data integrity
- **Coverage**:
  - Task persistence across application restarts
  - File format handling and data recovery
  - Timestamp and ID preservation
  - Error handling for corrupted data
  - Concurrent access scenarios

### 3. CLI Tests

#### `TodoCLISpec.scala`
- **Purpose**: Tests the command-line interface
- **Coverage**:
  - All CLI commands (add, delete, complete, incomplete, list, help)
  - Command-line argument parsing
  - Output formatting and error messages
  - Complex workflow scenarios
  - Edge cases and error conditions

### 4. Test Suite Organization

#### `AllTestSuite.scala`
- **Purpose**: Organizes all tests in a logical execution order
- **Benefits**: Ensures unit tests run before integration tests

## Running Tests

### Using the Test Runner Script

The provided `run-tests.sh` script offers several options:

```bash
# Run all tests
./run-tests.sh

# Run specific test categories
./run-tests.sh unit         # Unit tests only
./run-tests.sh integration  # Integration tests only
./run-tests.sh cli          # CLI tests only
./run-tests.sh suite        # Organized test suite

# Development workflows
./run-tests.sh watch        # Auto-run tests on file changes
./run-tests.sh coverage     # Run with coverage report

# Help
./run-tests.sh help
```

### Using SBT Directly

```bash
# Run all tests
sbt test

# Run specific test classes
sbt "testOnly todo.TaskSpec"
sbt "testOnly todo.TodoManagerSpec"
sbt "testOnly todo.TodoManagerIntegrationSpec"
sbt "testOnly todo.TodoCLISpec"

# Run test suite
sbt "testOnly todo.AllTestSuite"

# Watch mode
sbt "~test"

# With detailed output
sbt "test-only * -- -oD"
```

## Test Features

### Automatic Cleanup
- All tests automatically clean up temporary files
- No interference between test runs
- Safe for parallel execution (where applicable)

### Comprehensive Coverage
- **Happy path scenarios**: Normal operation flows
- **Edge cases**: Empty inputs, special characters, boundary conditions
- **Error scenarios**: Invalid inputs, missing resources, corrupted data
- **Integration scenarios**: File persistence, state management

### Output Capture
- CLI tests capture and verify console output
- Ensures proper user feedback and error messages
- Tests both success and error message formatting

## Continuous Integration Ready

The test suite is designed for CI/CD pipelines:

1. **Fast execution**: Unit tests run quickly for rapid feedback
2. **Isolated tests**: No dependencies between tests
3. **Clear reporting**: Detailed output for debugging failures
4. **Exit codes**: Proper exit codes for CI systems

## Adding New Tests

When adding new features, follow this pattern:

1. **Add unit tests** for new classes/methods
2. **Add integration tests** if the feature involves file operations
3. **Add CLI tests** if the feature includes new commands
4. **Update the test suite** if needed

### Example Test Structure

```scala
class NewFeatureSpec extends AnyFlatSpec with Matchers {
  
  "NewFeature" should "handle normal case" in {
    // Arrange
    val input = "test input"
    
    // Act
    val result = newFeature.process(input)
    
    // Assert
    result shouldBe expected
  }
  
  it should "handle edge case" in {
    // Test edge cases
  }
  
  it should "handle error case" in {
    // Test error scenarios
  }
}
```

## Test Configuration

The `build.sbt` includes optimized test settings:

- **Detailed output**: `-oD` flag for comprehensive test reporting
- **No parallel execution**: Prevents file system conflicts
- **Colored output**: Enhanced readability
- **Test reports**: Generated in `target/test-reports/`

## Benefits of This Test Suite

1. **Regression Prevention**: Catch breaking changes immediately
2. **Refactoring Safety**: Confident code improvements
3. **Documentation**: Tests serve as executable specifications
4. **Quality Assurance**: Ensures consistent behavior
5. **Development Speed**: Fast feedback loop for developers

## Troubleshooting

### Common Issues

1. **File Permission Errors**: Ensure `run-tests.sh` is executable
2. **Port Conflicts**: Tests clean up automatically, but manual cleanup may be needed
3. **SBT Memory**: Increase heap size if needed: `sbt -J-Xmx2G test`

### Test Failures

1. Check the detailed output for specific failure reasons
2. Run individual test classes to isolate issues
3. Use watch mode for rapid debugging cycles
4. Verify file system permissions for integration tests

This comprehensive test suite ensures the Todo CLI application is robust, reliable, and maintainable.
