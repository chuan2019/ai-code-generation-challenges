import pygame
import sys

# Initialize pygame
pygame.init()

# Constants
WINDOW_WIDTH = 800
WINDOW_HEIGHT = 600
BACKGROUND_COLOR = (0, 0, 0)  # Black
WHITE = (255, 255, 255)
FPS = 60

class BreakoutGame:
    def __init__(self):
        self.screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
        pygame.display.set_caption("Breakout Game")
        self.clock = pygame.time.Clock()
        self.running = True
    
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
        # Game logic will be added here
        pass
    
    def draw(self):
        """Draw everything on the screen"""
        # Clear screen with background color
        self.screen.fill(BACKGROUND_COLOR)
        
        # Draw game title (temporary, for testing)
        font = pygame.font.Font(None, 74)
        text = font.render("Breakout Game", True, WHITE)
        text_rect = text.get_rect(center=(WINDOW_WIDTH // 2, WINDOW_HEIGHT // 2))
        self.screen.blit(text, text_rect)
        
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
