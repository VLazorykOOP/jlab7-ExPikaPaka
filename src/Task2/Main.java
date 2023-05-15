package Task2;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel panel = new DrawingPanel();
        frame.add(panel);

        frame.setSize(640, 480);
        frame.setVisible(true);

        Thread thread1 = new Thread(() -> {
            while (true) {
                if (!panel.isObject1Paused()) {
                    panel.moveObject1();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                if (!panel.isObject2Paused()) {
                    panel.moveObject2();
                }
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

        createControlButtons(frame, panel);
    }

    static class DrawingPanel extends JPanel {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        private  int o1v;
        private  int o2v;

        private boolean object1Paused;
        private boolean object2Paused;

        public DrawingPanel() {
            x1 = 50;
            y1 = 50;
            x2 = 200;
            y2 = 200;
            object1Paused = false;
            object2Paused = false;

            o1v = 5;
            o2v = 3;
        }

        public void moveObject1() {
            x1 += o1v;
            y1 += o1v;

            if (x1 + 50 > getWidth()) x1 = getWidth() - 50;
            if (y1 + 50 > getHeight()) y1 = getHeight() - 50;
        }

        public void moveObject2() {
            x2 -= o2v;
            y2 -= o2v;

            if (x2 < 0) x2 = 0;
            if (y2 < 0) y2 = 0;
        }

        public boolean isObject1Paused() {
            return object1Paused;
        }

        public boolean isObject2Paused() {
            return object2Paused;
        }

        public void pauseObject1() {
            object1Paused = true;
        }

        public void resumeObject1() {
            object1Paused = false;
        }

        public void pauseObject2() {
            object2Paused = true;
        }

        public void resumeObject2() {
            object2Paused = false;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.RED);
            g.fillOval(x1, y1, 50, 50);
            g.setColor(Color.BLUE);
            g.fillRect(x2, y2, 50, 50);
        }
    }

    private static void createControlButtons(JFrame frame, DrawingPanel panel) {
        JPanel controlPanel = new JPanel();
        JButton pauseObject1Button = new JButton("Pause Object 1");
        JButton resumeObject1Button = new JButton("Resume Object 1");
        JButton pauseObject2Button = new JButton("Pause Object 2");
        JButton resumeObject2Button = new JButton("Resume Object 2");

        pauseObject1Button.addActionListener(e -> panel.pauseObject1());

        resumeObject1Button.addActionListener(e -> panel.resumeObject1());

        pauseObject2Button.addActionListener(e -> panel.pauseObject2());

        resumeObject2Button.addActionListener(e -> panel.resumeObject2());

        controlPanel.add(pauseObject1Button);
        controlPanel.add(resumeObject1Button);
        controlPanel.add(pauseObject2Button);
        controlPanel.add(resumeObject2Button);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}