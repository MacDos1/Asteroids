import javax.imageio.*;

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import java.util.ArrayList;
public class Spaceship extends Sprite{
    private BufferedImage ShipImage;
    public Spaceship(int x, int y){
        super(x, y);
        String SpaceShipImageLocation = System.getProperty("user.dir");

        /*
        Checks which operations system the program is running on and adds the folder where all of the sprites lives.

        Windows like to use backslash instead of slashes like every other operating system like Mac OS, and many linux distros. 
        */
        if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")){
            SpaceShipImageLocation = SpaceShipImageLocation + "/Sprites/spaceship.png";
        }
        else if(System.getProperty("os.name").equalsIgnoreCase("Windows")){
            SpaceShipImageLocation = SpaceShipImageLocation + "\\Sprites\\spaceship.png";
        }
        File BackgroundImageFile = new File(SpaceShipImageLocation);
        
        try{
            ShipImage = ImageIO.read(BackgroundImageFile);
        }
        catch(IOException e){
            ShipImage = null;
        }
        super.setWidth(ShipImage.getWidth() + 30);
        super.setHeight(ShipImage.getHeight() + 30);
        super.setY(getY() - super.getHeight());
    }
    public void moveRight(int ScreenWidth){
        if(getX()+ getWidth() >=ScreenWidth || getX() + getWidth() + getSpeed() >= ScreenWidth){
            setX(getX());
        }
    }
    public void moveLeft(){
        if(getX() <=0 || getX() - getSpeed() <=0){
            setX(getX());
        }
        else{
            setX(getX() - getSpeed());
        }
    }
    public void shoot(ArrayList<Bullet> bullets){
        bullets.add(new Bullet(getX(), getY(), 10, 10));
        bullets.get(bullets.size() - 1).enableDrawing(true);
        bullets.get(bullets.size() - 1).enableMovement(true);
    }
    public void draw(Graphics g){
        if(ShipImage != null){
            g.drawImage(ShipImage, getX(), getY(), null);
        }
    }
}
