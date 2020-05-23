import java.awt.*;

public class BlockGrass extends Block {
    public BlockGrass(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getInstance().drawGrass(g,this.x,this.y);
    }
}
