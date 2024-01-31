import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class BrickBreakerGame extends JFrame implements KeyListener, ActionListener {
    private Timer timer;
    private int ballX = 120;
    private int ballY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;
    private int paddleX = 310;
    private int paddleY = 550;
    private int paddleXDir = 0;
    private boolean[][] bricks;

    public BrickBreakerGame() {
        bricks = new boolean[3][7];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                bricks[i][j] = true;
            }
        }

        setTitle("Brick Breaker Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        timer = new Timer(5, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        setVisible(true);
    }

    public void paint(Graphics g) {
        // Drawing background
        g.setColor(Color.black);
        g.fillRect(1, 1, 592, 592);

        // Drawing bricks
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (bricks[i][j]) {
                    g.setColor(Color.white);
                    g.fillRect(j * 80 + 8, i * 100 + 50, 70, 20);
                }
            }
        }

        // Drawing paddle
        g.setColor(Color.green);
        g.fillRect(paddleX, paddleY, 100, 8);

        // Drawing ball
        g.setColor(Color.yellow);
        g.fillOval(ballX, ballY, 20, 20);

        if (ballY > 570) {
            timer.stop();
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 190, 300);
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();

        // Ball movement
        ballX += ballXDir;
        ballY += ballYDir;

        // Wall collisions
        if (ballX < 0 || ballX > 570) {
            ballXDir = -ballXDir;
        }
        if (ballY < 0) {
            ballYDir = -ballYDir;
        }

        // Paddle collision
        if ((ballY > paddleY) && (ballY < paddleY + 8) && (ballX > paddleX) && (ballX < paddleX + 100)) {
            ballYDir = -ballYDir;
        }

        // Brick collisions
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (bricks[i][j]) {
                    int brickX = j * 80 + 8;
                    int brickY = i * 100 + 50;
                    if ((ballX > brickX) && (ballX < brickX + 70) && (ballY > brickY) && (ballY < brickY + 20)) {
                        bricks[i][j] = false;
                        ballYDir = -ballYDir;
                    }
                }
            }
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX >= 500) {
                paddleX = 500;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddleX <= 10) {
                paddleX = 10;
            } else {
                moveLeft();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void moveRight() {
        paddleXDir = 20;
        paddleX += paddleXDir;
    }

    public void moveLeft() {
        paddleXDir = -20;
        paddleX += paddleXDir;
    }

    public static void main(String[] args) {
        new BrickBreakerGame();
    }
}
