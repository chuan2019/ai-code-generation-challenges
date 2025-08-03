#!/bin/bash
# Simple script to run the Scala ToDo CLI application

cd "$(dirname "$0")"
exec sbt "runMain todo.TodoCLI \"$@\""
