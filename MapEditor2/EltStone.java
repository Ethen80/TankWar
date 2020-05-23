package MapEditor2;

import java.awt.*;

public class EltStone extends Element {
    public EltStone(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawStone(graphics,x,y);
    }
}
