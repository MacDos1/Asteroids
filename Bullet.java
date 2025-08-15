import java.awt.*;
public class Bullet extends Sprite{
    public Bullet(int x, int y, int width, int height){
        super(x, y);
        super.setWidth(width);
        super.setHeight(height);
        super.enableDrawing(true);
        setWidth(width);
        setHeight(height);
    }
    public void move(){
        if(canMove()){
            if(getY() <= 0 || getY() - getSpeed() <=0){
                enableMovement(false);
            }
            setY(getY() - getSpeed());
        }
    }
    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        if(canDraw())
            g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
