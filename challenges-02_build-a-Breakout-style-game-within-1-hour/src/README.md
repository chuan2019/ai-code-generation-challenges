# Breakout Game

A simple breakout-style game built with Python and pygame.

## Setup

### Option 1: Using the setup script (Recommended)
```bash
# From the project root directory
./setup_env.sh
```

### Option 2: Manual setup
1. Create and activate virtual environment:
   ```bash
   pyenv virtualenv 3.11.11 breakout-game
   pyenv local breakout-game
   ```

2. Install dependencies:
   ```bash
   pip install -r src/requirements.txt
   ```

## Running the Game

### Option 1: Using the run script
```bash
./run_game.sh
```

### Option 2: Direct execution
```bash
python src/breakout.py
```

## Controls

- **ESC** or close window to quit the game

## Features

- Basic game window with title
- Game loop structure ready for expansion
- Event handling for quit events
- 60 FPS frame rate control

## Next Steps

The basic game loop is ready. You can now add:
- Paddle (player-controlled)
- Ball with physics
- Bricks to break
- Collision detection
- Score system
- Game states (menu, playing, game over)
