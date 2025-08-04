# AI Code Challenge #2

## Python Breakout-style Game

**Tool**: Github Copilot in VS Code


### Iterations

#### Iteration 1: Basic Game Loop and Window Setup

**Objective**: Create a basic game loop that opens a window

**Implementation**:
- Created `src/breakout.py` with a complete game loop structure using pygame
- Implemented `BreakoutGame` class with methods for:
  - `handle_events()`: Process user input and window events
  - `update()`: Game state updates (placeholder for now)
  - `draw()`: Render graphics to screen
  - `run()`: Main game loop at 60 FPS
- Added basic window (800x600) with black background
- Included ESC key and window close button for quitting
- Added requirements.txt with pygame dependency
- Created README.md with setup and usage instructions

**Files Created**:
- `src/breakout.py` - Main game file
- `src/requirements.txt` - Dependencies
- `src/README.md` - Documentation

**Status**: ✅ Complete - Basic window opens and game loop runs

**Next Steps**: Add paddle, ball, and bricks

---

#### Iteration 2: Virtual Environment Setup

**Objective**: Set up a proper Python virtual environment using pyenv for isolated development

**Implementation**:
- Created virtual environment `breakout-game` using Python 3.11.11
- Set up local environment with `.python-version` file
- Installed pygame dependency in isolated environment
- Created convenience scripts:
  - `setup_env.sh`: Automated environment setup script
  - `run_game.sh`: Game execution script with environment checks
- Updated documentation with setup instructions

**Files Created/Modified**:
- `.python-version` - Auto-activates virtual environment
- `setup_env.sh` - Environment setup automation
- `run_game.sh` - Game runner script
- `src/README.md` - Updated with virtual environment instructions

**Verification**:
- ✅ Virtual environment created successfully
- ✅ pygame installed in isolated environment
- ✅ Game runs correctly in virtual environment
- ✅ Environment auto-activates when entering project directory

**Status**: ✅ Complete - Virtual environment configured and working

---

#### Iteration 3: Ball Physics and Randomly Positioned Bricks

**Objective**: Add a moving ball with physics and randomly arranged destructible bricks

**Implementation**:
- **Ball Physics**:
  - Added ball that starts at screen center and moves diagonally
  - Implemented collision detection with all window edges
  - Ball bounces realistically off walls while maintaining constant speed
  - Ball position is constrained to stay within window boundaries

- **Brick System**:
  - Created 40 randomly positioned colorful bricks in the upper half of screen
  - Each brick has random color from 7-color palette (red, green, blue, yellow, magenta, cyan, orange)
  - Bricks are sized 60x20 pixels with proper spacing margins
  - Added white borders around bricks for better visibility

- **Collision Detection**:
  - Implemented precise ball-to-brick collision detection using pygame rectangles
  - When ball hits a brick, the brick "explodes" (disappears from screen)
  - Smart collision response determines hit direction and bounces ball accordingly
  - Ball direction changes based on which side of brick was hit (horizontal vs vertical)

**Technical Details**:
- Used `pygame.Rect.colliderect()` for accurate collision detection
- Brick data structure stores position, size, color, and collision rectangle
- Safe iteration over brick list during collision to prevent runtime errors
- Ball collision response considers relative positions for realistic physics

**Files Modified**:
- `src/breakout.py` - Enhanced with ball physics and brick system

**Game Features Added**:
- ✅ Bouncing ball with realistic physics
- ✅ 40 randomly positioned colorful bricks
- ✅ Brick destruction on collision
- ✅ Smart collision response system
- ✅ Visual brick borders for clarity

**Status**: ✅ Complete - Core breakout gameplay mechanics implemented





