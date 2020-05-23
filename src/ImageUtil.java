import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static final int  BLOCKW=34;
    public static final int GRASS=0;
    public static ImageUtil instance=null;
    private Image img = null;
    private int imgX=0;
    private int imgY=0;
    private int[][][] tankX=new int[8][4][2];
    private int[] tankY={0,0,0,0,34,34,34,34};
    private int[][] mapBlockXY={{136,238,170,272}};
    private int[][][] cartoonXY={{{544,136},{578,136},{612,136},{646,136}},
            {{680,136},{714,136},{748,136},{-34,-34}},
            {{442,238},{510,238},{476,238},{-34,-34}}};
    private int[] blockGrass = {136,238,170,272};
    private int[] blockBrick = {612,170,629,187};
    private int[] blockStone={0,204,17,221};
    private int[] blockWater={0,238,34,272};

    private ImageUtil(){
        File f = new File("F:\\Desktop\\EcliWorkSpace\\imageSrc\\robots_sprite.png");
        try {
            img = ImageIO.read(f);
        } catch (IOException e){
            e.printStackTrace();
        }
        for(int catagory=0;catagory<8;catagory++){
            for(int direction=0;direction<4;direction++){
                for (int frameState=0;frameState<2;frameState++){
                    tankX[catagory][direction][frameState]=((catagory%4)*8+direction*2+frameState)*BLOCKW;
                }
            }
        }
    }

    public static ImageUtil getInstance(){
        if(instance==null){
            instance=new ImageUtil();
        }
        return instance;
    }

    public void drawSpirit(Graphics g,Spirit spirit){
        if(spirit.getState()!=Spirit.DIE){
            if(spirit instanceof Tank){
                //draw Tank
                drawTank(g,(Tank)spirit);
            }else if(spirit instanceof Bullet){
                //draw Bullets
                drawBullets(g,(Bullet)spirit);
            }
        }
    }

    void drawTank(Graphics g, Tank tank){
        if(tank.getState()==Spirit.ALIVE){
            imgX=tankX[tank.getCatagory()][tank.getDirection()][tank.getFrameState()];
            imgY=tankY[tank.getCatagory()];
        }else if(tank.getState()==Spirit.EXPLODE){
            imgX=680+tank.getFrameState()*BLOCKW;
            imgY=136;
        }
        g.drawImage(img,tank.getX()-BLOCKW/2,tank.getY()-BLOCKW/2,tank.getX()+BLOCKW/2,tank.getY()+BLOCKW/2,imgX,imgY,imgX+BLOCKW,imgY+BLOCKW,null);
    }
    private void drawBullets(Graphics g, Bullet bullet){
        if(bullet.getState()==Spirit.ALIVE){
            imgX=170;
            imgY=204;
        }else if(bullet.getState()==Spirit.EXPLODE){
            imgX=544+bullet.getFrameState()*BLOCKW;
            imgY=136;
        }
        g.drawImage(img,bullet.getX()-BLOCKW/2,bullet.getY()-BLOCKW/2,bullet.getX()+BLOCKW/2,bullet.getY()+BLOCKW/2,imgX,imgY,imgX+BLOCKW,imgY+BLOCKW,null);
    }
    public void drawMapBlock(Graphics g,int x,int y,int blockIndex){
        g.drawImage(img,x-BLOCKW/2,y-BLOCKW/2,x+BLOCKW/2,y+BLOCKW/2,mapBlockXY[blockIndex][0],mapBlockXY[blockIndex][1],mapBlockXY[blockIndex][2],mapBlockXY[blockIndex][3],null);
    }
    public void drawCartoon(Graphics g,Cartoon obj){
        int[][] pos=new int[4][2];
        pos[0][0]=obj.getX()-BLOCKW/2;
        pos[0][1]=obj.getY()-BLOCKW/2;
        pos[1][0]=pos[0][0]+BLOCKW;
        pos[1][1]=pos[0][1]+BLOCKW;
        pos[2][0]=cartoonXY[obj.getCartoonStyle()][obj.getFrameNumber()][0];
        pos[2][1]=cartoonXY[obj.getCartoonStyle()][obj.getFrameNumber()][1];
        pos[3][0]=pos[2][0]+BLOCKW;
        pos[3][1]=pos[2][1]+BLOCKW;

        g.drawImage(img,pos[0][0],pos[0][1],pos[1][0],pos[1][1],pos[2][0],pos[2][1],pos[3][0],pos[3][1],null);
    }
    public void drawGrass(Graphics g,int x,int y){
        g.drawImage(img,x-BLOCKW/2,y-BLOCKW/2,x+BLOCKW/2,y+BLOCKW/2,
                blockGrass[0],blockGrass[1],blockGrass[2],blockGrass[3],null);
    }
    public void drawBrick(Graphics g,int x,int y){
        g.drawImage(img,x-BLOCKW/2,y-BLOCKW/2,x,y,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
        g.drawImage(img,x,y-BLOCKW/2,x+BLOCKW/2,y,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
        g.drawImage(img,x-BLOCKW/2,y,x,y+BLOCKW/2,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
        g.drawImage(img,x,y,x+BLOCKW/2,y+BLOCKW/2,
                blockBrick[0],blockBrick[1],blockBrick[2],blockBrick[3],null);
    }
    //绘制水块图块
    public void drawWater(Graphics g,int x,int y,int state) {
        g.drawImage(img, x-BLOCKW/2, y-BLOCKW/2,
                x+BLOCKW/2, y+BLOCKW/2,
                blockWater[0]+state*BLOCKW, blockGrass[1],
                blockWater[2]+state*BLOCKW, blockGrass[3], null);
    }
    //绘制石块图块
    public void drawStone(Graphics g,int x,int y) {
        g.drawImage(img, x-BLOCKW/2, y-BLOCKW/2,
                x, y,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
        g.drawImage(img, x, y-BLOCKW/2,
                x+BLOCKW/2, y,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
        g.drawImage(img, x-BLOCKW/2, y,
                x, y+BLOCKW/2,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
        g.drawImage(img, x, y,
                x+BLOCKW/2, y+BLOCKW/2,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
    }

}


//TODO repair the problem of the losing frame of Tank

