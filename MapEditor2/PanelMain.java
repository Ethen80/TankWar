package MapEditor2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class PanelMain extends JPanel implements MouseMotionListener, MouseListener {
    private FrameMain frameMain;
    private int pTankCount=5;
    private int sTankCount=1;
    private int sTankTimeCount=100;
    private ArrayList<EltSTank> eltSTanks=new ArrayList<EltSTank>();
    private ArrayList<EltPTank> eltPTanks=new ArrayList<EltPTank>();
    private ArrayList<Element> elements=new ArrayList<Element>();
    public PanelMain(FrameMain frameMain){
        this.frameMain=frameMain;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
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
        for(int i=elements.size()-1;i>=0;i--){
            elements.get(i).draw(g);
        }
        for(int i=eltPTanks.size()-1;i>=0;i--){
            eltPTanks.get(i).draw(g);
        }
        for(int i=eltSTanks.size()-1;i>=0;i--){
            eltSTanks.get(i).draw(g);
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Element element;
        element=Operation.getInstance().getElement();
        if(element!=null){
            if(element instanceof EltPTank){
                if(eltPTanks.size()<2){
                    EltPTank addElt=(EltPTank)element.clone();
                    addElt.x=mouseEvent.getX()/34*34;
                    addElt.y=mouseEvent.getY()/34*34;
                    eltPTanks.add(addElt);
                }
            }else if(element instanceof EltSTank){
                if(eltSTanks.size()<2){
                    EltSTank addElt = (EltSTank)element.clone();
                    addElt.x=mouseEvent.getX()/34*34;
                    addElt.y=mouseEvent.getY()/34*34;
                    eltSTanks.add(addElt);
                }
            }else {
                Element addElt= element.clone();
                addElt.x=mouseEvent.getX()/34*34;
                addElt.y=mouseEvent.getY()/34*34;
                elements.add(addElt);
            }
            frameMain.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
