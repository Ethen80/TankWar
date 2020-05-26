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
    public Element clone(){
        return new EltBrick(x,y);
    }

    @Override
    public String toString() {
        return "brick="+x/34+","+y/34;
    }
}
