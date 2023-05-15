package Task1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel panel = new DrawingPanel();
        frame.add(panel);

        frame.setSize(400, 400);
        frame.setVisible(true);

        Thread thread1 = new Thread(() -> {
            while (true) {
                panel.moveObject1();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                panel.moveObject2();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            while (true) {
                panel.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class DrawingPanel extends JPanel {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        private  int o1v;
        private  int o2v;

        public DrawingPanel() {
            x1 = 50;
            y1 = 50;
            x2 = 200;
            y2 = 200;

            o1v = 5;
            o2v = 3;
        }

        public void moveObject1() {
            x1 += o1v;
            y1 += o1v;

            if(x1 + 50 > getWidth()) x1 = getWidth() - 50;
            if(y1 + 50 > getHeight()) y1 = getHeight() - 50;
        }

        public void moveObject2() {
            x2 -= o2v;
            y2 -= o2v;

            if(x2 < 0) x2 = 0;
            if(y2 < 0) y2 = 0;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.RED);
            g.fillOval(x1, y1, 50, 50);

            g.setColor(Color.BLUE);
            g.fillRect(x2, y2, 50, 50);
        }
    }
}
