import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MidpointLine_Drawing extends JPanel {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public MidpointLine_Drawing() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);

        setLayout(new FlowLayout());

        JTextField x1Field = new JTextField(5);
        JTextField y1Field = new JTextField(5);
        JTextField x2Field = new JTextField(5);
        JTextField y2Field = new JTextField(5);

        JButton drawButton = new JButton("Draw Line");
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                x1 = Integer.parseInt(x1Field.getText());
                y1 = Integer.parseInt(y1Field.getText());
                x2 = Integer.parseInt(x2Field.getText());
                y2 = Integer.parseInt(y2Field.getText());
                repaint();
            }
        });

        add(new JLabel("x1:"));
        add(x1Field);
        add(new JLabel("y1:"));
        add(y1Field);
        add(new JLabel("x2:"));
        add(x2Field);
        add(new JLabel("y2:"));
        add(y2Field);
        add(drawButton);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    y1 -= 10;
                    y2 -= 10;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    y1 += 10;
                    y2 += 10;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    x1 -= 10;
                    x2 -= 10;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    x1 += 10;
                    x2 += 10;
                    repaint();
                }
            }
        });

        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        drawLine(g, x1, y1, x2, y2);
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            g.fillRect(x1, getHeight() - y1 - 1, 1, 1);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MidpointLine_Drawing panel = new MidpointLine_Drawing();
        frame.add(panel);

        frame.setVisible(true);
        panel.requestFocus();
    }
}
