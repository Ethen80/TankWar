import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;

/************************************************************************************
* @UpdateTime 05.15 8:52
 */
public class GamePanel extends JPanel implements KeyListener {
//    public static final int createX=400;
//    public static final int createY =450;
    private MainFrame mainFrame;
    private Image offScreenImage = null;
    private Graphics goffScreen = null;

    private PlayerTank playerTank;
    private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
    private ArrayList<SpiritTank> spiritTanks = new ArrayList<SpiritTank>();
    private ArrayList<Bullet> spiritBullets = new ArrayList<Bullet>();
    private ArrayList<Cartoon> cartoons=new ArrayList<Cartoon>();
    private int[][] hotPs = {{50,100},{350,100},{650,100}};

    private Map map;

    private Thread thread;
    private int sTankCreat;
    private int sTankDestroyed;
    private int pTankNumber;

    private ArrayList<String> strLevel=new ArrayList<String>();

    public int getGameLevel() {
        return gameLevel;
    }

    private int gameLevel=-1;

    public void setGameLevel(int level){
        if(level>=0&&level<strLevel.size()){
            if(gameLevel!=level){
                gameLevel=level;
                try{
                    map.readData(strLevel.get(gameLevel));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                map.initData();
            }
        }
    }

    public GamePanel(MainFrame mainFrame) throws IOException {
        super();
        this.mainFrame=mainFrame;
        FileReader fr=new FileReader("F:\\Desktop\\EcliWorkSpace\\maps\\config.tank");
        BufferedReader br=new BufferedReader(fr);
        String str;
        while ((str=br.readLine())!=null){
            strLevel.add(str);
        }
        br.close();
        fr.close();
        map=new Map();
        setGameLevel(0);
        playerTank=new PlayerTank(300,450);
        playerTank.setCatagory(4);
        map.initPTankData(playerTank,0);
        thread=new Thread((new myThread()));
        thread.start();
        sTankCreat=0;
        sTankDestroyed=0;
        pTankNumber=0;
    }
    private class myThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; ) {
                try {
                    Thread.sleep(100);
                    calculateData();
                    repaint();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void initData(){
        sTankCreat=0;
        sTankDestroyed=0;
        map.initData();
        map.initPTankData(playerTank,0);
        playerBullets.clear();
        spiritBullets.clear();
        spiritTanks.clear();
        //thread.start();
    }
    private void calculateData() throws IOException {
        for(int i=cartoons.size()-1;i>=0;i--){
            Cartoon cartoon=cartoons.get(i);
            cartoon.calculateData();
            if(cartoon.isDead()){
                cartoons.remove(i);
//                cartoon.calculateData();
            }
        }
//        if(!map.isCollide(playerTank)){
//            playerTank.move();
//        }
//        playerTank.calculateData();
//        SpiritTank spiritTank=map.CreateSTank();
//        if(spiritTank!=null){
//            spiritTanks.add(spiritTank);
//        }
//        for(int i=spiritTanks.size()-1;i>=0;i--){
//            spiritTank=spiritTanks.get(i);
//            if(!map.isCollide(spiritTank)){
//                spiritTank.move();
//            }
//            Bullet tmpBullet = spiritTank.fire();
//            if(tmpBullet!=null){
//                spiritBullets.add(tmpBullet);
//            }
//            spiritTank.calculateData();
//        }
        //GENERATE NEW SPIRIT TANK
        if(sTankCreat<map.getpTankCount()) {
            try {
                SpiritTank spiritTank = map.CreateSTank();
                if (spiritTank != null) {
                    spiritTanks.add(spiritTank);
                    sTankCreat++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!map.isCollide(playerTank)){
            playerTank.move();
        }
        playerTank.calculateData();
        //deal with players' Bulltes
        for(int i=playerBullets.size()-1;i>=0;i--){
            Bullet bullet = playerBullets.get(i);
            if(map.isCollide(bullet)){
                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
                playerBullets.remove(i);
                bullet=null;
                continue;
            }
            bullet.move();
            bullet.calculateData();
            if(bullet!=null) {
                for (int j = spiritTanks.size() - 1; j >= 0; j--) {
                    SpiritTank spiritTank = spiritTanks.get(j);
                    //if the bullet attack the tank
                    if(bullet!=null) {
                        if (spiritTank.isCollide(bullet)) {
                            cartoons.add(new Cartoon(Cartoon.BEXPLODE, bullet.getX(), bullet.getY()));
                            cartoons.add(new Cartoon(Cartoon.TEXPLODE, spiritTank.getX(), spiritTank.getY()));
                            playerBullets.remove(i);
                            spiritTanks.remove(j);
                            sTankDestroyed++;
                            if(sTankDestroyed==map.getsTankCount()){
                                mainFrame.gameWin();
                            }
                            bullet = null;
                            continue;
                        }
                    }
                }
            }
            if(bullet!=null){
                for(int k=spiritBullets.size()-1;k>=0;k--){
                    Bullet bullet1=spiritBullets.get(k);
                    if(bullet.isCollide(bullet1)){
                        cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet1.getX(),bullet1.getY()));
                        playerBullets.remove(i);
                        spiritBullets.remove(k);
                        bullet=null;
                        continue;
                    }
                }
            }
        }
        // deal with spiritTanks' Bulltes' motion
        for(int i=spiritBullets.size()-1;i>=0;i--){
            Bullet bullet = spiritBullets.get(i);
            if(map.isCollide(bullet)){
                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
                spiritBullets.remove(i);
                continue;
            }
            bullet.move();
            bullet.calculateData();
            if(playerTank.isCollide(bullet)){
                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
                Cartoon cartoon=new Cartoon(Cartoon.TEXPLODE,playerTank.getX(),playerTank.getY());
                cartoon.addFinishListener(new Listener1());
                cartoons.add(cartoon);
                spiritBullets.remove(i);
                playerTank.setX(-1000);
            }
        }
        //deal with the spiritTanks' motion
        for(int i=spiritTanks.size()-1;i>=0;i--){
            SpiritTank spiritTank=spiritTanks.get(i);
            if(!map.isCollide(spiritTank)){
                spiritTank.move();
            }
            Bullet tmpBullet=spiritTank.fire();
            if(tmpBullet!=null){
                spiritBullets.add(tmpBullet);
            }
            spiritTank.calculateDate();
        }
    }
    public void paint(Graphics g){
        //deal with the born problem of spirittank
//        try {
//            SpiritTank spiritTank=map.CreateSTank();
//            if(spiritTank!=null){
//                spiritTanks.add(spiritTank);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //creat the second space
        if(offScreenImage==null){
            offScreenImage=this.createImage(MainFrame.WIDTH,MainFrame.HEIGHT);
            goffScreen=offScreenImage.getGraphics();
        }
        //solve the problem of screen
        super.paint(goffScreen);
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0,0,MainFrame.WIDTH,MainFrame.HEIGHT);
        //deal with playerTanks motion(now just one playerTank)
//        if(!map.isCollide(playerTank)){
//            playerTank.move();
//        }
        playerTank.draw(goffScreen);
//        playerTank.calculateData();
        //deal with players' Bulltes
        for(int i=playerBullets.size()-1;i>=0;i--){
            Bullet bullet = playerBullets.get(i);
//            if(map.isCollide(bullet)){
//                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
//                playerBullets.remove(i);
//                bullet=null;
//                continue;
//            }
//            bullet.move();
            bullet.draw(goffScreen);
//            bullet.calculateData();
//            if(bullet!=null) {
//                for (int j = spiritTanks.size() - 1; j >= 0; j--) {
//                    SpiritTank spiritTank = spiritTanks.get(j);
//                    //if the bullet attack the tank
//                    if(bullet!=null) {
//                        if (spiritTank.isCollide(bullet)) {
//                            cartoons.add(new Cartoon(Cartoon.BEXPLODE, bullet.getX(), bullet.getY()));
//                            cartoons.add(new Cartoon(Cartoon.TEXPLODE, spiritTank.getX(), spiritTank.getY()));
//                            playerBullets.remove(i);
//                            spiritTanks.remove(j);
//                            bullet = null;
//                            continue;
//                        }
//                    }
//                }
//            }
//            if(bullet!=null){
//                for(int k=spiritBullets.size()-1;k>=0;k--){
//                    Bullet bullet1=spiritBullets.get(k);
//                    if(bullet.isCollide(bullet1)){
//                        cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet1.getX(),bullet1.getY()));
//                        playerBullets.remove(i);
//                        spiritBullets.remove(k);
//                        bullet=null;
//                        continue;
//                    }
//                }
//            }
//            switch (bullet.getState()){
//                case Spirit.ALIVE:
//                    bullet.move();
//                    for(int j=spiritTanks.size()-1;j>=0;j--){
//                        SpiritTank spiritTank=spiritTanks.get(j);
//                        if(spiritTank.isCollide(bullet)){
//                            bullet.setState(1);
//                            spiritTank.setState(1);
//                        }
//                    }
//                case Spirit.EXPLODE:
//                    bullet.draw(goffScreen);
//                    bullet.calculateData();
//                    break;
//                case Spirit.DIE:
//                    playerBullets.remove(i);
//                    break;
//            }
        }
        // deal with spiritTanks' Bulltes' motion
        for(int i=spiritBullets.size()-1;i>=0;i--){
            Bullet bullet = spiritBullets.get(i);
//            if(map.isCollide(bullet)){
//                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
//                spiritBullets.remove(i);
//                continue;
//            }
//            bullet.move();
            bullet.draw(goffScreen);
//            bullet.calculateData();
//            if(playerTank.isCollide(bullet)){
//                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
//                Cartoon cartoon=new Cartoon(Cartoon.TEXPLODE,playerTank.getX(),playerTank.getY());
//                cartoon.addFinishListener(new Listener1());
//                cartoons.add(cartoon);
//                spiritBullets.remove(i);
//                playerTank.setX(-1000);
//            }
            //            switch (bullet.getState()){
//                case Spirit.ALIVE:
//                    bullet.move();
//                    if(playerTank.isCollide(bullet)){
//                        bullet.setState(Spirit.EXPLODE);
//                        playerTank.setState(Spirit.EXPLODE);
//                    }
//                case Spirit.EXPLODE:
//                    bullet.draw(goffScreen);
//                    bullet.calculateData();
//                    break;
//                case Spirit.DIE:
//                    spiritBullets.remove(i);
//            }
        }
        //deal with the spiritTanks' motion
        for(int i=spiritTanks.size()-1;i>=0;i--){
            SpiritTank spiritTank=spiritTanks.get(i);
//            if(!map.isCollide(spiritTank)){
//                spiritTank.move();
//            }
//            Bullet tmpBullet=spiritTank.fire();
//            if(tmpBullet!=null){
//                spiritBullets.add(tmpBullet);
//            }
            spiritTank.draw(goffScreen);
//            spiritTank.calculateDate();
            //            switch (spiritTank.getState()) {
//                case Spirit.ALIVE:
//                    // tank move and fire
//                    spiritTank.move();
//                    //deal with the bullte of spiritetank
//                    Bullet tmpBullet=spiritTank.fire();
//                    if(tmpBullet!=null){
//                        spiritBullets.add(tmpBullet);
//                    }
//                case Spirit.EXPLODE:
//                    spiritTank.draw(goffScreen);
//                    spiritTank.calculateDate();
//                    break;
//                case Spirit.DIE:
//                    spiritTanks.remove(i);
//            }
        }
        // pot where spiritTank borns
        for(int i=0;i<hotPs.length;i++){
            ImageUtil.getInstance().drawMapBlock(goffScreen,hotPs[i][0],hotPs[i][1],ImageUtil.GRASS);
//            goffScreen.drawImage(img,hotPs[i][0],hotPs[i][1],hotPs[i][0]+34,hotPs[i][1]+34,34*4,34*7,34*5,34*8,null);
//            goffScreen.drawRect(hotPs[i][0],hotPs[i][1],34,34);
        }
        //deal with the animation
//        for(int i=cartoons.size()-1;i>=0;i--){
//            Cartoon cartoon=cartoons.get(i);
//            if(!cartoon.isDead()){
//                cartoons.remove(i);
//            }
//        }
        map.draw(goffScreen);
//        spiritTanks.move();
//        spiritTanks.draw(goffScreen);
        //draw the Image to the screen
        for(int i=cartoons.size()-1;i>=0;i--){
            Cartoon cartoon=cartoons.get(i);
            cartoon.draw(goffScreen);
        }
        g.drawImage(offScreenImage,0,0,null);
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key){
            case KeyEvent.VK_SPACE:
                playerBullets.add(playerTank.fire());
                break;
            case KeyEvent.VK_ESCAPE:
                mainFrame.removeKeyListener(this);
                mainFrame.login();
        }
        playerTank.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    private class Listener1 extends FinishListener{

        @Override
        public void doFinish() {
            pTankNumber++;
            if(pTankNumber<map.getpTankCount()){
                Cartoon ct=new Cartoon(Cartoon.TCREAT,0,0);
                map.initPCartoonData(ct,0);
                ct.addFinishListener(new Listener2());
                cartoons.add(ct);
            }else {    //gameover
                mainFrame.gameOver();

            }
        }
    }
    private class Listener2 extends FinishListener{

        @Override
        public void doFinish() {
            map.initPTankData(playerTank,0);
        }
    }
    //TODO slove the problem of the Tank's move when it touch the edge
    //TODO solve the problem of the exception
}
