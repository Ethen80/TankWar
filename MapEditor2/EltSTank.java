package MapEditor2;

import java.awt.*;

public class EltSTank extends Element{
    protected int direction;

    public EltSTank(int x, int y,int direction) {
        super(x, y);
        this.direction=direction;
    }

    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawSTank(graphics,this);
        super.draw(graphics);
    }

    @Override
    public Element clone() {
        return new EltSTank(x,y,direction);
    }

    @Override
    public String toString() {
        return "sTankPos="+(x+17)+","+(y+17)+","+direction;
    }
}
