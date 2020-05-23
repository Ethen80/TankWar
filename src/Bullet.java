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
        //        double length=Math.sqrt(Math.pow(x-block.getX(),2)+Math.pow(y-block.getY(),2));
//        if(length<(width+block.getW())/2){
//            result=true;
//        }
        return result;
    }
    //    private int x;
//    private int y;
//    private int direction; //0-up 1-right 2-down 3-left
//    private int state=0; //0-normal 1-4 4 stages of explode
//    private Image img;
//
//    public Bullets(int x,int y,int direction,Image img){
//        this.x=x;
//        this.y=y;
//        this.direction=direction;
//        this.img=img;
//    }
//
//    public void move(){
//        if((state==0)){
//            switch (direction){
//                case 0: //up
//                    y=y-6;
//                    break;
//                case 1: //right
//                    x=x+6;
//                    break;
//                case 2: //down
//                    y=y+6;
//                    break;
//                case 3: //left
//                    x=x-6;
//                    break;
//            }
//            if(((x<10)||(x>685)||(y<35)||(y>485))){
//                state=1;
//            }
//        }
//        else if(!(state==0)){
//            state++;
//        }
//    }
//
//    public void draw(Graphics g){
//        if(state==0){
//            g.setColor(Color.WHITE);
//            g.drawOval(x-4,y-4,8,8);
//        }else if(state<4){
//            g.drawImage(img,x-17,y-17,x+17,y+17,34*15+(state*34),34*4,34*16+state*34,34*5,null);
//        }
//    }
//
//    public void drawPlayers(Graphics g){
//        if(state==0){
//            g.setColor(Color.WHITE);
//            g.drawRect(x-4,y-4,8,8);
//        }else if(state<4){
//            g.drawImage(img,x-17,y-17,x+17,y+17,34*15+(state*34),34*4,34*16+state*34,34*5,null);
//        }
//    }
//
//    public int getState() {
//        return state;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public int getR(){
//        return 4;
//    }
}
