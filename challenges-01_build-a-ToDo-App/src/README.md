# Scala CLI ToDo Application

A simple command-line ToDo application built with Scala using SBT and the scopt library for argument parsing.

## Features

- ✅ Add tasks with unique IDs
- ✅ Delete tasks by partial ID matching
- ✅ Mark tasks as complete/incomplete
- ✅ List all tasks or only active (incomplete) tasks
- ✅ Persistent storage to file system
- ✅ User-friendly command-line interface
- ✅ Error handling and validation

## Prerequisites

- Java 11 or later
- Scala 2.13.x
- SBT (Scala Build Tool)

## Installation

1. Ensure you have Java, Scala, and SBT installed
2. Clone or download this project
3. Navigate to the project directory

## Building the Project

```bash
sbt compile
```

## Usage

### Using SBT directly

```bash
# Show help
sbt "runMain todo.TodoCLI --help"

# Add a new task
sbt "runMain todo.TodoCLI add \"Learn Scala programming\""

# List all tasks
sbt "runMain todo.TodoCLI list"

# List only active tasks
sbt "runMain todo.TodoCLI list --active-only"

# Mark a task as complete (using partial ID)
sbt "runMain todo.TodoCLI complete abc123"

# Mark a task as incomplete
sbt "runMain todo.TodoCLI incomplete abc123"

# Delete a task (using partial ID)
sbt "runMain todo.TodoCLI delete abc123"
```

### Using the shell script

For convenience, you can use the provided shell script:

```bash
# Make script executable (first time only)
chmod +x todo.sh

# Use the script
./todo.sh add "Buy groceries"
./todo.sh list
./todo.sh complete abc123
./todo.sh delete abc123
```

## Commands

### `add <description>`
Adds a new task with the given description. Each task gets a unique UUID.

**Example:**
```bash
./todo.sh add "Learn Scala functional programming"
```

### `delete <id>`
Deletes a task by ID. You can use partial IDs for convenience.

**Example:**
```bash
./todo.sh delete abc123  # Deletes task with ID starting with 'abc123'
```

### `complete <id>`
Marks a task as complete using partial ID matching.

**Example:**
```bash
./todo.sh complete def456
```

### `incomplete <id>`
Marks a completed task as incomplete using partial ID matching.

**Example:**
```bash
./todo.sh incomplete def456
```

### `list [--active-only]`
Lists tasks. By default shows all tasks. Use `--active-only` to show only incomplete tasks.

**Examples:**
```bash
./todo.sh list                # Show all tasks
./todo.sh list --active-only  # Show only incomplete tasks
```

## Task Format

Tasks are displayed with the following format:
```
[○] Task description (created: YYYY-MM-DD)    # Incomplete task
[✓] Task description (created: YYYY-MM-DD)    # Complete task
   ID: abc12345...
```

## Data Storage

Tasks are automatically saved to `tasks.txt` in the project directory. The file uses a pipe-separated format:
```
uuid|description|isCompleted|createdAt|completedAt
```

## Project Structure

```
src/
├── build.sbt                 # Project configuration
├── todo.sh                   # Convenience shell script
├── tasks.txt                 # Data storage file (created automatically)
├── project/
│   └── build.properties      # SBT version configuration
└── src/main/scala/
    ├── Task.scala            # Task case class
    ├── TodoManager.scala     # Core business logic
    └── TodoCLI.scala         # Command-line interface
```

## Dependencies

- **scopt 4.1.0**: Command-line argument parsing
- **ScalaTest 3.2.17**: Testing framework (for future tests)

## Error Handling

The application includes comprehensive error handling:
- Invalid commands show help information
- Missing task IDs or descriptions show appropriate error messages
- File I/O errors are handled gracefully with warning messages
- Partial ID matching prevents accidental operations on wrong tasks

## Future Enhancements

- [ ] Add unit tests
- [ ] Support for due dates
- [ ] Task categories/tags
- [ ] Priority levels
- [ ] Search functionality
- [ ] Export/import capabilities
- [ ] Configuration file support

## Contributing

This is a learning project for exploring Scala CLI application development. Feel free to suggest improvements or contribute enhancements.

## License

This project is for educational purposes.
