package MapEditor2;

import java.awt.*;

public class EltPTank extends Element {
    protected int direction;

    public EltPTank(int x, int y,int d) {
        super(x, y);
        this.direction=d;
    }

    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawPTank(graphics,this);
        super.draw(graphics);
    }

    @Override
    public Element clone() {
        return new EltPTank(x,y,direction);
    }
}
