#!/bin/bash

# Test runner script for the Scala Todo CLI application
# This script provides different ways to run tests

set -e  # Exit on any error

cd "$(dirname "$0")"

echo "🧪 Scala Todo CLI Test Runner"
echo "================================"

# Function to run tests with colored output
run_tests() {
    local test_type="$1"
    local test_class="$2"
    
    echo "🔄 Running $test_type tests..."
    if [ -n "$test_class" ]; then
        sbt "testOnly $test_class"
    else
        sbt test
    fi
    
    if [ $? -eq 0 ]; then
        echo "✅ $test_type tests passed!"
    else
        echo "❌ $test_type tests failed!"
        exit 1
    fi
    echo ""
}

# Parse command line arguments
case "${1:-all}" in
    "unit")
        echo "Running unit tests only..."
        run_tests "Unit" "todo.TaskSpec"
        run_tests "Unit" "todo.TodoManagerSpec"
        ;;
    "integration")
        echo "Running integration tests only..."
        run_tests "Integration" "todo.TodoManagerIntegrationSpec"
        ;;
    "cli")
        echo "Running CLI tests only..."
        run_tests "CLI" "todo.TodoCLISpec"
        ;;
    "all")
        echo "Running all tests..."
        run_tests "All" ""
        ;;
    "suite")
        echo "Running test suite..."
        run_tests "Test Suite" "todo.AllTestSuite"
        ;;
    "watch")
        echo "Running tests in watch mode..."
        echo "Tests will re-run automatically when files change."
        echo "Press Ctrl+C to stop."
        sbt "~test"
        ;;
    "coverage")
        echo "Running tests with coverage report..."
        if ! grep -q "sbt-scoverage" ../project/plugins.sbt 2>/dev/null; then
            echo "⚠️  Coverage plugin not installed. Install with:"
            echo "   Add 'addSbtPlugin(\"org.scoverage\" % \"sbt-scoverage\" % \"1.9.3\")' to project/plugins.sbt"
            echo "   Add 'coverageEnabled := true' to build.sbt"
            echo ""
            echo "Running tests without coverage..."
            run_tests "All" ""
        else
            sbt clean coverage test coverageReport
            echo "📊 Coverage report generated in target/scala-*/scoverage-report/index.html"
        fi
        ;;
    "help"|"-h"|"--help")
        echo "Usage: $0 [OPTION]"
        echo ""
        echo "Options:"
        echo "  all         Run all tests (default)"
        echo "  unit        Run unit tests only (Task, TodoManager)"
        echo "  integration Run integration tests only (file persistence)"
        echo "  cli         Run CLI tests only"
        echo "  suite       Run organized test suite"
        echo "  watch       Run tests in watch mode (re-run on file changes)"
        echo "  coverage    Run tests with coverage report"
        echo "  help        Show this help message"
        echo ""
        echo "Examples:"
        echo "  $0              # Run all tests"
        echo "  $0 unit         # Run only unit tests"
        echo "  $0 watch        # Run tests in watch mode"
        echo "  $0 coverage     # Run tests with coverage"
        ;;
    *)
        echo "❌ Unknown option: $1"
        echo "Use '$0 help' to see available options."
        exit 1
        ;;
esac

echo "🎉 Test execution completed!"
