package MapEditor2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.ArrayList;

public class PanelMain extends JPanel implements MouseMotionListener, MouseListener {
    private FrameMain frameMain;
    private int pTankCount=5;
    private int sTankCount=1;
    private int sTankTimeCount=100;
    private ArrayList<EltSTank> eltSTanks=new ArrayList<EltSTank>();
    private ArrayList<EltPTank> eltPTanks=new ArrayList<EltPTank>();
    private EltBase eltBase;
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
        if(eltBase!=null)
            eltBase.draw(g);
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
        if(element!=null) {
            if (element instanceof EltPTank) {
                if (eltPTanks.size() < 2) {
                    EltPTank addElt = (EltPTank) element.clone();
                    addElt.x = mouseEvent.getX() / 34 * 34;
                    addElt.y = mouseEvent.getY() / 34 * 34;
                    eltPTanks.add(addElt);
                }
            } else if (element instanceof EltSTank) {
                if (eltSTanks.size() < 2) {
                    EltSTank addElt = (EltSTank) element.clone();
                    addElt.x = mouseEvent.getX() / 34 * 34;
                    addElt.y = mouseEvent.getY() / 34 * 34;
                    eltSTanks.add(addElt);
                }
            } else if (element instanceof EltSpade) {
                int tmpx, tmpy;
                tmpx = mouseEvent.getX() / 34 * 34;
                tmpy = mouseEvent.getY() / 34 * 34;
                for (int i = elements.size() - 1; i >= 0; i--) {
                    Element deleteElt = elements.get(i);
                    if (deleteElt.x == tmpx && deleteElt.y == tmpy) {
                        elements.remove(i);
                        break;
                    }
                }
            }else if(element instanceof EltBase){
                if(eltBase==null){
                    eltBase=(EltBase) element.clone();
                    eltBase.x=mouseEvent.getX()/34*34;
                    eltBase.y=mouseEvent.getY()/34*34;
                }
            }else {
                Element addElt = element.clone();
                addElt.x = mouseEvent.getX() / 34 * 34;
                addElt.y = mouseEvent.getY() / 34 * 34;
                elements.add(addElt);
            }
        }
        frameMain.repaint();
    }

    public void saveMap(File file) throws FileNotFoundException {
        //以下代码将地图元素坐标字符串存储到文件中
        OutputStream out = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(out);
        BufferedWriter bw = new BufferedWriter(writer);
        PrintWriter pw = new PrintWriter(bw,true);
        //存储设置值
        pw.println("pTankCount=5");
        pw.println("sTankCount=1");
        pw.println("sTankTimeCount=100");
        //存储且只存储两个玩家坦克的位置
        for(int i=0;i<eltPTanks.size();i++) {
            pw.println(eltPTanks.get(i).toString());
        }
        for(int i=0;i<2-eltPTanks.size();i++) {
            pw.println("pTankPos=0,0,0");
        }
        //存储且只存储两个精灵坦克的位置
        for(int i=0;i<eltSTanks.size();i++) {
            pw.println(eltSTanks.get(i).toString());
        }
        for(int i=0;i<2-eltSTanks.size();i++) {
            pw.println("sTankPos=0,0,0");
        }
        pw.println(eltBase.toString());
        //存储地图图块的坐标
        for(int i=0;i<elements.size();i++) {
            pw.println(elements.get(i).toString());
        }

        pw.close();
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
