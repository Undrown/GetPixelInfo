import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class root {
    private JPanel rootPanel;
    private JPanel pixelInfoPanel;
    private JTextField refLabel;
    private JTextField xLabel;
    private JTextField yLabel;
    private JTextField colorLabel;
    private JButton startButton;
    private javax.swing.Timer timer;
    private final int tps = 10;

    public root() {
        timer = new javax.swing.Timer((int)(1000/tps), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("tick");
            }
        });
        timer.start();
        rootPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '1'){
                    System.out.println("1!!!");
                }
                if (e.getKeyChar() == '2'){
                    System.out.println("2!!!");
                }
                System.out.println(e.getID());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("root");
        frame.setContentPane(new root().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
