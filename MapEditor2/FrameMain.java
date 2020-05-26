package MapEditor2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
public class FrameMain extends JFrame{

    private PanelMain panelMain;
    public FrameMain() {
        // TODO Auto-generated constructor stub
        this.setTitle("");

        //添加菜单
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();//新建菜单条
        this.setJMenuBar(menuBar);//将菜单条添加到窗体上
        addFileMenu(menuBar);//添加File菜单
        addEditMenu(menuBar);//添加Edit菜单
        addHelpMenu(menuBar);//添加Help菜单



        //新建图标面板，并添加到主窗体上
        PanelIcon panelIcon = new PanelIcon(this);
        panelIcon.initPanel();
        add(panelIcon,BorderLayout.WEST);


        //新建地图面板
        panelMain = new PanelMain(this);
        panelMain.setBorder(new LineBorder(Color.BLUE));
        panelMain.setPreferredSize(new Dimension(800,600));
        //新建一个滚动面板，地图面板区域放到滚动面板中，垂直滚动条总是显示，水平滚动条只有在需要时显示
        JScrollPane panel = new JScrollPane(panelMain,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(panel);//	将滚动区域添加到窗体
        addToolBar();//添加工具栏

        this.setSize(800,600);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dm = kit.getScreenSize();

        this.setLocation((dm.width-800)/2, (dm.height-600)/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);//设置窗体可见
    }
    private void addToolBar() {
        JToolBar toolBar = new JToolBar("工具栏");
        toolBar.setBorder(new EtchedBorder());
        // 创建 工具栏按钮
        JButton undoBtn = new JButton(new ImageIcon("F:\\Desktop\\EcliWorkSpace\\imageSrc\\undo.png"));
        undoBtn.setToolTipText("undo");
        JButton redoBtn = new JButton(new ImageIcon("F:\\Desktop\\EcliWorkSpace\\imageSrc\\redo.png"));
        redoBtn.setToolTipText("redo");

        // 添加 按钮 到 工具栏
        toolBar.add(undoBtn);
        toolBar.add(redoBtn);
        // 添加 工具栏 到 内容面板 的 顶部
        this.add(toolBar, BorderLayout.PAGE_START);
    }
    private void addEditMenu(JMenuBar menuBar) {
        JMenu editMenu = new JMenu("Edit");//新建菜单"File"
        editMenu.setMnemonic('E');//设置"File"菜单助记符
        menuBar.add(editMenu);//将菜单"File"添加到菜单条中

        JMenuItem undoItem = new JMenuItem("undo");//新建"undo"菜单项
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_MASK));
        editMenu.add(undoItem);//将"undo"菜单项添加到"Edit"菜单中

        JMenuItem redoItem = new JMenuItem("redo");//新建"redo"菜单项
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        editMenu.add(redoItem);//将"redo"菜单项添加到"Edit"菜单中
    }
    private void addFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");//新建菜单"File"
        fileMenu.setMnemonic('F');//设置"File"菜单助记符
        menuBar.add(fileMenu);//将菜单"File"添加到菜单条中

        JMenuItem newItem = new JMenuItem("new");//新建"new"菜单项
        newItem.setAccelerator(KeyStroke.getKeyStroke('N'));//为菜单项添加"N"快捷键
        fileMenu.add(newItem);//将"new"菜单项添加到"File"菜单中

        JMenuItem openItem = new JMenuItem("open");//新建"open"菜单项
        //为菜单项添加Ctrl+O快捷键
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        fileMenu.add(openItem);//将"open"菜单项添加到"File"菜单中

        JMenuItem saveItem = new JMenuItem("save");//新建"open"菜单项
        //为菜单项添加Ctrl+S快捷键
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
        saveItem.addActionListener(new SaveItemAct());
        fileMenu.add(saveItem);//将"open"菜单项添加到"File"菜单中
    }
    private void addHelpMenu(JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");//新建菜单"Help"
        helpMenu.setMnemonic('H');//设置"Help"菜单助记符
        menuBar.add(helpMenu);//将菜单"Help"添加到菜单条中

        JMenuItem manualItem = new JMenuItem("Manual");//新建"Manual"菜单项
        manualItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK));
        helpMenu.add(manualItem);//将"Manual"菜单项添加到"Help"菜单中

        JMenuItem aboutItem = new JMenuItem("About");//新建"About"菜单项
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        helpMenu.add(aboutItem);//将"About"菜单项添加到"Help"菜单中
    }
    class SaveItemAct implements ActionListener {

        JFileChooser fileDialog = new JFileChooser();
        public void actionPerformed(ActionEvent arg0) {

            int state = fileDialog.showSaveDialog(null);
            if (state == JFileChooser.APPROVE_OPTION) {
                // 取得文件路径显示在tfDir文本框中
                try {
                    panelMain.saveMap(fileDialog.getSelectedFile().getAbsoluteFile());
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(fileDialog.getSelectedFile().getAbsoluteFile());
            }
        }
    }

}
