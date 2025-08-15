import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
public class Asteroid extends Sprite{
    private BufferedImage AsteroidImage;
    public Asteroid(int x, int y){
        super(x, y);
        String AsteroidImageLocation = System.getProperty("user.dir");

        /*
        Checks which operations system the program is running on and adds the folder where all of the sprites lives.

        Windows like to use backslash instead of slashes like every other operating system like Mac OS, and many linux distros. 
        */
        if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
            AsteroidImageLocation = AsteroidImageLocation + "/Sprites/astroid.png";
        }
        else if(System.getProperty("os.name").equalsIgnoreCase("Windows")){
            AsteroidImageLocation = AsteroidImageLocation + "\\Sprites\\astroid.png";
        }
        File BackgroundImageFile = new File(AsteroidImageLocation);
        
        try{
            AsteroidImage = ImageIO.read(BackgroundImageFile);
        }
        catch(IOException e){
            AsteroidImage = null;
        }
        setWidth(AsteroidImage.getWidth());
        setHeight(AsteroidImage.getHeight() +30);
        enableDrawing(true);
        enableMovement(true);
    }
    public void move(int ScreenHeight){
        //System.out.println(getHeight());
        if(canMove()){
            if(getY() + getHeight() >= ScreenHeight || getY() + getHeight() + getSpeed() >=ScreenHeight){
                enableMovement(false);
            }
            setY(getY() + getSpeed());
        }
    }
    public void draw(Graphics g){
        if(AsteroidImage != null || canDraw()){
            g.drawImage(AsteroidImage, getX(), getY(), null);
        }
    }
}
