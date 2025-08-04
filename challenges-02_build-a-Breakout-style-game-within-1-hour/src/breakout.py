import pygame
import sys
import random

# Initialize pygame
pygame.init()

# Constants
WINDOW_WIDTH = 800
WINDOW_HEIGHT = 600
BACKGROUND_COLOR = (0, 0, 0)  # Black
WHITE = (255, 255, 255)
FPS = 60

# Ball constants
BALL_SIZE = 15
BALL_COLOR = WHITE
BALL_SPEED = 5

# Brick constants
BRICK_WIDTH = 60
BRICK_HEIGHT = 20
BRICK_COLORS = [
    (255, 0, 0),    # Red
    (0, 255, 0),    # Green
    (0, 0, 255),    # Blue
    (255, 255, 0),  # Yellow
    (255, 0, 255),  # Magenta
    (0, 255, 255),  # Cyan
    (255, 165, 0),  # Orange
]
BRICK_COUNT = 40
BRICK_MARGIN = 2

class BreakoutGame:
    def __init__(self):
        self.screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
        pygame.display.set_caption("Breakout Game")
        self.clock = pygame.time.Clock()
        self.running = True
        
        # Initialize ball
        self.ball_x = WINDOW_WIDTH // 2
        self.ball_y = WINDOW_HEIGHT // 2
        self.ball_dx = BALL_SPEED
        self.ball_dy = -BALL_SPEED
        
        # Initialize bricks
        self.bricks = self.create_grid_bricks()
    
    def create_grid_bricks(self):
        """Create bricks in a non-overlapping 2D grid layout"""
        bricks = []
        
        # Calculate grid dimensions
        cols = (WINDOW_WIDTH - 2 * BRICK_MARGIN) // (BRICK_WIDTH + BRICK_MARGIN)
        rows = (WINDOW_HEIGHT // 2 - 2 * BRICK_MARGIN) // (BRICK_HEIGHT + BRICK_MARGIN)
        
        # Create a list of all possible grid positions
        grid_positions = []
        for row in range(rows):
            for col in range(cols):
                x = BRICK_MARGIN + col * (BRICK_WIDTH + BRICK_MARGIN)
                y = BRICK_MARGIN + row * (BRICK_HEIGHT + BRICK_MARGIN)
                grid_positions.append((x, y))
        
        # Randomly select positions from the grid (up to BRICK_COUNT)
        num_bricks = min(BRICK_COUNT, len(grid_positions))
        selected_positions = random.sample(grid_positions, num_bricks)
        
        # Create bricks at selected positions
        for x, y in selected_positions:
            color = random.choice(BRICK_COLORS)
            
            # Create brick as a dictionary with position, size, and color
            brick = {
                'x': x,
                'y': y,
                'width': BRICK_WIDTH,
                'height': BRICK_HEIGHT,
                'color': color,
                'rect': pygame.Rect(x, y, BRICK_WIDTH, BRICK_HEIGHT)
            }
            bricks.append(brick)
        
        return bricks
    
    def handle_events(self):
        """Handle pygame events"""
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                self.running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    self.running = False
    
    def update(self):
        """Update game state"""
        # Update ball position
        self.ball_x += self.ball_dx
        self.ball_y += self.ball_dy
        
        # Ball collision with left and right walls
        if self.ball_x - BALL_SIZE // 2 <= 0 or self.ball_x + BALL_SIZE // 2 >= WINDOW_WIDTH:
            self.ball_dx = -self.ball_dx
            # Keep ball within bounds
            if self.ball_x - BALL_SIZE // 2 <= 0:
                self.ball_x = BALL_SIZE // 2
            else:
                self.ball_x = WINDOW_WIDTH - BALL_SIZE // 2
        
        # Ball collision with top and bottom walls
        if self.ball_y - BALL_SIZE // 2 <= 0 or self.ball_y + BALL_SIZE // 2 >= WINDOW_HEIGHT:
            self.ball_dy = -self.ball_dy
            # Keep ball within bounds
            if self.ball_y - BALL_SIZE // 2 <= 0:
                self.ball_y = BALL_SIZE // 2
            else:
                self.ball_y = WINDOW_HEIGHT - BALL_SIZE // 2
        
        # Ball collision with bricks
        ball_rect = pygame.Rect(self.ball_x - BALL_SIZE // 2, self.ball_y - BALL_SIZE // 2, BALL_SIZE, BALL_SIZE)
        
        for brick in self.bricks[:]:  # Use slice copy to safely remove during iteration
            if ball_rect.colliderect(brick['rect']):
                # Remove the brick (brick "explodes")
                self.bricks.remove(brick)
                
                # Determine collision side and bounce ball accordingly
                ball_center_x = self.ball_x
                ball_center_y = self.ball_y
                brick_center_x = brick['x'] + brick['width'] // 2
                brick_center_y = brick['y'] + brick['height'] // 2
                
                # Simple collision response - reverse direction based on collision
                dx = abs(ball_center_x - brick_center_x)
                dy = abs(ball_center_y - brick_center_y)
                
                if dx > dy:  # Hit from left or right
                    self.ball_dx = -self.ball_dx
                else:  # Hit from top or bottom
                    self.ball_dy = -self.ball_dy
                
                break  # Only handle one collision per frame
    
    def draw(self):
        """Draw everything on the screen"""
        # Clear screen with background color
        self.screen.fill(BACKGROUND_COLOR)
        
        # Draw bricks
        for brick in self.bricks:
            pygame.draw.rect(self.screen, brick['color'], brick['rect'])
            # Draw brick border for better visibility
            pygame.draw.rect(self.screen, WHITE, brick['rect'], 1)
        
        # Draw ball
        pygame.draw.circle(self.screen, BALL_COLOR, (int(self.ball_x), int(self.ball_y)), BALL_SIZE // 2)
        
        # Update display
        pygame.display.flip()
    
    def run(self):
        """Main game loop"""
        print("Starting Breakout Game...")
        print("Press ESC or close window to quit")
        
        while self.running:
            # Handle events
            self.handle_events()
            
            # Update game state
            self.update()
            
            # Draw everything
            self.draw()
            
            # Control frame rate
            self.clock.tick(FPS)
        
        # Cleanup
        pygame.quit()
        sys.exit()

def main():
    """Entry point of the game"""
    game = BreakoutGame()
    game.run()

if __name__ == "__main__":
    main()
