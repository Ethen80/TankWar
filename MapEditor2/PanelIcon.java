package MapEditor2;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PanelIcon extends JPanel implements MouseListener {
    private FrameMain frameMain;
    private ArrayList<Element> elements=new ArrayList<Element>();
    private Element eltSelected;      //记录当前选择的元素
    public PanelIcon(FrameMain frameMain) {
        this.frameMain = frameMain;
        eltSelected = null;

        elements.add(new EltPTank(10,50,0));
        elements.add(new EltPTank(50,50,1));
        elements.add(new EltPTank(10,100,2));
        elements.add(new EltPTank(50,100,3));

        elements.add(new EltSTank(10,150,0));
        elements.add(new EltSTank(50,150,1));
        elements.add(new EltSTank(10,200,2));
        elements.add(new EltSTank(50,200,3));

        elements.add(new EltGrass(10,250));
        elements.add(new EltStone(50,250));
        elements.add(new EltBrick(10,300));
        elements.add(new EltWater(50,300));
        elements.add(new EltSpade(10,350));
        elements.add(new EltBase(50,350));
        this.addMouseListener(this);
    }
    public Element getEltSelected() {
        return eltSelected;
    }
    public void initPanel() {
        setBorder(new EtchedBorder());
        Label label = new Label("---E L E M E N T S---");
        add(label);
    }
    public void paint(Graphics g) {
        for(int i=0;i<elements.size();i++) {
            elements.get(i).draw(g);
        }
    }
    @Override
    public void mouseClicked(MouseEvent arg0) {
        Element elt=null;
        for(int i=0;i<elements.size();i++){
            Element tmpElt;
            tmpElt=elements.get(i).click(arg0.getX(),arg0.getY());
            if(tmpElt!=null){
                elt=tmpElt;
            }
        }
        Operation.getInstance().setElement(elt);
        frameMain.repaint();
    }
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }
}

