package MapEditor2;

import java.awt.*;

public abstract class Element {
    protected int x;
    protected int y;
    public abstract void draw(Graphics graphics);
    public Element(int x,int y){
        this.x=x;
        this.y=y;
    }
}
