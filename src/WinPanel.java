import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class WinPanel extends JPanel implements KeyListener {
    private MainFrame mainFrame;
    private Image img;

    private PlayerTank tank;
    private int choice;
    private int[][] tankPos={{280,352},{280,380}};

    public WinPanel(MainFrame mainFrame) throws IOException {
        super();
        this.mainFrame=mainFrame;
        File file=new File("F:\\Desktop\\EcliWorkSpace\\imageSrc\\game_win.png");
        img=ImageIO.read(file);
        choice=0;
        tank=new PlayerTank(tankPos[choice][0],tankPos[choice][1]);
        tank.setDirection(Spirit.RIGHT);
    }
    public void  paint(Graphics g){
        g.drawImage(img,0,0,MainFrame.WIDTH,MainFrame.HEIGHT,
                0,0,MainFrame.WIDTH,MainFrame.HEIGHT,null);
        ImageUtil.getInstance().drawTank(g,tank);
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key=keyEvent.getKeyCode();
        switch (key){
            case KeyEvent.VK_ENTER:
                if(choice==0) {
                    mainFrame.removeKeyListener(this);
                    mainFrame.nextGame();
                }else{
                    mainFrame.removeKeyListener(this);
                    mainFrame.startGame();
                }
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                choice=(choice+1)%2;
                tank.setX(tankPos[choice][0]);
                tank.setY(tankPos[choice][1]);
                repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
