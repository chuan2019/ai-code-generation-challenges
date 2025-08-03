# AI Code Challenge #1
## Scala CLI ToDo Application

**Tool**: Github Copilot in VS Code

### Project Setup

Created a Scala CLI ToDo application with the following features:
- Add, delete, complete, and list tasks
- Persistent storage to file system
- Command-line interface using scopt library
- Partial ID matching for easy task management

### Iterations

#### Iteration 1: Initial Setup and Core Implementation

**Structure Created:**
- `build.sbt` - Project configuration with dependencies
- `Task.scala` - Case class representing a task with ID, description, completion status, and timestamps
- `TodoManager.scala` - Core business logic for managing tasks and file persistence
- `TodoCLI.scala` - Command-line interface using scopt for argument parsing

**Features Implemented:**
- ✅ Adding tasks with unique IDs
- ✅ Deleting tasks by partial ID
- ✅ Marking tasks complete/incomplete
- ✅ Listing all tasks or only active tasks
- ✅ Persistent storage to `tasks.txt` file
- ✅ Error handling and user feedback

**Dependencies Used:**
- `scopt` for command-line argument parsing
- `scalatest` for testing (setup but not implemented yet)

**Next Steps:**
- Add unit tests
- Improve error handling
- Add task editing functionality
- Consider adding due dates

##### Testing Results

Successfully implemented and tested all core features:

**✅ Core Functionality Tests:**
- Adding tasks: `./todo.sh add "Learn Scala programming"`
- Listing tasks: `./todo.sh list` and `./todo.sh list --active-only`
- Completing tasks: `./todo.sh complete 5a339f` (partial ID matching)
- Deleting tasks: `./todo.sh delete 2b09a1` (partial ID matching)
- Data persistence: Tasks saved to `tasks.txt` and loaded on restart

**✅ User Experience Features:**
- Helpful command-line help: `./todo.sh --help`
- Visual task status indicators: `[○]` for incomplete, `[✓]` for complete
- Partial ID matching for easy task management
- Clear error messages and user feedback
- Shell script wrapper for convenient execution

**✅ Technical Implementation:**
- Clean separation of concerns (Task, TodoManager, TodoCLI)
- Functional programming patterns using Scala case classes
- Robust file I/O with error handling
- Command-line argument parsing with scopt library
- UUID-based task identification

##### Key Learning Outcomes

1. **Scala Syntax & Idioms**: Learned case classes, pattern matching, Option types, and functional collections
2. **Build System**: Configured SBT with dependencies and project structure
3. **CLI Design**: Implemented user-friendly command-line interface with proper help and error handling
4. **File Persistence**: Handled file I/O operations with error recovery
5. **Project Organization**: Structured code into logical modules following Scala conventions

The application is fully functional and ready for use as a personal ToDo management tool.

#### Iteration 2: Add Test Cases

**Objective**: Generate comprehensive test cases to prevent broken changes during development.

**Test Suite Implemented:**
- **TaskSpec.scala** (11 unit tests): Task case class functionality
  - Task creation with all required fields
  - Task completion state changes and immutability
  - ID generation and timestamp verification
- **TodoManagerSpec.scala** (19 unit tests): Core business logic
  - CRUD operations (add, delete, complete, list tasks)
  - File persistence and data integrity
  - Error handling and edge cases
  - Test isolation using unique file names
- **TodoManagerIntegrationSpec.scala** (9 integration tests): File system integration
  - End-to-end persistence workflows
  - File creation and data recovery
  - Cross-session data consistency
- **TodoCLISpec.scala** (11 CLI tests): Command-line interface validation
  - Command parsing and execution
  - User input validation
  - Output formatting verification

**Test Infrastructure:**
- **Test runner script** (`run-tests.sh`): Multiple execution modes
- **GitHub Actions workflow**: Automated CI/CD testing
- **Test isolation**: Each test uses unique files to prevent interference
- **ScalaTest framework**: FlatSpec with Matchers for readable test syntax

**Key Testing Features:**
- 50+ test cases across 4 categories providing comprehensive coverage
- Automated test execution before/after code changes
- Regression detection to catch breaking changes immediately
- Documentation and setup instructions for team collaboration

**Usage Commands:**
```bash
sbt test                    # Run all tests
sbt "testOnly *TaskSpec"    # Run specific test suite
./run-tests.sh             # Use custom test runner
```

**Learning Outcomes:**
1. **Test-Driven Development**: Importance of comprehensive test coverage
2. **ScalaTest Framework**: Writing effective unit and integration tests
3. **Test Isolation**: Preventing test interference through unique file handling
4. **CI/CD Integration**: Automated testing workflows with GitHub Actions
5. **Quality Assurance**: Using tests to maintain code reliability during refactoring

#### Iteration 3: Fix Regression Bug

**Issue Identified**: Manual code edits introduced compilation failures due to undefined functions.

**Root Cause Analysis:**
- Manual edits in `TodoManager.scala` introduced calls to undefined functions `escapePipes` and `JSON`
- Changed from working pipe-delimited format to non-existent JSON parsing
- Return type mismatch in `findTask` method causing compilation errors

**Debugging Process:**
1. **Error Detection**: `sbt compile` revealed compilation failures
2. **Issue Location**: Errors at lines 69 and 86 in `TodoManager.scala`
3. **Code Investigation**: Read file contents to understand manual changes
4. **Problem Identification**: Undefined function calls and format inconsistencies

**Fixes Applied:**
- **Restored pipe-delimited format**: Removed JSON parsing attempts
- **Fixed undefined functions**: Removed calls to `escapePipes` and `JSON.parse`
- **Corrected return types**: Fixed `findTask` method to return `Option[Task]`
- **Maintained original logic**: Preserved working file I/O implementation

**Verification Steps:**
1. **Compilation check**: `sbt compile` - successful
2. **Test execution**: `sbt test` - all 50+ tests passing
3. **JAR assembly**: `sbt assembly` - successful build
4. **Runtime verification**: JAR execution confirmed working

**Prevention Measures:**
- Test suite catches regressions immediately
- Regular compilation checks before commits
- Code review process for manual edits
- Documentation of working implementations

**Key Learning Outcomes:**
1. **Regression Testing**: Tests successfully caught breaking changes
2. **Debugging Skills**: Systematic approach to identifying compilation issues
3. **Code Stability**: Importance of maintaining working implementations
4. **Version Control**: Value of having test coverage to validate fixes
5. **Development Workflow**: Integration of testing into development process

**Final Status**: All functionality restored and verified working. Application ready for continued development with robust test coverage.


