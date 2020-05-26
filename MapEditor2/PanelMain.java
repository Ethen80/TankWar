package MapEditor2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PanelMain extends JPanel implements MouseMotionListener {
    private FrameMain frameMain;
    public PanelMain(FrameMain frameMain){
        this.frameMain=frameMain;
        this.addMouseMotionListener(this);
    }
    public void paint(Graphics g){
        super.paint(g);
//        g.drawString("EditorPanel",200,200);
        g.setColor(Color.black);
        g.fillRect(0,0,800,600);
        g.setColor(Color.GRAY);
        for(int i=1;i<=800/34;i++){
            g.drawLine(i*34,0,i*34,600);
        }
        for(int i=1;i<=600/34;i++){
            g.drawLine(0,i*34,800,i*34);
        }
        Operation.getInstance().draw(g);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Operation.getInstance().setX(mouseEvent.getX());
        Operation.getInstance().setY(mouseEvent.getY());
        frameMain.repaint();
    }
}
