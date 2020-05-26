package MapEditor2;

import java.awt.*;

public class EltSpade extends Element {
    public EltSpade(int x, int y) {
        super(x, y);
    }
    public void draw(Graphics graphics){
        ImageUtil.getInstance().drawSpade(graphics,x,y);
        super.draw(graphics);
    }
}
