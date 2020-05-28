import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PlayerTank extends Tank {
    private int playerCode;
    protected int deathTime;

    public PlayerTank(int x, int y, int playerCode) throws IOException {
        super(x, y);
        this.playerCode = playerCode;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (playerCode == 0) {
            switch (key) {
                case KeyEvent.VK_UP:
                    setDirection(Spirit.UP);
                    break;
                case KeyEvent.VK_RIGHT:
                    setDirection(Spirit.RIGHT);
                    break;
                case KeyEvent.VK_LEFT:
                    setDirection(Spirit.LEFT);
                    break;
                case KeyEvent.VK_DOWN:
                    setDirection(Spirit.DOWN);
                    break;
            }
        } else {
            switch (key) {
                case KeyEvent.VK_W:
                    setDirection(Spirit.UP);
                    break;
                case KeyEvent.VK_D:
                    setDirection(Spirit.RIGHT);
                    break;
                case KeyEvent.VK_A:
                    setDirection(Spirit.LEFT);
                    break;
                case KeyEvent.VK_S:
                    setDirection(Spirit.DOWN);
                    break;
            }
        }
    }
}
