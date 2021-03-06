import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.io.File;

public class root{
    private JPanel rootPanel;
    private JTextField refLabel;
    private JTextField xLabel;
    private JTextField yLabel;
    private JTextField colorLabel;
    private JButton startButton;
    private JTextArea fixList;
    private JButton button1;
    private JLabel rgbColorLabel;
    private javax.swing.Timer timer;
    private Point point;
    private Point refPoint;
    private ArrayList <Point> fixPointArray;
    private java.awt.Robot robot;
    private java.awt.Color color;

    private root(){
        fixPointArray = new ArrayList<>();
        refPoint = new Point(0,0);
        //robot init
        try {
            robot = new Robot();
            robot.setAutoDelay(100);
            robot.setAutoWaitForIdle(true);
        }
        catch (Exception e) {
            System.out.println("Error with Robot...");
        }

        point = new Point(1,1);
        int tps = 10;
        timer = new javax.swing.Timer(1000/ tps, e -> {
            point = MouseInfo.getPointerInfo().getLocation();
            color = robot.getPixelColor(point.x, point.y);
            String color_string = String.format(
                    "(R:%03d   G:%03d   B:%03d)",
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue());
            xLabel.setText(String.valueOf(point.x - refPoint.x));
            yLabel.setText(String.valueOf(point.y - refPoint.y));
            colorLabel.setText(color_string);
            rgbColorLabel.setText(String.valueOf(color.getRGB()));
            //fixPoint
            fixList.setText("");
            fixPointArray.forEach(p -> {
                Color clr = robot.getPixelColor(p.x, p.y);
                fixList.append(String.format(
                    "X:%d, Y:%d, R:%03d   G:%03d   B:%03d\n%d\n",
                    p.x - refPoint.x,
                    p.y - refPoint.y,
                    clr.getRed(),
                    clr.getGreen(),
                    clr.getBlue(),
                    clr.getRGB()));
            });
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
                    case KeyEvent.VK_F2:
                        unfixPoint();
                }
            return false;
        });
        button1.addActionListener(e -> {
            String s = JOptionPane.showInputDialog(rootPanel,
                    "x,y","0,0");
            String[] ss = s.split(",",2);
            fixRefPoint(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
        });
    }

    private void fixPoint(){
        fixPointArray.add(MouseInfo.getPointerInfo().getLocation());
    }
    private void unfixPoint(){
        fixPointArray.remove(fixPointArray.size()-1);
    }

    private void fixRefPoint(int x, int y){
        refPoint.x = x;
        refPoint.y = y;
        refLabel.setText(String.format("x:%03d, y:%03d", refPoint.x, refPoint.y));
    }

    private void fixRefPoint(){
        try{
            refPoint = MouseInfo.getPointerInfo().getLocation();
            refLabel.setText(String.format("x:%03d, y:%03d", refPoint.x, refPoint.y));
            //screen rect 10x10 > refPoint.png
            Rectangle rect = new Rectangle();
            rect.x = refPoint.x;
            rect.y = refPoint.y;
            rect.height = 10;
            rect.width = 10;
            ImageIO.write(robot.createScreenCapture(rect), "png", new File("refPoint.png"));
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
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
