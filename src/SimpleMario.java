import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimpleMario extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    Player player;
    ArrayList<Rectangle> platforms;
    boolean left, right, jump;

    public SimpleMario() {
        player = new Player(100, 300, 40, 50);
        platforms = new ArrayList<>();

        // Ground and some platforms
        platforms.add(new Rectangle(0, 400, 800, 100));         // ground
        platforms.add(new Rectangle(200, 350, 100, 20));         // platform 1
        platforms.add(new Rectangle(400, 300, 100, 20));         // platform 2
        platforms.add(new Rectangle(600, 250, 100, 20));         // platform 3

        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        // Movement
        if (left) player.x -= 4;
        if (right) player.x += 4;

        // Apply gravity
        if (!player.onGround) player.velocityY += 1;
        else player.velocityY = 0;

        player.y += player.velocityY;
        player.onGround = false;

        // Platform collision
        for (Rectangle plat : platforms) {
            if (player.getBounds().intersects(plat)) {
                Rectangle intersect = player.getBounds().intersection(plat);

                // Coming down onto a platform
                if (intersect.height < intersect.width && player.velocityY >= 0) {
                    player.y = plat.y - player.height;
                    player.onGround = true;
                    break;
                }
            }
        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(135, 206, 235)); // sky blue

        // Draw player
        g.setColor(Color.RED);
        g.fillRect(player.x, player.y, player.width, player.height);

        // Draw platforms
        g.setColor(Color.GRAY);
        for (Rectangle plat : platforms) {
            g.fillRect(plat.x, plat.y, plat.width, plat.height);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;

        if (e.getKeyCode() == KeyEvent.VK_SPACE && player.onGround) {
            player.velocityY = -15;
            player.onGround = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Mario Game");
        SimpleMario game = new SimpleMario();

        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }

    // Inner class for player
    class Player {
        int x, y, width, height;
        int velocityY = 0;
        boolean onGround = false;

        public Player(int x, int y, int w, int h) {
            this.x = x; this.y = y;
            this.width = w; this.height = h;
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, width, height);
        }
    }
}
