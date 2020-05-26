package MapEditor2;

import java.awt.*;

public class Element {
    protected int x;
    protected int y;
    private boolean isSelected;
    public void draw(Graphics graphics){
        if(isSelected){
            Graphics2D g2=(Graphics2D)graphics;
            Stroke stroke=new BasicStroke(2.0f);
            g2.setStroke(stroke);
            g2.drawRect(x,y,ImageUtil.BLOCKW,ImageUtil.BLOCKW);
        }
    }
    public Element(int x,int y){
        this.x=x;
        this.y=y;
        isSelected=false;
    }
    public Element click(int x,int y){
        if(x>=this.x&&x<=this.x+ImageUtil.BLOCKW&&y>=this.y&&y<this.y+ImageUtil.BLOCKW){
            isSelected=true;
            return this;
        }else{
            isSelected=false;
            return null;
        }
    }
    public Element clone(){
        return null;
    }
}
