import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

/*
 * @author: soumitra saha
 */
public class Board extends JPanel implements ActionListener {
    // Declaring all dimensions related to the board:
    private final int B_HEIGHT = 400;
    private final int B_WIDTH = 400;
    private final int DOT_SIZE = 10;
    private final int MAX_DOTS = (B_HEIGHT/DOT_SIZE) * (B_WIDTH/DOT_SIZE);
    private final int DELAY = 140;

    private int DOTS;
    private boolean inGame = true;

    // Position on the Board [Coordination]
    private final int []x = new int[MAX_DOTS];
    private final int []y = new int[MAX_DOTS];

    // Apple Position:
    private int apple_x;
    private int apple_y;

    // Images:
    private Image body, head, apple;
    private Timer timer;

    // Direction
    private boolean leftDirection = true;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;

    Board(){
        Adapter adapter = new Adapter();
        addKeyListener(adapter);
        setFocusable(true);
        this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        this.setBackground(Color.black);
        this.initGame();
        this.loadImage();
    }

    private void initGame(){
        DOTS = 3;

        // Initialize Snake's Position:
        x[0] = 250;
        y[0] = 250;

        for(int i=0; i<DOTS; i++){
            x[i] = x[0] - DOT_SIZE * i;
            y[i] = y[0];
        }

        // Initialize the Apple Position:
        locateApple();
        // Initialize the Timer:
        // Timer
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Loading images form resource folder:
    private void loadImage(){
        ImageIcon bodyImage = new ImageIcon("src/resources/dot.png");
        body = bodyImage.getImage();

        ImageIcon appleImage = new ImageIcon("src/resources/apple.png");
        apple = appleImage.getImage();

        ImageIcon headImage = new ImageIcon("src/resources/head.png");
        head = headImage.getImage();
    }
    // Draw images at snake and apple position:
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    // Draw images || Game Over Tasks are assigned here:
    private void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
        else { // Game Over:
            int highestScore = ScoreManager.getHighestScore();
            int currentScore = (DOTS - 3) * 10;
            // Writing the Score into the Score file:
            if(currentScore > highestScore){
                ScoreManager.saveHighestScore(currentScore);
            }
            // Game Over Task:
            gameOver(g);
            timer.stop();
        }
    }
    // Game Over Message:
    private void gameOver(Graphics g){
        int score = (DOTS - 3) * 10;
        int hScore = ScoreManager.getHighestScore();
        String msg = "Game over";
        String scoreMsg = "Score: " + score;
        // If the Highest Score is Zero:
        if(hScore == 0) hScore = score;
        String highestScoreMsg = "Highest Score: " + hScore;
        // Font:
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

        // Display Message:
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fontMetrics.stringWidth(msg))/2, B_HEIGHT/4);
        g.drawString(highestScoreMsg, (B_WIDTH - fontMetrics.stringWidth(highestScoreMsg))/2, 2*(B_HEIGHT/4));
        g.drawString(scoreMsg, (B_WIDTH - fontMetrics.stringWidth(scoreMsg))/2, 3*(B_HEIGHT/4));
    }
    // Randomize apple's position:
    private void locateApple(){
        apple_x = (int)(Math.random() * 39) * DOT_SIZE;
        apple_y = (int)(Math.random() * 39) * DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // This will run each time: for every event happens in the game:
        move();
        repaint();
        checkApple();
        checkCollision();
    }
    // Make Snake Move:
    private void checkCollision(){
        for (int i = 0; i < DOTS; i++) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }

        // Collision with Border:
        if(x[0] < 0 || (x[0] >= B_WIDTH)) inGame = false;
        if(y[0] < 0 || (y[0] >= B_HEIGHT)) inGame = false;
    }
    private void move(){
        for (int i = DOTS-1; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection) x[0] -= DOT_SIZE;
        if(rightDirection) x[0] += DOT_SIZE;
        if(upDirection) y[0] -= DOT_SIZE;
        if(downDirection) y[0] += DOT_SIZE;
    }
    // Make Snake Eat Food:
    private void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]) {
            DOTS++;
            // Re-locate the Apple Position:
            locateApple();
        }
    }
    // ScoreManager:
    private static class ScoreManager {
        private static final String SCORE_FILE = "src/resources/scores.txt";
        public static int getHighestScore() {
            int highestScore = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE));
                String line = reader.readLine();
                if (line != null) {
                    highestScore = Integer.parseInt(line);
                }
                reader.close();
            } catch (IOException e) {
                // File not found or error reading the file, handle accordingly
                e.printStackTrace();
            }
            System.out.println(highestScore);
            return highestScore;
        }
        public static void saveHighestScore(int score) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE));
                writer.write(Integer.toString(score));
                writer.close();
            } catch (IOException e) {
                // Error writing to the file, handle accordingly
                e.printStackTrace();
            }
        }
    }
    // Implements Controls:
    private class Adapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && !downDirection) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && !upDirection) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
