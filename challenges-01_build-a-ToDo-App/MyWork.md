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

### Testing Results

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

### Key Learning Outcomes

1. **Scala Syntax & Idioms**: Learned case classes, pattern matching, Option types, and functional collections
2. **Build System**: Configured SBT with dependencies and project structure
3. **CLI Design**: Implemented user-friendly command-line interface with proper help and error handling
4. **File Persistence**: Handled file I/O operations with error recovery
5. **Project Organization**: Structured code into logical modules following Scala conventions

The application is fully functional and ready for use as a personal ToDo management tool.





