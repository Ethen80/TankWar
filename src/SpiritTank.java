import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class SpiritTank extends Tank{
    private int stepCount;
    private int stepNumber;
    public SpiritTank(int x,int y) throws IOException {
        super(x,y);
    }

    public void calculateDate(){
        //1是否要改变方向
        stepNumber++;
        if (stepNumber >= stepCount) {
            Random random = new Random();
            stepCount=10+random.nextInt(50);
            stepNumber=0;
            setDirection(random.nextInt(4));
            }
        super.calculateData();
    }
    public Bullet fire(){
        Bullet bullte=null;
        Random random=new Random();
        if(random.nextInt(30)==0) {
            bullte=super.fire();
        }
        return bullte;
    }
}
