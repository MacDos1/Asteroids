import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
public class Asteroid extends Sprite{
    private BufferedImage AsteroidImage;
    public Asteroid(int x, int y){
        super(x, y);
    }
    public void move(){
        if(canMove()){
            setY(getY() + getSpeed());
        }
    }
    public void draw(Graphics g){}
}
