import java.awt.*;

public class Bullet extends Spirit {
    public Bullet(int x, int y, int direction){
        super(x,y);
        super.setDirection(direction);
        velocity=10;
        width=14;
        explodeFrameCount=3;
    }

    public boolean isCollide(Block block){
        boolean result=false;
        Rectangle rect1=new Rectangle(x-width/2,y-width/2,width,width);
        Rectangle rect2=new Rectangle(block.getX()-block.getW()/2,block.getY()-block.getH()/2,block.getW(),block.getH());
        if(rect1.intersects(rect2)){
            result=true;
        }
        return result;
    }
}
