import java.awt.*;

public class Tank extends Spirit {
    public Tank(int x,int y) {
        super(x, y);
        ailveFrameCount = 2;
        explodeFrameCount = 3;
    }
    public Bullet fire(){
        Bullet bullet=null;
        if(getState()==Spirit.ALIVE){
            int bullteX;
            int bullteY;
            bullteX=x;
            bullteY=y;
            switch (getDirection()){
                case Spirit.UP:
                    bullteY-=width/2;
                    break;
                case Spirit.RIGHT:
                    bullteX+=width/2;
                    break;
                case Spirit.DOWN:
                    bullteY+=width/2;
                    break;
                case Spirit.LEFT:
                    bullteX-=width/2;
                    break;
            }
            bullet = new Bullet(bullteX,bullteY,direction);
        }
        return bullet;
    }

    /***
     *
     * @param block
     * @return
     */
    public boolean isCollide(Block block){
        boolean result=false;
        int tempX,tempY;
        tempX=x;
        tempY=y;
        switch(direction){
            case UP:
                tempY=tempY-velocity;
                break;
            case RIGHT:
                tempX=tempX+velocity;
                break;
            case DOWN:
                tempY=tempY+velocity;
                break;
            case LEFT:
                tempX=tempX-velocity;
                break;
        }
        Rectangle rect1=new Rectangle(tempX-width/2,tempY-width/2,width,width);
        Rectangle rect2=new Rectangle(block.getX()-block.getW()/2,block.getY()-block.getH()/2,block.getW()-5,block.getH()-5);
        if(rect1.intersects(rect2)){
            result = true;
        }
        return result;
    }

//TODO repaire the problem of the birth of spirit tanks
    //    protected int x;
//    protected int y; //坦克在窗体上的坐标
//    private int imgX,imgY; //坦克在资源上的坐标
//    private int catagory = 0;
//    protected int direction = 0;   //0-up  1-right  2-down  3-left
//    private int flag;
//    protected Image img;
//    protected int state=0; //0-normal 1-3-four stages of explode
//
//    public Tank(int x,int y,Image img) throws IOException {
//        super();
//        this.x = x;
//        this.y = y;
//        this.img = img;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//    }
//
//    private void cauculateImgPosition(){
//        imgX=catagory*68*4+direction*68;
//        imgY=catagory/4*34;
//        if(imgY==34){
//            imgX=imgX-68*16;
//        }
//    }
//    public void setCatagory(int catagory) {
//        this.catagory = catagory;
//        cauculateImgPosition();
//    }
//
//    public void setDirection(int direction) {
//        this.direction = direction;
//        cauculateImgPosition();
//    }
//
//    public void move(){
//        if(state!=0){
//            return;
//        }
//        switch (direction){
//            case 0: //up
//                y=y-2;
//                break;
//            case 1: //right
//                x=x+2;
//                break;
//            case 2: //down
//                y=y+2;
//                break;
//            case 3: //left
//                x=x-2;
//                break;
//        }
//    }
//
//    public void draw(Graphics g){
//        if(state==0) {
//            flag = (flag + 1) % 2;
//            if (flag == 0) {
//                g.drawImage(img, x - 17, y - 17, x + 17, y + 17, imgX, imgY, imgX + 34, imgY + 34, null);
//            } else {
//                g.drawImage(img, x - 17, y - 17, x + 17, y + 17, imgX + 34, imgY, imgX + 68, imgY + 34, null);
//            }
//        }else if(state<=3){
//            g.drawImage(img,x-17,y-17,x+17,y+17,(19+state)*34,34*4,(20+state)*34,34*5,null);
//            state++;
//        }
//    }
//    public Bullets fire(){
//        if(state!=0){
//            return null;
//        }
//        Bullets bullet=null;
//        int bullteX;
//        int bullteY;
//        bullteX=x;
//        bullteY=y;
//        switch (direction){
//            case 0:
//                bullteY-=17;
//                break;
//            case 1:
//                bullteX+=17;
//                break;
//            case 2:
//                bullteY+=17;
//                break;
//            case 3:
//                bullteX-=17;
//                break;
//        }
//        bullet = new Bullets(bullteX,bullteY,direction,img);
//        return bullet;
//    }
//
//    public boolean isCollide(Bullets bullets){
//        if(state!=0) return false;
//        if (bullets.getX() > x - 17 - bullets.getR() && (bullets.getX() < x + 17 + bullets.getR()) && (bullets.getY() > y - 17 - bullets.getR()) &&
//                (bullets.getY() < y + 17 + bullets.getR())) {
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    public int getState() {
//        return state;
//    }
}
