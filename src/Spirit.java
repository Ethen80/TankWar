import java.awt.*;


public class Spirit {
    public static final int ALIVE = 0;
    public static final int EXPLODE = 1;
    public static final int DIE = 2;

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    protected int x;
    protected int y;
    protected int width;
    protected int velocity;
    protected int catagory;
    private int state;
    protected int frameState;
    protected int direction;

    protected int ailveFrameCount;
    protected int explodeFrameCount;

    public Spirit(int x, int y) {
        this.x = x;
        this.y = y;
        width = 34;
        velocity = 5;
        direction = UP;
        state = ALIVE;
        frameState = 0;
        ailveFrameCount = 0;
        explodeFrameCount = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCatagory() {
        return catagory;
    }

    public int getDirection() {
        return direction;
    }

    public int getFrameState() {
        return frameState;
    }

    public int getState() {
        return state;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getWidth() {
        return width;
    }

    public void setState(int state) {
        this.state = state;
        frameState = 0;
    }

    public void setFrameState(int frameState) {
        this.frameState = frameState;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setCatagory(int catagory) {
        this.catagory = catagory;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move() {
        if (state == ALIVE) {
            switch (direction) {  //set direction as the tag
                case UP:
                    y -= velocity;
                    break;
                case RIGHT:
                    x += velocity;
                    break;
                case DOWN:
                    y += velocity;
                    break;
                case LEFT:
                    x -= velocity;
                    break;
            }
        }
    }

    public void draw(Graphics g) {
        ImageUtil.getInstance().drawSpirit(g, this);
    }

    public boolean isCollide(Spirit spirit) {
        boolean result = false;
        if (state == ALIVE) {
            double length = Math.sqrt(Math.pow(x - spirit.getX(), 2) + Math.pow(y - spirit.getY(), 2));
            if (length < ((width + spirit.width) / 2)) {
                result = true;
            }
        }
        return result;
    }

    public void calculateData() {
        switch (state) {
            case Spirit.ALIVE:
                frameState++;
                if (frameState == ailveFrameCount) {
                    frameState = 0;
                }
                break;
            case Spirit.EXPLODE:
                frameState++;
                if (frameState == explodeFrameCount) {
                    state = Spirit.DIE;
                }
                break;
        }
    }
}
//    public boolean isCollide(){
//        boolean result=false;
//        switch(direction){
//            case UP:
//                if(y-velocity<width/2){
//                    result=true;
//                }
//                break;
//            case RIGHT:
//                if(x+velocity>MainFrame.WIDTH-width/2){
//                    result=true;
//                }
//                break;
//            case DOWN:
//                if(y+velocity>MainFrame.HEIGHT-width/2){
//                    result=true;
//                }
//                break;
//            case LEFT:
//                if(x-velocity<width/2){
//                    result=true;
//                }
//                break;
//        }
//        return result;
//    }
//}
