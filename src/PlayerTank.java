import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PlayerTank extends Tank {
    public PlayerTank(int x, int y) throws IOException {
        super(x, y);
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
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
    }
}
