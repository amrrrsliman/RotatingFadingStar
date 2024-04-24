import javax.swing.*;
import java.awt.*;

public class RotatingFadingStar extends JPanel implements Runnable {
    private int x = 100;
    private int y = 0;
    private int sideLength = 50;
    private int ySpeed = 2;
    private double rotationAngle = 0;
    private double rotationSpeed = 0.05;
    private boolean fadingOut = false;
    private int alpha = 255;

    public RotatingFadingStar() {
        Thread animationThread = new Thread(this);
        animationThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x + sideLength / 2, y + sideLength / 2);
        g2d.rotate(rotationAngle);

        if (ySpeed == 1) {
            Color fadedColor = new Color(255, 0, 0, alpha);
            g2d.setColor(fadedColor);
        } else {
            Color fadedColor = new Color(0, 0, 0, alpha);
            g2d.setColor(fadedColor);
        }

        int halfSideLength = sideLength / 2;
        g2d.fillRect(-halfSideLength, -halfSideLength, sideLength, sideLength);
        g2d.rotate(Math.PI / 4);
        g2d.fillRect(-halfSideLength, -halfSideLength, sideLength, sideLength);

        g2d.dispose();

        if (fadingOut) {
            alpha -= 1;
            if (alpha < 0) {
                alpha = 0;
                fadingOut = false;
            }
        } else {
            alpha += 1;
            if (alpha > 255) {
                alpha = 255;
                fadingOut = true;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            rotationAngle += rotationSpeed;

            y += ySpeed;

            if (y + sideLength > getHeight()) {
                ySpeed = -1;
            } else if (y < 0) {
                ySpeed = 1;
            }

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Fading Star");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new RotatingFadingStar());
        frame.setVisible(true);
    }
}