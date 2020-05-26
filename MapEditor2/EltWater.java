package MapEditor2;

import java.awt.*;

public class EltWater extends Element {
    public EltWater(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawWater(graphics,x,y);
        super.draw(graphics);
    }

    @Override
    public Element clone() {
        return new EltWater(x,y);
    }
}
