/*
COMP110 Assignment-4 Projectile Motion
Date       : 10.05.2020
Student    : Mert Arda Asar
Student ID : 041801095

 GameObject class includes type, x and y coordinates, width and height attributes.
Represents obstacles and targets. Targets are red, obstacles are blue.
*/

public class GameObject {
    int type; //Represents object is obstacle or target, 1 is obstacle, 2 is target
    double x; //X coordinate of the game object
    double y; //Y coordinate of the game object
    double w; //Width information of the game object
    double h; //Height information of the game object

    /**
     * Constructor method, assign x,y coordinates, width and height information
     * @param type represents object is obstacle or target, 1 is obstacle, 2 is target
     * @param x X coordinate of the game object
     * @param y Y coordinate of the game object
     * @param w Width information of the game object
     * @param h Height information of the game object
     */
    public GameObject(int type, double x, double y, double w, double h) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Checks if ball inside the game object or not
     * @param xBall current X coordinate of the ball
     * @param yBall current Y coordinate of the ball
     * @param w width of the game object
     * @param h height of the game object
     * @return if ball inside the game object or not
     */
    public boolean isInside(double xBall, double yBall, double w, double h){
        if (xBall>=x-(w/2)&&xBall<=x+(w/2)&&yBall<=(h/2+y)&&yBall>=y-h/2)
            return true;
        return false;
    }

    /**
     * Draws obstacles with blue color, and draws targets with red color
     */
    public void draw(){
        StdDraw.setPenRadius(0.01);
        if (type==1)
            StdDraw.setPenColor(60,85,180);
        else
            StdDraw.setPenColor(255,69,0);
        StdDraw.filledRectangle(x,y,w/2,h/2);
        StdDraw.show();
    }
}
