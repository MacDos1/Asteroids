import java.awt.*;
public abstract class Sprite {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private boolean moveable;
    private boolean drawable;
    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        this.speed = 10;
        moveable = false;
        drawable = false;
    }
    public Sprite(int x, int y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        moveable = false;
        drawable = false;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public void enableMovement(boolean moveable){
        this.moveable = moveable;
    }
    public void enableDrawing(boolean drawable){
        this.drawable = drawable;
    }
    protected void setWidth(int width){
        this.width = width;
    }
    protected void setHeight(int height){
        this.height = height;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getX(){
        return x;
    }
    public int getSpeed(){
        return speed;
    }
    public int getY(){
        return y;
    }
    public boolean canMove(){
        return moveable;
    }
    public boolean canDraw(){
        return drawable;
    }
    public abstract void draw(Graphics g);
}
