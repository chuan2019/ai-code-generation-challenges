#!/bin/bash

# Breakout Game Development Environment Setup

echo "Setting up Breakout Game development environment..."

# Check if pyenv is available
if ! command -v pyenv &> /dev/null; then
    echo "Error: pyenv is not installed or not in PATH"
    exit 1
fi

# Check if the virtual environment exists
if pyenv versions --bare | grep -q "breakout-game"; then
    echo "Virtual environment 'breakout-game' found"
else
    echo "Creating virtual environment 'breakout-game'..."
    pyenv virtualenv 3.11.11 breakout-game
fi

# Set local Python version
echo "Setting local Python version to breakout-game..."
pyenv local breakout-game

# Install dependencies
echo "Installing dependencies..."
pip install -r src/requirements.txt

echo "Setup complete!"
echo ""
echo "To run the game:"
echo "  python src/breakout.py"
echo ""
echo "To deactivate the environment:"
echo "  pyenv local --unset"
