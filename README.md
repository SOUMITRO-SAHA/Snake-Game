# Snake Game

This is a simple Snake game implemented using Java Swing. The game allows the player to control a snake and eat apples to score points. The game ends when the snake collides with itself or the game boundaries.

## Features

- Snake movement controlled by arrow keys or WASD Keys.
- Randomly generated apples for the snake to eat.
- Score tracking and display.
- Highest score saving and display.
- Confirmation dialog before closing the game window.

## Requirements

- Java Development Kit (JDK) 8 or later
- Java Swing library

## Getting Started

1. Clone the repository or download the source code.
2. Compile the Java source file `SnakeGame.java` using the command:
   ```
   javac SnakeGame.java
   ```
3. Run the compiled bytecode file `SnakeGame.class` using the command:
   ```
   java SnakeGame
   ```
4. The game window will open, and you can start playing the Snake game.

## How to Play

- Control the snake's movement using the arrow keys.
- The objective is to eat as many apples as possible to score points.
- The game ends when the snake collides with itself or the boundaries of the game window.
- After the game ends, the highest score achieved is displayed.

## Customization

You can customize the game by modifying the following aspects:

- Dimensions and size: The game window's dimensions and size are automatically adjusted based on the board's content.
- Images: The game uses default images for the snake's body, head, and the apple. If you want to change the images, you can modify the `loadImage()` method in the `Board` class.

## Score Management

The game keeps track of the highest score achieved by the player. The highest score is saved in a file called `scores.txt`. If the current score is higher than the stored highest score, it gets updated and saved.

## Exiting the Game

When you try to close the game window, a confirmation dialog will appear asking if you want to exit. You can choose either "Yes" to exit the game or "No" to continue playing.

## Credit

This Snake game implementation was created by Soumitra Saha.

Author: Soumitra Saha

Email: [soumitrosahaofficial@gmail.com](mailto:soumitrosahaofficial@gmail.com)
