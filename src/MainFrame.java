import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import MapEditor2.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int GAME_MODE_SINGLE=0;
    public static final int GAME_MODE_MULTI=1;

    private LoginPanel loginPanel;
    private GamePanel gamePanel;
    private WinPanel winPanel;
    private OverPanel overPanel;

    public MainFrame(String string) throws IOException {
        super("TankWar2020 @Author AlexanderZhao @Version 0.0.0 Alpha");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu startMenu=new JMenu("Strat");
        startMenu.setMnemonic('S');
        menuBar.add(startMenu);
        JMenuItem newItem=new JMenuItem("New Game");
        newItem.setAccelerator(KeyStroke.getKeyStroke('N'));
        startMenu.add(newItem);
        JMenuItem levelItem = new JMenuItem("Choose a level");
        startMenu.add(levelItem);
        startMenu.addSeparator();
        JMenuItem pauseItem=new JMenuItem("Pause");
        startMenu.add(pauseItem);
        JMenuItem resumeItem=new JMenuItem("Resume");
        startMenu.add(resumeItem);
        JMenu helpMenu=new JMenu("Help");
        startMenu.setMnemonic('H');
        menuBar.add(helpMenu);
        JMenuItem manualItem = new JMenuItem("How to Play");
        helpMenu.add(manualItem);
        helpMenu.addSeparator();
        JMenuItem aboutItem=new JMenuItem("About");
        aboutItem.addActionListener(new AboutListener());
        helpMenu.add(aboutItem);
        JMenu Map=new JMenu("Map");
        Map.setMnemonic('M');
        menuBar.add(Map);
        JMenuItem mapEditorItem=new JMenuItem("Map Editor");
        JMenuItem importMap=new JMenuItem("Import Map");
        Map.add(mapEditorItem);mapEditorItem.addActionListener(new MapEditorListener());
        Map.add(importMap);

        setVisible(true);

        Insets insets = getInsets();
        int x, y, tempW, tempH;
        tempW = WIDTH + insets.left + insets.right;
        tempH = HEIGHT + insets.top + insets.bottom+menuBar.getHeight();

        x = (Toolkit.getDefaultToolkit().getScreenSize().width - tempW) / 2;
        y = (Toolkit.getDefaultToolkit().getScreenSize().height - tempH) / 2;
        setBounds(x, y, tempW, tempH);

        loginPanel = new LoginPanel(this);
        gamePanel = new GamePanel(this);
        winPanel=new WinPanel(this);
        overPanel=new OverPanel(this);
        login();

//        Thread tankThread;
//        tankThread = new Thread(new myThread());
//        tankThread.start();

        //    private PlayerTank playertank;
//    private Image offScreenTmage = null;
//    private Graphics goffScreen = null;
//    private ArrayList<SpiritTank> spiritTanks = new ArrayList<SpiritTank>();
//    private int[][] hotPs = {{50,100},{350,100},{650,100},{50,150}};
//    private Image img=null;
//    private ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
//    private ArrayList<Bullet> spiritBu =new ArrayList<Bullet>();
//
//
//    public MyFrame(String string) throws IOException {
//        super("坦克大战");
//        setSize(700,500);
//        File f = new File("F:\\Desktop\\EcliWorkSpace\\imageSrc\\robots_sprite.png");
//        try {
//            img = ImageIO.read(f);
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        playertank=new PlayerTank(300,450);
//        playertank.setCatagory(4);
//        this.addKeyListener(new KeyMonitor());
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
//        Thread tankThread;
//        tankThread=new Thread(new myThread());
//        tankThread.start();
//    }
//    private class KeyMonitor extends KeyAdapter{
//        @Override
//        public void keyPressed(KeyEvent e) {
//            int key = e.getKeyCode();
//            if (key==KeyEvent.VK_SPACE){
//                playerBullets.add(playertank.fire());
//            }
//            playertank.keyPressed(e);
//            }
//        }
//
//    private class myThread implements Runnable{
//        @Override
//        public void run() {
//          for(int i=0;i<100;){
//              try{
//                  Thread.sleep(100);
//                  repaint();
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
//
//          }
//        }
//    }
//
//    //TODO rearrange the paint function.
//
//    public void paint(Graphics g){
//        //deal with the born problem of spirittank
//        Random random = new Random();
//        if(random.nextInt(40)==0){
//            try {
//                int hotP;
//                hotP = random.nextInt(hotPs.length);
//                    spiritTanks.add(new SpiritTank(hotPs[hotP][0],hotPs[hotP][1],img));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //creat the second space
//        if(offScreenTmage==null){
//            offScreenTmage=this.createImage(700,500);
//            goffScreen=offScreenTmage.getGraphics();
//        }
//        //solve the problem of screen
//        super.paint(goffScreen);
//        goffScreen.setColor(Color.BLACK);
//        goffScreen.fillRect(0,0,700,500);
//        //deal with playerTanks motion(now just one playerTank)
//        playertank.move();
//        playertank.draw(goffScreen);
//        playertank.calculateData();
//        //deal with players' Bulltes
//        for(int i=playerBullets.size()-1;i>=0;i--){
//            Bullet bullet = playerBullets.get(i);
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
//        }
//        // deal with spiritTanks' Bulltes' motion
//        for(int i=spiritBu.size()-1;i>=0;i--){
//            Bullet bullet = spiritBu.get(i);
//            switch (bullet.getState()){
//                case Spirit.ALIVE:
//                    bullet.move();
//                    if(playertank.isCollide(bullet)){
//                        bullet.setState(Spirit.EXPLODE);
//                        playertank.setState(Spirit.EXPLODE);
//                    }
//                case Spirit.EXPLODE:
//                    bullet.draw(goffScreen);
//                    bullet.calculateData();
//                    break;
//                case Spirit.DIE:
//                    spiritBu.remove(i);
//            }
//        }
//        //deal with the spiritTanks' motion
//        for(int i=spiritTanks.size()-1;i>=0;i--){
//            SpiritTank spiritTank=spiritTanks.get(i);
//            switch (spiritTank.getState()) {
//                case Spirit.ALIVE:
//                    // tank move and fire
//                    spiritTank.move();
//                    //deal with the bullte of spiritetank
//                    Bullet tmpBullet=spiritTank.fire();
//                    if(tmpBullet!=null){
//                        spiritBu.add(tmpBullet);
//                    }
//                    case Spirit.EXPLODE:
//                        spiritTank.draw(goffScreen);
//                        spiritTank.calculateDate();
//                        break;
//                    case Spirit.DIE:
//                        spiritTanks.remove(i);
//            }
//        }
//        // pot where spiritTank borns
//        for(int i=0;i<hotPs.length;i++){
//            ImageUtil.getInstance().drawMapBlock(goffScreen,hotPs[i][0],hotPs[i][1],ImageUtil.GRASS);
////            goffScreen.drawImage(img,hotPs[i][0],hotPs[i][1],hotPs[i][0]+34,hotPs[i][1]+34,34*4,34*7,34*5,34*8,null);
////            goffScreen.drawRect(hotPs[i][0],hotPs[i][1],34,34);
//        }
//
////        spiritTanks.move();
////        spiritTanks.draw(goffScreen);
//        //draw the Image to the screen
//        g.drawImage(offScreenTmage,0,0,null);
//    }
    }
