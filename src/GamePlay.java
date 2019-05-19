import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private Timer timer;


    private int leftPaddlePos = 310;
    private int rightPaddlePos = 310;
    private static int leftPlayerScore = 0;
    private static int rightPlayerScore = 0;

    private int ballPosX = 320;
    private int ballPosY = 220;
    private int ballDirX = 3;
    private int ballDirY = 2;
    private String scoreDisplay = 0 + " - " + 0;


    GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 4;
        timer = new Timer(delay, this);
        timer.start();
    }


    public void paint(Graphics g) {

        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(681, 0, 3, 592);

        // paddles
        g.setColor(Color.red);
        g.fillRect(5, leftPaddlePos, 8, 100);
        g.setColor(Color.red);
        g.fillRect(672, rightPaddlePos, 8, 100);

        //ball
        g.setColor(Color.red);
        g.fillRect(ballPosX, ballPosY, 20, 20);
        g.drawString(scoreDisplay, 350,10);

        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        scoreDisplay = leftPlayerScore + " - " + rightPlayerScore;

        // moves the ball if the game is started
        if (play) {
            ballPosX += ballDirX;
            ballPosY += ballDirY;
        }

        // bounces the ball off the top and the bottom
        if (ballPosY < 4) ballDirY *= -1;
        if (ballPosY > 545) ballDirY *= -1;

        // triggers score and reset
        if (ballPosX >= 675) leftPlayerScore = score(leftPlayerScore);
        if (ballPosX <= 5  ) rightPlayerScore = score(rightPlayerScore);

        // makes the ball bounce off the paddles
        if (ballPosX >= 658 && rightPaddlePos - 10 <= ballPosY && ballPosY <= rightPaddlePos + 90)
            ballDirX *= -1;
        if (ballPosX <= 10 && leftPaddlePos - 10 <= ballPosY && ballPosY <= leftPaddlePos + 90)
            ballDirX *= -1;
        repaint();
    }

    private int score(int scorer) {
            scorer++;
            ballPosX = 320;
            ballPosY = 320;
            ballDirX *= -1;
            play = false;
        return scorer;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP) rightPaddlePos = moveUp(rightPaddlePos);
        if (e.getExtendedKeyCode() == KeyEvent.VK_W) leftPaddlePos = moveUp(leftPaddlePos);
        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN)
            rightPaddlePos = moveDown(rightPaddlePos);
        if (e.getExtendedKeyCode() == KeyEvent.VK_S)
            leftPaddlePos = moveDown(leftPaddlePos);
    }


    private int moveDown(int player) {
        play = true;
        if (player >= 500) return 500;
        else return player += 30;
    }

    private int moveUp(int player) {
        play = true;
        if (player <= 0) return 0;
        else return player -= 30;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
