import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class root {
    private JPanel rootPanel;
    private JTextField refLabel;
    private JTextField xLabel;
    private JTextField yLabel;
    private JTextField colorLabel;
    private JButton startButton;
    private javax.swing.Timer timer;
    private Point point;
    private java.awt.Robot robot;
    private java.awt.Color color;
    private final int tps = 100;

    public root() {
        try {robot = new Robot();}
        catch (Exception e){
            System.out.println("Error with Robot...");
        }
        point = new Point(1,1);
        timer = new javax.swing.Timer((int)(1000/tps), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("tick");
                point = MouseInfo.getPointerInfo().getLocation();
                color = robot.getPixelColor(point.x, point.y);
                String color_string = String.format(
                        "R:%03d   G:%03d   B:%03d",
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue());
                xLabel.setText(String.valueOf(point.x));
                yLabel.setText(String.valueOf(point.y));
                colorLabel.setText(color_string);
            }
        });
        timer.start();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()){
                    startButton.setText("Start");
                    timer.stop();
                }else{
                    startButton.setText("Stop");
                    timer.start();
                }

            }
        });
        rootPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("key");
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("root");
        frame.setContentPane(new root().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

    }
}
