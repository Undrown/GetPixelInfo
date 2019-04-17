import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class root{
    private JPanel rootPanel;
    private JTextField refLabel;
    private JTextField xLabel;
    private JTextField yLabel;
    private JTextField colorLabel;
    private JButton startButton;
    private JTextArea fixList;
    private javax.swing.Timer timer;
    private Point point;
    private Point refPoint;
    private ArrayList <Point> fixPointArray;
    private java.awt.Robot robot;
    private java.awt.Color color;
    private final int tps = 10;

    public root(){
        fixPointArray = new ArrayList<Point>();
        refPoint = new Point(0,0);
        //robot init
        try {robot = new Robot();}
        catch (Exception e) {
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
                //fixpoint
                fixList.setText("");
                fixPointArray.forEach(p -> {
                    fixList.append(String.format(
                            "X:%d, Y:%d, R:%03d   G:%03d   B:%03d\n",
                            p.x,
                            p.y,
                            robot.getPixelColor(p.x, p.y).getRed(),
                            robot.getPixelColor(p.x, p.y).getGreen(),
                            robot.getPixelColor(p.x, p.y).getBlue()));
                });
            }
        });
        timer.start();

        startButton.addActionListener(e -> {
            if(timer.isRunning()){
                startButton.setText("Start");
                timer.stop();
            }else{
                startButton.setText("Stop");
                timer.start();
            }

        });
        java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if(e.getID() == KeyEvent.KEY_PRESSED)
                switch (e.getKeyCode()){
                    case KeyEvent.VK_F1:
                        fixPoint();
                        break;
                    case KeyEvent.VK_F3:
                        fixRefPoint();
                        break;
                }
            return false;
        });

    }

    public void fixPoint(){
        fixPointArray.add(MouseInfo.getPointerInfo().getLocation());
    }

    public void fixRefPoint(){
        refPoint = MouseInfo.getPointerInfo().getLocation();

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
