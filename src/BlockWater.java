import java.awt.*;

public class BlockWater extends Block {

    private int frameState;

    public BlockWater(int x, int y) {
        super(x, y);
        this.frameState=0;
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getInstance().drawWater(g,x,y,frameState);
        frameState=(frameState+1)%2;
    }
}
