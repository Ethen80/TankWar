import java.awt.*;

public class Base extends Block{
    public static final int BASE_ALIVE=0;
    public static final int BASE_DIE=1;
    public int state;
    protected int x;
    protected int y;

    public Base(int x,int y){
        super(x,y);
        this.state=BASE_ALIVE;
    }

    @Override
    public void draw(Graphics g) {
        ImageUtil.getInstance().drawBase(g,this);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
