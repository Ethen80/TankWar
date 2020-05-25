package MapEditor2;

import java.awt.*;

public class EltBrick extends Element {

    public EltBrick(int x,int y){
        super(x,y);
    }
    @Override
    public void draw(Graphics graphics) {
        ImageUtil.getInstance().drawBrick(graphics,x,y);
        super.draw(graphics);
    }
}
