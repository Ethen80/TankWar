import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/************************************************************************************
* @UpdateTime 05.15 8:52
 */
public class GamePanel extends JPanel implements KeyListener {
//    public static final int createX=400;
//    public static final int createY =450;
    private MainFrame mainFrame;
    private Image offScreenImage = null;
    private Graphics goffScreen = null;

//    private PlayerTank playerTank;
    private ArrayList<PlayerTank> playerTanks=new ArrayList<PlayerTank>();
    private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
    private ArrayList<SpiritTank> spiritTanks = new ArrayList<SpiritTank>();
    private ArrayList<Bullet> spiritBullets = new ArrayList<Bullet>();
    private ArrayList<Cartoon> cartoons=new ArrayList<Cartoon>();
    private int[][] hotPs = {{50,100},{350,100},{650,100}};

    private static int gameMode;
    public static int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }



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
                map.initEltData();
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
//        playerTank=new PlayerTank(300,450,0);
        for(int i=gameMode;i>=0;i--){
            playerTanks.add(new PlayerTank(0,0,i));
        }
        for(int i=gameMode;i>=0;i--) {
            playerTanks.get(i).setCatagory(4);
        }
        playerTanks.add(new PlayerTank(400,300,1));
        playerTanks.add(new PlayerTank(400,300,0));
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
        map.initEltData();
        for(int i=gameMode;i>=0;i--) {
            map.initPTankData(playerTanks.get(i), i);
        }
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
                cartoon.calculateData();
            }
        }

        if(sTankCreat<map.getsTankCount()) {
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
        for(int i=gameMode;i>=0;i--){
            if(!map.isCollide(playerTanks.get(i))){
                    playerTanks.get(i).move();
            }
        }
        for(int i=gameMode;i>=0;i--){
            playerTanks.get(i).calculateData();
        }

        //deal with players' Bulltes
        for(int i=playerBullets.size()-1;i>=0;i--){
            Bullet bullet = playerBullets.get(i);
            if(map.isCollide(bullet)){
                cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
                new AudioPlayer(AudioUtil.HIT).new AudioThread().start();
                playerBullets.remove(i);
//                bullet=null;
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
                            new AudioPlayer(AudioUtil.BLAST).new AudioThread().start();
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
                        new AudioPlayer(AudioUtil.HIT).new AudioThread().start();
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
                new AudioPlayer(AudioUtil.HIT).new AudioThread().start();
                spiritBullets.remove(i);
                continue;
            }
            bullet.move();
            bullet.calculateData();
            for(int j=gameMode;j>=0;j--){
                if(playerTanks.get(j).isCollide(bullet)){
                    cartoons.add(new Cartoon(Cartoon.BEXPLODE,bullet.getX(),bullet.getY()));
                    Cartoon cartoon=new Cartoon(Cartoon.TEXPLODE,playerTanks.get(j).getX(),playerTanks.get(j).getY(),j);
                    cartoon.addFinishListener(new Listener1());
                    cartoons.add(cartoon);
                    spiritBullets.remove(i);
                    playerTanks.get(j).setX(-1000);
                }
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

        if(offScreenImage==null){
            offScreenImage=this.createImage(MainFrame.WIDTH,MainFrame.HEIGHT);
            goffScreen=offScreenImage.getGraphics();
        }
        //solve the problem of screen
        super.paint(goffScreen);
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0,0,MainFrame.WIDTH,MainFrame.HEIGHT);

        for(int i=gameMode;i>=0;i--){
            playerTanks.get(i).draw(goffScreen);
        }

        for(int i=playerBullets.size()-1;i>=0;i--){
            Bullet bullet = playerBullets.get(i);

            bullet.draw(goffScreen);

        }
        // deal with spiritTanks' Bulltes' motion
        for(int i=spiritBullets.size()-1;i>=0;i--){
            Bullet bullet = spiritBullets.get(i);

            bullet.draw(goffScreen);

        }
        //deal with the spiritTanks' motion
        for(int i=spiritTanks.size()-1;i>=0;i--){
            SpiritTank spiritTank=spiritTanks.get(i);

            spiritTank.draw(goffScreen);

        }
        // pot where spiritTank borns
        for(int i=0;i<hotPs.length;i++){
            ImageUtil.getInstance().drawMapBlock(goffScreen,hotPs[i][0],hotPs[i][1],ImageUtil.GRASS);

        }
        //deal with the animation
//        for(int i=cartoons.size()-1;i>=0;i--){
//            Cartoon cartoon=cartoons.get(i);
//            if(!cartoon.isDead()){
//                cartoons.remove(i);
//            }
//        }
        map.draw(goffScreen);

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
                new AudioPlayer(AudioUtil.FIRE).new AudioThread().start();
                playerBullets.add(playerTanks.get(0).fire());
//                    System.out.println(playerBullets.size());
                break;
            case KeyEvent.VK_J:
                if (gameMode==MainFrame.GAME_MODE_MULTI)
                    new AudioPlayer(AudioUtil.FIRE).new AudioThread().start();
                playerBullets.add(playerTanks.get(1).fire());
                break;
            case KeyEvent.VK_ESCAPE:
                mainFrame.removeKeyListener(this);
                mainFrame.login();
        }
        for(int i=gameMode;i>=0;i--) {
            playerTanks.get(i).keyPressed(keyEvent);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    private class Listener1 extends FinishListener{

        @Override
        public void doFinish(int playerCode) {
            playerTanks.get(playerCode).deathTime++;
            if(playerTanks.get(playerCode).deathTime <map.getpTankCount()){
                Cartoon ct=new Cartoon(Cartoon.TCREAT,0,0);
                map.initPCartoonData(ct,playerCode);
                ct.addFinishListener(new Listener2());
                cartoons.add(ct);
            }else {    //gameover
                mainFrame.gameOver();

            }
        }

    }
    private class Listener2 extends FinishListener{

        @Override
        public void doFinish(int playerCode) {
                map.initPTankData(playerTanks.get(playerCode), playerCode);
        }


    }
    //TODO slove the problem of the Tank's move when it touch the edge
    //TODO solve the problem of the exception
    //TODO fix the bug of the death of the Tank2
}
