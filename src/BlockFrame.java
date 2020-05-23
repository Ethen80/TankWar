import java.awt.*;

public class BlockFrame extends Block {
    @Override
    public void draw(Graphics g) {

    }
    public BlockFrame(int x,int y,int width,int height){
        super(x,y);
        this.x=x+width/2;
        this.y=y+height/2;
        this.width=width;
        this.height=height;
    }
}
