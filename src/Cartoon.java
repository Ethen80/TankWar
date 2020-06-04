import java.awt.*;

public class Cartoon {
    public static final int BEXPLODE=0;
    public static final int TEXPLODE=1;
    public static final int TCREAT=2;

    private int x;
    private int y;
    private int cartoonStyle;
    private int frameCount;
    private int frameNumber;
    private int repeatTime;
    private int playerCode;

    public FinishListener finishListener;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCartoonStyle() {
        return cartoonStyle;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Cartoon(int style, int x, int y) {
        this.x=x;
        this.y=y;
        cartoonStyle=style;
        frameNumber=0;
        repeatTime=1;
        finishListener=null;
        switch (style){
            case BEXPLODE:
                frameCount=4;
                break;
            case TEXPLODE:
                frameCount=3;
                break;
            case TCREAT:
                frameCount=3;
                repeatTime=10;
                break;
        }
    }
    public Cartoon(int style, int x, int y,int playerCode) {
        this.x=x;
        this.y=y;
        this.playerCode=playerCode;
        cartoonStyle=style;
        frameNumber=0;
        repeatTime=1;
        finishListener=null;
        switch (style){
            case BEXPLODE:
                frameCount=4;
                break;
            case TEXPLODE:
                frameCount=3;
                break;
            case TCREAT:
                frameCount=3;
                repeatTime=10;
                break;
        }
    }
    public void addFinishListener(FinishListener finishListener){
        this.finishListener=finishListener;
    }
    public void calculateData(){
        if(repeatTime!=0){
            frameNumber++;
            if(frameNumber==frameCount){
                repeatTime--;
                frameNumber=0;
                if(repeatTime==0&&finishListener!=null){
                    finishListener.doFinish(this.playerCode);
                }
            }
        }

    }
    public boolean isDead(){
        return repeatTime==0;
    }
    public void draw(Graphics g){
        if(repeatTime!=0){
            ImageUtil.getInstance().drawCartoon(g,this);
        }

    }
}