//    private class myThread implements Runnable {
//        @Override
//        public void run() {
//            for (int i = 0; i < 100; ) {
//                try {
//                    Thread.sleep(50);
//                    repaint();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    public void login(){
        this.setContentPane(loginPanel);
        this.addKeyListener(loginPanel);
        this.revalidate();
    }
    public void startGame(int gameMode){
        this.setContentPane(gamePanel);
        this.removeKeyListener(gamePanel);
        this.addKeyListener(gamePanel);
        new AudioPlayer(AudioUtil.START).new AudioThread().start();
        gamePanel.setGameMode(gameMode);
        gamePanel.initData();
        this.revalidate();
    }
    public void gameWin(){
        this.setContentPane(winPanel);
        this.addKeyListener(winPanel);
        this.revalidate();
    }
    public void gameOver(){
        this.setContentPane(overPanel);
        this.addKeyListener(overPanel);
        this.revalidate();
    }
    public void nextGame(){
        this.setContentPane(gamePanel);
        this.removeKeyListener(gamePanel);
        this.addKeyListener(gamePanel);
        gamePanel.setGameLevel(gamePanel.getGameLevel()+1);
        gamePanel.initData();
        this.revalidate();
    }
    private class AboutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AboutFrame();
        }
    }
    private class MapEditorListener implements ActionListener{
        String[] args=null;
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MapEditor mapEditor=new MapEditor();
            mapEditor.main(args);
        }
    }
    //TODO develop the multiple players method
    //TODO make the bullet explode and stop when it touches the edge
}
