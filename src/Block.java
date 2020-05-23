import java.awt.*;

public abstract class Block {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    public Block(int x,int y) {
        this.x = x*ImageUtil.BLOCKW+ImageUtil.BLOCKW/2;
        this.y = y*ImageUtil.BLOCKW+ImageUtil.BLOCKW/2;
        width = ImageUtil.BLOCKW;
        height=ImageUtil.BLOCKW;
    }

    public abstract void draw(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int width) {
        this.width = width;
    }

    public int getH() {
        return height;
    }

    public void setH(int height) {
        this.height = height;
    }
}
