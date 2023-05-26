import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * @author: soumitra saha
 */
public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board = new Board();
        this.add(board);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        // Initialize the Snake Game:
        SnakeGame snakeGame = new SnakeGame();

        // Closing Confirmation:
        snakeGame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(snakeGame, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    snakeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    snakeGame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        snakeGame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        snakeGame.setVisible(true);
    }
}