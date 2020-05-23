package MapEditor2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static final int BLOCKW=34;

    private static ImageUtil instance=null;
    private Image img = null;

    private int blockGrass[] = {136,238,170,272};
    private int blockBrick[] = {612,170,629,187};
    private int blockStone[] = {0,204,17,221};
    private int blockWater[] = {0,238,34,272};

    private ImageUtil() {
        File f = new File("robots_sprite.png");//建立文件对象
        try {//文件操作放在错误处理块中
            img = ImageIO.read(f);//从文件中读取图像
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ImageUtil getInstance() {
        if(instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }

    public void drawPTank(Graphics g,EltPTank tank) {
        g.drawImage(img, tank.x, tank.y, tank.x+BLOCKW, tank.y+BLOCKW,
                tank.direction*68, 34, tank.direction*68+BLOCKW, 68, null);
    }

    public void drawSTank(Graphics g,EltSTank tank) {
        g.drawImage(img, tank.x, tank.y, tank.x+BLOCKW, tank.y+BLOCKW,
                tank.direction*34, 0, tank.direction*34+BLOCKW, 34, null);
    }

    //绘制草地图块
    public void drawGrass(Graphics g,int x,int y) {
        g.drawImage(img, x, y,x+BLOCKW, y+BLOCKW,
                blockGrass[0], blockGrass[1],
                blockGrass[2], blockGrass[3], null);
    }
    //绘制水块图块
    public void drawWater(Graphics g,int x,int y) {
        g.drawImage(img, x, y,x+BLOCKW, y+BLOCKW,
                blockWater[0]+BLOCKW, blockWater[1],
                blockWater[2]+BLOCKW, blockWater[3], null);
    }
    //绘制石块图块
    public void drawStone(Graphics g,int x,int y) {
        g.drawImage(img, x, y,x+BLOCKW/2, y+BLOCKW/2,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
        g.drawImage(img, x+BLOCKW/2, y,
                x+BLOCKW, y+BLOCKW/2,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
        g.drawImage(img, x, y+BLOCKW/2,
                x+BLOCKW/2, y+BLOCKW,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
        g.drawImage(img, x+BLOCKW/2, y+BLOCKW/2,
                x+BLOCKW, y+BLOCKW,
                blockStone[0], blockStone[1],
                blockStone[2], blockStone[3], null);
    }
    //绘制砖块图块,图块较小,需要绘制4个
    public void drawBrick(Graphics g,int x,int y) {
        g.drawImage(img, x, y,
                x+BLOCKW/2, y+BLOCKW/2,
                blockBrick[0], blockBrick[1],
                blockBrick[2], blockBrick[3], null);
        g.drawImage(img, x+BLOCKW/2, y,
                x+BLOCKW, y+BLOCKW/2,
                blockBrick[0], blockBrick[1],
                blockBrick[2], blockBrick[3], null);
        g.drawImage(img, x, y+BLOCKW/2,
                x+BLOCKW/2, y+BLOCKW,
                blockBrick[0], blockBrick[1],
                blockBrick[2], blockBrick[3], null);
        g.drawImage(img, x+BLOCKW/2, y+BLOCKW/2,
                x+BLOCKW, y+BLOCKW,
                blockBrick[0], blockBrick[1],
                blockBrick[2], blockBrick[3], null);
    }
}

