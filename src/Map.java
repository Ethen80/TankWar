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
//    private int[][] arrBrick= {{5,4},{6,4},{7,4},{8,4},{9,4},
//            {5,5},{6,5},{7,5},{8,5},{9,5},
//            {5,6},{6,6},{7,6},{8,6},{9,6},
//
//            {5,10},{6,10},{7,10},{8,10},{9,10},
//            {5,11},{6,11},{7,11},{8,11},{9,11},
//            {5,12},{6,12},{7,12},{8,12},{9,12},
//
//            {15,4},{16,4},{17,4},{18,4},{19,4},
//            {15,5},{16,5},{17,5},{18,5},{19,5},
//            {15,6},{16,6},{17,6},{18,6},{19,6},
//
//            {15,10},{16,10},{17,10},{18,10},{19,10},
//            {15,11},{16,11},{17,11},{18,11},{19,11},
//            {15,12},{16,12},{17,12},{18,12},{19,12}
//    };
//    //草块数组
//    private int[][] arrGrass= {{10,7},{11,7},{12,7},{13,7},{14,7},
//            {10,8},{11,8},{12,8},{13,8},{14,8},
//            {10,9},{11,9},{12,9},{13,9},{14,9}
//    };
//    //石块数组
//    private int[][] arrStone= {{0,0},{1,0},{2,0},{3,0},{4,0},{5,0},
//            {6,0},{7,0},{8,0},{9,0},{10,0},{11,0},
//            {12,0},{13,0},{14,0},{15,0},{16,0},
//            {17,0},{18,0},{19,0},{20,0},{21,0},
//            {22,0},
//
//            {0,17},{1,17},{2,17},{3,17},{4,17},
//            {5,17},{6,17},{7,17},{8,17},{9,17},
//            {10,17},{11,17},{12,17},{13,17},{14,17},
//            {15,17},{16,17},{17,17},{18,17},{19,17},
//            {20,17},{21,17},{22,17},
//
//            {0,1},{0,2},{0,3},{0,4},{0,5},{0,6},
//            {0,7},{0,8},{0,9},{0,10},{0,11},{0,12},
//            {0,13},{0,14},{0,15},{0,16},{22,1},
//
//            {22,2},{22,3},{22,4},{22,5},{22,6},
//            {22,7},{22,8},{22,9},{22,10},{22,11},
//            {22,12},{22,13},{22,14},{22,15},{22,16},
//
//            {30,30}
//    };
//    //水块数组
//    private int[][] arrWater= {{3,14},{4,14},{5,14},{6,14},{7,14},
//            {8,14},{9,14},{10,14},{11,14},{12,14},
//            {13,14},{14,14},{15,14},{16,14},{17,14},
//            {18,14},{19,14},{20,14},{21,14},
//
//            {3,15},{4,15},{5,15},{6,15},{7,15},
//            {8,15},{9,15},{10,15},{11,15},{12,15},
//            {13,15},{14,15},{15,15},{16,15},{17,15},
//            {18,15},{19,15},{20,15},{21,15}
//    };

    private ArrayList<Point> bricks;
    private ArrayList<Point> grasses;
    private ArrayList<Point> stones;
    private ArrayList<Point> waters;
    public Map() throws IOException {
        bricks= new ArrayList<Point>();
        grasses=new ArrayList<Point>();
        stones=new ArrayList<Point>();
        waters=new ArrayList<Point>();
        initData();
        //将图块的行列数，转换为窗口坐标
//        for(int i=0;i<arrBrick.length;i++) {
//            blocks.add(new BlockBrick(arrBrick[i][0],arrBrick[i][1]));
//        }
//        for(int i=0;i<arrGrass.length;i++) {
//            blocks.add(new BlockGrass(arrGrass[i][0],arrGrass[i][1]));
//        }
//        for(int i=0;i<arrStone.length;i++) {
//            blocks.add(new BlockStone(arrStone[i][0],arrStone[i][1]));
//        }
//        for(int i=0;i<arrWater.length;i++) {
//            blocks.add(new BlockWater(arrWater[i][0],arrWater[i][1]));
//        }
//        //添加边框块
//        for(int i=0;i<arrFrame.length-1;i++) {
//            blocks.add(new BlockFrame(arrFrame[i][0],arrFrame[i][1],
//                    arrFrame[i][2],arrFrame[i][3]));
//        }
//        this.pTankCount=10;
//        this.sTankTimeCount=100;
//        this.sTankCount=10;
//        this.sTankTime=0;
    }
    public void initData(){
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


