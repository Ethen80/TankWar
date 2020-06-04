import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private int sTankTime;
    private int sTankCount;
    private int pTankCount;
    private int sTankTimeCount;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    //将整个窗口划分为34*34的小图块格，共，23*17个图块格，以下这些数组记录图块格的行列数
    //砖块数组
    private int[][] arrFrame={
            {0,-ImageUtil.BLOCKW,MainFrame.WIDTH,ImageUtil.BLOCKW},
            {MainFrame.WIDTH,0,ImageUtil.BLOCKW,MainFrame.HEIGHT},
            {0,MainFrame.HEIGHT,MainFrame.WIDTH,ImageUtil.BLOCKW},
            {-ImageUtil.BLOCKW,ImageUtil.BLOCKW,MainFrame.HEIGHT}
    };
    private int[][] pTankPos=new int[2][3];
    private int[][] sTankPos=new int[2][3];


    private ArrayList<Point> bricks;
    private ArrayList<Point> grasses;
    private ArrayList<Point> stones;
    private ArrayList<Point> waters;
    private ArrayList<Point> bases;
    public Map() throws IOException {
        bricks= new ArrayList<Point>();
        grasses=new ArrayList<Point>();
        stones=new ArrayList<Point>();
        waters=new ArrayList<Point>();
        initEltData();
    }
    public void initEltData(){
        sTankTime=0;
        blocks.clear();
        for(int i=0;i<bricks.size();i++) {
            blocks.add(new BlockBrick(bricks.get(i).x,bricks.get(i).y));
        }
        for(int i=0;i<grasses.size();i++) {
            blocks.add(new BlockGrass(grasses.get(i).x,grasses.get(i).y));
        }
        for(int i=0;i<stones.size();i++) {
            blocks.add(new BlockStone(stones.get(i).x,stones.get(i).y));
        }
        for(int i=0;i<waters.size();i++) {
            blocks.add(new BlockWater(waters.get(i).x,waters.get(i).y));
        }
        //添加边框块
        for(int i=0;i<arrFrame.length-1;i++) {
            blocks.add(new BlockFrame(arrFrame[i][0],arrFrame[i][1],
                    arrFrame[i][2],arrFrame[i][3]));
        }
    }

    public void readData(String file) throws IOException {
        FileReader fr =new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String str1,str2,line[]=null;

        bricks.clear();
        grasses.clear();
        stones.clear();
        waters.clear();

        while ((str1=br.readLine())!=null){
            line = str1.split("=");
            str1=line[0];
            str2=line[1];
            if(str1.compareTo("pTankCount")==0){
                pTankCount=Integer.parseInt(str2);
            }else if(str1.compareTo("sTankCount")==0){
                sTankCount=Integer.parseInt(str2);
            }else if(str1.compareTo("sTankTimeCount")==0){
                sTankTimeCount=Integer.parseInt(str2);
            }else if(str1.compareTo("pTankPos")==0){
                line=str2.split(",");
                pTankPos[0][0]=Integer.parseInt(line[0]);
                pTankPos[0][1]=Integer.parseInt(line[1]);
                pTankPos[0][2]=Integer.parseInt(line[2]);
                str1=br.readLine();
                line=str1.split("=");
                line=line[1].split(",");
                pTankPos[1][0]=Integer.parseInt(line[0]);
                pTankPos[1][1]=Integer.parseInt(line[1]);
                pTankPos[1][2]=Integer.parseInt(line[2]);
            }else if(str1.compareTo("sTankPos")==0){
                line=str2.split(",");
                sTankPos[0][0]=Integer.parseInt(line[0]);
                sTankPos[0][1]=Integer.parseInt(line[1]);
                sTankPos[0][2]=Integer.parseInt(line[2]);
                str1=br.readLine();
                line=str1.split("=");
                line=line[1].split(",");
                sTankPos[1][0]=Integer.parseInt(line[0]);
                sTankPos[1][1]=Integer.parseInt(line[1]);
                sTankPos[1][2]=Integer.parseInt(line[2]);
            }else if(str1.compareTo("brick")==0){
                addBlock(bricks,str2);
            }else if(str1.compareTo("stone")==0){
                addBlock(stones,str2);
            }else if(str1.compareTo("grass")==0){
                addBlock(grasses,str2);
            }else if(str1.compareTo("water")==0){
                addBlock(waters,str2);
            }else if(str1.compareTo("base")==0){
                addBlock(bases,str2);
            }
        }
        br.close();
        fr.close();
    }

    public int getsTankTime() {
        return sTankTime;
    }

    public void setsTankTime(int sTankTime) {
        this.sTankTime = sTankTime;
    }

    public int getsTankCount() {
        return sTankCount;
    }

    public void setsTankCount(int sTankCount) {
        this.sTankCount = sTankCount;
    }

    public int getpTankCount() {
        return pTankCount;
    }

    public void setpTankCount(int pTankCount) {
        this.pTankCount = pTankCount;
    }

    public int getsTankTimeCount() {
        return sTankTimeCount;
    }

    public void setsTankTimeCount(int sTankTimeCount) {
        this.sTankTimeCount = sTankTimeCount;
    }

    public void draw(Graphics g) {
        for(int i=blocks.size()-1;i>=0;i--) {
            Block block = blocks.get(i);
            block.draw(g);
        }

    }
    public SpiritTank CreateSTank() throws IOException {
        SpiritTank tank=null;
        sTankTime++;
        if((sTankTime==sTankTimeCount)){
            Random random=new Random();
            int pos=random.nextInt(sTankPos.length);
            tank=new SpiritTank(sTankPos[pos][0],sTankPos[pos][1]);
            tank.setDirection(sTankPos[pos][2]);
            sTankTime=0;
        }
        return tank;
    }
    public boolean isCollide(Tank tank){
        boolean result=false;
        for(int i=blocks.size()-1;i>=0;i--){
            Block block=blocks.get(i);
            if(block instanceof BlockBrick||block instanceof BlockStone||block instanceof BlockFrame){
                if(tank.isCollide(block)){
                    result=true;
                }
            }
        }
        return result;
    }
    public boolean isCollide(Bullet bullet){
        boolean result=false;
        for(int i=blocks.size()-1;i>=0;i--){
            Block block=blocks.get(i);
            if(bullet.isCollide(block)){
                if(block instanceof BlockBrick){
                    blocks.remove(i);
                    result=true;
                }else if(block instanceof BlockStone||block instanceof BlockFrame){
                    result=true;
                }
            }
        }
        return result;
    }
    public void initPCartoonData(Cartoon cartoon,int pos){
        cartoon.setX(pTankPos[pos][0]);
        cartoon.setY(pTankPos[pos][1]);
    }
    public void initPTankData(PlayerTank tank,int pos){
        tank.setX(pTankPos[pos][0]);
        tank.setY(pTankPos[pos][1]);
        tank.setDirection(pTankPos[pos][2]);
    }
    private void addBlock(ArrayList<Point> blks,String str){
        String[] line = str.split(",");
        int x,y;
        x=Integer.parseInt(line[0]);
        y=Integer.parseInt(line[1]);
        blks.add(new Point(x,y));
    }
    //TODO repaire the bug of the bullet
}


