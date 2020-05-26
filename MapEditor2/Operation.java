package MapEditor2;

import java.awt.*;

public class Operation {
    private int x;
    private int y;
    private  Element element=null;
    private static Operation instance=null;

    private Operation(){
    }
    public static Operation getInstance(){
        if(instance==null){
            instance=new Operation();
        }
        return instance;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setElement(Element element) {
        this.element = element;
    }
    public void draw(Graphics graphics){
        int tmpx,tmpy;
        if(element!=null){
            tmpx=element.x;
            tmpy=element.y;
            element.x=x/34*34;
            element.y=y/34*34;
            element.draw(graphics);
            element.x=tmpx;
            element.y=tmpy;
        }
    }
}
