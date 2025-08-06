#!/bin/bash

# Run the Breakout Game

# Check if we're in the right directory
if [ ! -f "src/breakout.py" ]; then
    echo "Error: breakout.py not found. Make sure you're in the project root directory."
    exit 1
fi

# Check if the virtual environment is active
if [ "$(pyenv version-name)" != "breakout-game" ]; then
    echo "Setting up virtual environment..."
    pyenv local breakout-game
fi

echo "Starting Breakout Game..."
python src/breakout.py
