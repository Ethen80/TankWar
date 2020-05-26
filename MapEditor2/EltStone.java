package MapEditor2;

import java.awt.*;

public class EltStone extends Element {
    public EltStone(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawStone(graphics,x,y);
        super.draw(graphics);
    }

    @Override
    public Element clone() {
        return new EltStone(x,y);
    }

    @Override
    public String toString() {
        return "stone="+x/34+","+y/34;
    }
}
