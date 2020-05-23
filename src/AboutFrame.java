import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutFrame extends JFrame implements ActionListener {

    public AboutFrame(){
        setTitle("About This Program");
        setLayout(null);
        setSize(380,150);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension size = kit.getScreenSize();
        this.setLocation((size.width-380)/2,(size.height-150)/2);
        this.setResizable(false);

        JLabel lable1=new JLabel("GAME TankWar2020\n @student Alexander Zhao\n@Date 2020.05.20");
        lable1.setLocation(20,20);
        lable1.setSize(320,20);
        add(lable1);

        JButton button = new JButton("确定");
        button.setBounds(280,80,80,30);
        button.addActionListener(this);
        add(button);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.dispose();
    }
}
