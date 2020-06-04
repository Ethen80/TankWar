import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GamePanel extends JPanel implements KeyListener {

    private MainFrame mainFrame;            //主窗体
    private Image offScreenImage = null;    //第二屏
    private Graphics goffScreen = null;     //第二屏的画笔
    private ArrayList<PlayerTank> playerTanks=new ArrayList<PlayerTank>();          //玩家坦克集合
    private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();          //玩家炮弹集合
    private ArrayList<SpiritTank> spiritTanks = new ArrayList<SpiritTank>();        //精灵坦克集合
    private ArrayList<Bullet> spiritBullets = new ArrayList<Bullet>();          //精灵炮弹集合
    private ArrayList<Cartoon> cartoons=new ArrayList<Cartoon>();                //卡通集合
    private int[][] hotPs = {{50,100},{350,100},{650,100}};                 //精灵坦克生成点集合
    private static int gameMode;            //游戏模式
    private Map map;                        //地图
    private Thread thread;                  //主线程
    private int sTankCreat;                 //已创建精灵坦克数量
    private int sTankDestroyed;             //已击毁精灵坦克数量
    private int pTankNumber;                //玩家坦克生命数
    private ArrayList<String> strLevel=new ArrayList<String>();
    private int gameLevel=-1;
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
//        for(int i=gameMode;i>=0;i--){
//            playerTanks.add(new PlayerTank(0,0,i));
//        }
        playerTanks.add(new PlayerTank(400,300,1));
        playerTanks.add(new PlayerTank(400,300,0));
        for(int i=1;i>=0;i--) {
            playerTanks.get(i).setCategory(1+4*i);
        }
        thread=new Thread((new myThread()));
        thread.start();
        sTankCreat=0;
        sTankDestroyed=0;
        pTankNumber=0;

    }
    /******
     * 获得游戏模式
     * @return gameMode
     */
    public static int getGameMode() {
        return gameMode;
    }

    /*****
     * 设置游戏模式
     * @param gameMode
     */
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    /******
     * 获得关卡
     * @return gameLevel
     */
    public int getGameLevel() {
        return gameLevel;
    }
    /******
     * 设置游戏关卡
     */
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
    /******
     * 游戏计算与绘制线程
     */
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
    /******
     * 初始化数据
     */
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
    }

    /******
     * 计算数据
     * @throws IOException
     */
    private void calculateData() throws IOException {
        /*****
         * 计算卡通类
         */
        for(int i=cartoons.size()-1;i>=0;i--){
            Cartoon cartoon=cartoons.get(i);
            cartoon.calculateData();
            if(cartoon.isDead()){
                cartoons.remove(i);
                cartoon.calculateData();
            }
        }
        /******
         * 自动生成精灵坦克
         */
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
        /******
         * 控制玩家坦克的移动：判断是否触碰地图元素
         */
        for(int i=gameMode;i>=0;i--){
            if(!map.isCollide(playerTanks.get(i))){
                    playerTanks.get(i).move();
            }
        }
        /*******
         * 计算玩家坦克的数据
         */
        for(int i=gameMode;i>=0;i--){
            playerTanks.get(i).calculateData();
        }
        /******
         * 计算玩家坦克子弹的数据
         */
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
                            new AudioPlayer(AudioUtil.ADD).new AudioThread().start();
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
        /******
         * 计算精灵坦克炮弹的数据
         */
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
        /******
         * 处理精灵坦克的移动
         */
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

    /******
     * 图像绘制
     * @param g
     */
    public void paint(Graphics g){
        //创建第二屏幕
        if(offScreenImage==null){
            offScreenImage=this.createImage(MainFrame.WIDTH,MainFrame.HEIGHT);
            goffScreen=offScreenImage.getGraphics();
        }
        //双缓存技术消除屏幕闪烁
        super.paint(goffScreen);
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0,0,MainFrame.WIDTH,MainFrame.HEIGHT);
        //绘制玩家坦克
        for(int i=gameMode;i>=0;i--){
            playerTanks.get(i).draw(goffScreen);
        }
        //绘制玩家坦克的子弹
        for(int i=playerBullets.size()-1;i>=0;i--){
            Bullet bullet = playerBullets.get(i);
            bullet.draw(goffScreen);
        }
        //绘制精灵坦克的子弹
        for(int i=spiritBullets.size()-1;i>=0;i--){
            Bullet bullet = spiritBullets.get(i);
            bullet.draw(goffScreen);
        }
        //绘制精灵坦克
        for(int i=spiritTanks.size()-1;i>=0;i--){
            SpiritTank spiritTank=spiritTanks.get(i);
            spiritTank.draw(goffScreen);
        }
        //绘制精灵坦克生成点
        for(int i=0;i<hotPs.length;i++){
            ImageUtil.getInstance().drawMapBlock(goffScreen,hotPs[i][0],hotPs[i][1],ImageUtil.GRASS);

        }
        //绘制卡通
        for(int i=cartoons.size()-1;i>=0;i--){
            Cartoon cartoon=cartoons.get(i);
            cartoon.draw(goffScreen);
        }
        //绘制地图
        map.draw(goffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /******
     * 用于监听键盘按键
     * @param keyEvent
     */
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
                if (gameMode==MainFrame.GAME_MODE_MULTI) {
                    new AudioPlayer(AudioUtil.FIRE).new AudioThread().start();
                    playerBullets.add(playerTanks.get(1).fire());
                }
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
        /*****
         * 用于监听玩家坦克爆炸的结束
         * @param playerCode
         */
        @Override
        public void doFinish(int playerCode) {
            playerTanks.get(playerCode).deathTime++;
            if(playerTanks.get(playerCode).deathTime <map.getpTankCount()){
                Cartoon ct=new Cartoon(Cartoon.TCREAT,0,0,playerCode);
                map.initPCartoonData(ct,playerCode);
                ct.addFinishListener(new Listener2());
                cartoons.add(ct);
            }else {    //gameover
                new AudioPlayer(AudioUtil.GAMEOVER).new AudioThread().start();
                mainFrame.gameOver();
            }
        }

    }
    private class Listener2 extends FinishListener{
        /******
         * 用于监听玩家坦克重生动画的结束
         * @param playerCode
         */
        @Override
        public void doFinish(int playerCode) {
                map.initPTankData(playerTanks.get(playerCode), playerCode);
        }


    }
}
