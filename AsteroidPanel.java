import javax.swing.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.image.*;

import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

import java.util.ArrayList;
import java.util.Random;
public class AsteroidPanel extends JPanel implements ActionListener,KeyListener{
    private final int SECOND_IN_MILLISECONDS = 1000;
    private int ScreenWidth;
    private int ScreenHeight;
    private int SecondsToStart;

    private Timer StartTimer;
    private Timer MovementTimer;
    private Timer AsteroidSpawnTimer;

    private BufferedImage BackgroundImage;
    private AffineTransform BackgroundScaler;

    private JLabel GameLabel;
    
    private Spaceship player;
    
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> enemies;

    private Random ran = new Random();
    public AsteroidPanel(int DisplayWidth, int DisplayHeight){
        this.ScreenWidth = DisplayWidth;
        this.ScreenHeight = DisplayHeight;
        setLayout(new FlowLayout());
        GameLabel = new JLabel(""+ SecondsToStart, SwingConstants.CENTER);
        GameLabel.setForeground(Color.WHITE);
        Font LabelFont = new Font("Times New Roman", Font.BOLD, 72);
        GameLabel.setFont(LabelFont);

        //Setting up the Background Image

        //Finds the current directory
        String BackgroundImageLocation = System.getProperty("user.dir");

        /*
        Checks which operations system the program is running on and adds the folder where all of the sprites lives.

        Windows like to use backslash instead of slashes like every other operating system like Mac OS, and many linux distros. 
        */
        if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
            BackgroundImageLocation = BackgroundImageLocation + "/Sprites/space.png";
        }
        else if(System.getProperty("os.name").equalsIgnoreCase("Windows")){
            BackgroundImageLocation = BackgroundImageLocation + "\\Sprites\\space.png";
        }

        File BackgroundImageFile = new File(BackgroundImageLocation);
        
        try{
            BackgroundImage = ImageIO.read(BackgroundImageFile);
        }
        catch(IOException e){
            BackgroundImage = null;
        }

        BackgroundScaler = new AffineTransform();
        double ScaleWidth = (double)DisplayWidth/(double)BackgroundImage.getWidth();
        double ScaleHeight = (double)DisplayHeight/(double)BackgroundImage.getHeight();
        BackgroundScaler.scale(ScaleWidth, ScaleHeight);

        StartTimer = new Timer(SECOND_IN_MILLISECONDS, this);
        AsteroidSpawnTimer = new Timer(SECOND_IN_MILLISECONDS, this);
        MovementTimer = new Timer(SECOND_IN_MILLISECONDS, this);

        player = new Spaceship(DisplayWidth /2, DisplayHeight);
        //player.enableDrawing(true);
        
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        
        addKeyListener(this);
        setFocusable(true);
        SecondsToStart = 4;
        StartTimer.start();
        this.add(GameLabel);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == StartTimer){
            SecondsToStart = SecondsToStart -1;
            GameLabel.setText(""+ SecondsToStart);
            if(SecondsToStart <= 0){
                GameLabel.setText("");
                MovementTimer.start();
                AsteroidSpawnTimer.start();
                StartTimer.stop();
            }
        }
        if(e.getSource() ==AsteroidSpawnTimer){
            //System.out.println(AsteroidSpawnTimer.getDelay());
        }
        if(e.getSource() == MovementTimer){
            for(int i = 0; i < bullets.size(); i++){
                bullets.get(i).move();
            }
            for(int i = 0; i < enemies.size(); i++){
                enemies.get(i).move(ScreenHeight);
            }
        }
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_A){
            player.moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            player.moveRight(ScreenWidth);
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && MovementTimer.isRunning()){
            //player.shoot(bullets);
        }
        if(e.getKeyCode() == KeyEvent.VK_P){
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(BackgroundImage != null)
            g2d.drawImage(BackgroundImage, BackgroundScaler, null);
        player.draw(g);
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }
        repaint();
    }
}