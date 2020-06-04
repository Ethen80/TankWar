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

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu startMenu = new JMenu("Strat");
        startMenu.setMnemonic('S');
        menuBar.add(startMenu);
        JMenuItem newItem = new JMenuItem("New Game");
        newItem.setAccelerator(KeyStroke.getKeyStroke('N'));
        startMenu.add(newItem);
        JMenuItem levelItem = new JMenuItem("Choose a level");
        startMenu.add(levelItem);
        startMenu.addSeparator();
        JMenuItem pauseItem = new JMenuItem("Pause");
        startMenu.add(pauseItem);
        JMenuItem resumeItem = new JMenuItem("Resume");
        startMenu.add(resumeItem);
        JMenu helpMenu = new JMenu("Help");
        startMenu.setMnemonic('H');
        menuBar.add(helpMenu);
        JMenuItem manualItem = new JMenuItem("How to Play");
        helpMenu.add(manualItem);
        helpMenu.addSeparator();
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new AboutListener());
        helpMenu.add(aboutItem);
        JMenu Map = new JMenu("Map");
        Map.setMnemonic('M');
        menuBar.add(Map);
        JMenuItem mapEditorItem = new JMenuItem("Map Editor");
        JMenuItem importMap = new JMenuItem("Import Map");
        Map.add(mapEditorItem);
        mapEditorItem.addActionListener(new MapEditorListener());
        Map.add(importMap);

        setVisible(true);

        Insets insets = getInsets();
        int x, y, tempW, tempH;
        tempW = WIDTH + insets.left + insets.right;
        tempH = HEIGHT + insets.top + insets.bottom + menuBar.getHeight();

        x = (Toolkit.getDefaultToolkit().getScreenSize().width - tempW) / 2;
        y = (Toolkit.getDefaultToolkit().getScreenSize().height - tempH) / 2;
        setBounds(x, y, tempW, tempH);

        loginPanel = new LoginPanel(this);
        gamePanel = new GamePanel(this);
        winPanel = new WinPanel(this);
        overPanel = new OverPanel(this);
        login();
    }
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
