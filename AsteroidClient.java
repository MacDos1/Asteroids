import javax.swing.*;
public class AsteroidClient extends JFrame{
    private int ScreenWidth = 800;
    private int ScreenHeight = 600;
    public AsteroidClient(){
        setSize(ScreenWidth, ScreenHeight);
        setResizable(false);
        setContentPane(new AsteroidPanel(ScreenWidth, ScreenHeight));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args){
        new AsteroidClient();
    }
}