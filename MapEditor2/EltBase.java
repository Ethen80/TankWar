package MapEditor2;

import java.awt.*;

public class EltBase extends Element {
    public EltBase(int x, int y) {
        super(x, y);
    }
    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawBase(graphics,x,y);
        super.draw(graphics);
    }
    public Element clone(){
        return new EltBase(x,y);
    }

    @Override
    public String toString() {
        return "base="+x/34+","+y/34;
    }
}
