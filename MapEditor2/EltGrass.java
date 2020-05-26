package MapEditor2;

import java.awt.*;

public class EltGrass extends Element {
    public EltGrass(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawGrass(graphics,x,y);
        super.draw(graphics);
    }

    @Override
    public Element clone() {
        return new EltGrass(x,y);
    }
}
