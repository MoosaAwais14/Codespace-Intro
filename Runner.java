import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Runner extends JPanel implements ActionListener {

    private int ballX = 250, ballY = 250, ballDiameter = 20;
    private int ballXSpeed = 2, ballYSpeed = 2;
    private int paddleWidth = 10, paddleHeight = 60;
    private int leftPaddleY = 200, rightPaddleY = 200;
    private Timer timer;

    public PongGame() {
        timer = new Timer(10, this);
        timer.start();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W && leftPaddleY > 0) {
                    leftPaddleY -= 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_S && leftPaddleY < getHeight() - paddleHeight) {
                    leftPaddleY += 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_UP && rightPaddleY > 0) {
                    rightPaddleY -= 10;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && rightPaddleY < getHeight() - paddleHeight) {
                    rightPaddleY += 10;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.WHITE);
        g.fillRect(10, leftPaddleY, paddleWidth, paddleHeight); // Left paddle
        g.fillRect(getWidth() - 20, rightPaddleY, paddleWidth, paddleHeight); // Right paddle
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter); // Ball
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Ball collision with top and bottom
        if (ballY <= 0 || ballY >= getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball collision with paddles
        if (ballX <= 20 && ballY + ballDiameter >= leftPaddleY && ballY <= leftPaddleY + paddleHeight) {
            ballXSpeed = -ballXSpeed;
        }
        if (ballX >= getWidth() - 30 && ballY + ballDiameter >= rightPaddleY && ballY <= rightPaddleY + paddleHeight) {
            ballXSpeed = -ballXSpeed;
        }

        // Ball goes out of bounds
        if (ballX < 0 || ballX > getWidth()) {
            ballX = 250;
            ballY = 250;
            ballXSpeed = -ballXSpeed; // Reset ball direction
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame pongGame = new PongGame();
        frame.add(pongGame);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        pongGame.requestFocus();
    }
}