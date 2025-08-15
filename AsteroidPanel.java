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
    private int Score;

    private Timer StartTimer;
    private Timer MovementTimer;

    private BufferedImage BackgroundImage;
    private AffineTransform BackgroundScaler;

    private JLabel GameLabel;
    private JLabel ScoreLabel;
    
    private Spaceship player;
    
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> enemies;

    public AsteroidPanel(int DisplayWidth, int DisplayHeight){
        this.ScreenWidth = DisplayWidth;
        this.ScreenHeight = DisplayHeight;
        Score = 0;

        setLayout(new FlowLayout());

        GameLabel = new JLabel(""+ SecondsToStart, SwingConstants.CENTER);
        GameLabel.setForeground(Color.WHITE);
        Font LabelFont = new Font("Times New Roman", Font.BOLD, 72);
        GameLabel.setFont(LabelFont);

        ScoreLabel = new JLabel("", SwingConstants.CENTER);
        ScoreLabel.setForeground(Color.WHITE);
        Font ScoreFont = new Font("Times New Roman", Font.BOLD, 24);
        ScoreLabel.setFont(ScoreFont);

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
        MovementTimer = new Timer(SECOND_IN_MILLISECONDS, this);


        player = new Spaceship(DisplayWidth /2, DisplayHeight);
        //player.enableDrawing(true);
        
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        
        addKeyListener(this);
        setFocusable(true);
        SecondsToStart = 4;
        StartTimer.start();
        this.add(ScoreLabel);
        this.add(GameLabel);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == StartTimer){
            SecondsToStart = SecondsToStart -1;
            GameLabel.setText(""+ SecondsToStart);
            if(SecondsToStart <= 0){
                GameLabel.setText("");
                MovementTimer.start();
                StartTimer.stop();
                ScoreLabel.setText("Score: 0");
            }
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
        if(e.getKeyCode() == KeyEvent.VK_A ||e.getKeyCode() == KeyEvent.VK_LEFT){
            player.moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
            player.moveRight(ScreenWidth);
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && MovementTimer.isRunning()){
            player.shoot(bullets);
        }
        if(e.getKeyCode() == KeyEvent.VK_P){
            setPaused();
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
    public void spawnAsteroid(){
        Random ran = new Random();
        enemies.add(new Asteroid(ran.nextInt(ScreenWidth - 114), 0));
    }
    public void setPaused(){
        if(StartTimer.isRunning() || MovementTimer.isRunning()){
            if(StartTimer.isRunning())
                StartTimer.stop();
            else if(MovementTimer.isRunning())
                MovementTimer.stop();
            ScoreLabel.setText("");
            GameLabel.setText("PAUSED");
        }
        else if(!StartTimer.isRunning() || !MovementTimer.isRunning()){
            if(SecondsToStart > 0){
                GameLabel.setText("" + SecondsToStart);
                StartTimer.start();
            }
            else{
                MovementTimer.start();
                GameLabel.setText("");
                ScoreLabel.setText("Score: " + Score);
            }
        }
    }
}