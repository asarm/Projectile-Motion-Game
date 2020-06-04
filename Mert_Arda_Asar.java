/*
COMP110 Assignment-4 Projectile Motion
Date       : 10.05.2020
Student    : Mert Arda Asar
Student ID : 041801095

 The main purpose of the program is modelling the projectile motion with a ball.
It allows the user to set the velocity and angle of movement of the ball.
Checks if the ball touch the any game object during projectile motion and displays correct message on the screen.
*/

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Calls the function that launches the game. Draws the game frame.
 */
public class Mert_Arda_Asar {

    public static void main(String[] args) throws FileNotFoundException {
        File environment_file = new File("game_environment.txt"); //File to read

        ArrayList<GameObject> objectList = new ArrayList<>(); //Keeps game object information
        readEnvironment(environment_file,objectList); //Reads game objects information in the environment_file

        StdDraw.setCanvasSize(1250, 500); //Sets size of the canvas
        StdDraw.setXscale(0,1350);
        StdDraw.setYscale(0,400);
        StdDraw.enableDoubleBuffering();
        play(objectList); //Method that calls main functions to play game
    }

    /**
     * Reads environment_file to get game objects information and add them to the list
     * @param environment_file file to read, objects information will be taken here
     * @param objectList information of game objects will be added to this list
     */
    public static void readEnvironment(File environment_file, ArrayList<GameObject> objectList) throws FileNotFoundException
    {
        Scanner sc = new Scanner(environment_file); // may throw error
        while (sc.hasNextLine()) {
            String line = sc.nextLine(); //read line by line
            String[] fields = line.split(";");

            //Adding object information
            GameObject object = new GameObject(Integer.parseInt(fields[0]), Double.parseDouble(fields[1]), Double.parseDouble(fields[2]),Double.parseDouble(fields[3]),Integer.parseInt(fields[4]));
            objectList.add(object);
        }
        sc.close();
    }

    /**
     * Calls main fuctions to play game
     * @param objectList includes obstacles and targes information
     */
    public static void play(ArrayList<GameObject> objectList) {
        final double GRAVITY = 9.81;

        double startingX = 50; //initial X coordinate of position the ball
        double startingY = 50; //initial Y coordinate of position the ball
        double ballX=50;  //current X coordinate of position the ball
        double ballY=50; //current Y coordinate of position the ball
        double angle=35;  //initial angle of shoot
        double velocity=105; //initial scalar velocitiy magnitude

        boolean check = true; //checks the game's activity status
        double time = 0; //stores the game's own duration
        double horizontalVelocity; //express the horizontal velocity of the ball
        double verticalVelocity; //express the vertivcal velocity of the ball

        //Sets general font
        Font font = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(font);

        StdDraw.setPenColor(Color.BLACK);
        draw(objectList); //draws game environment (obstacles, targets and starting platform)
        writeInfo(velocity,angle); //writes velocity, angle information in additon to play again and quite information
        drawVector(angle,velocity,ballX,ballY); ////Draws the bar representing velocity and direction alsa draws filled Ball

        boolean win; //controls the winning situation
        int duration = 200;
        while (true){
            if (check){
                //Calculates horizontal and vertical velocity using angle and velocity information
                horizontalVelocity = velocity*Math.cos(Math.toRadians(angle));
                verticalVelocity = velocity*Math.sin(Math.toRadians(angle));

                //Sets the functions of the different keys before shot the ball
                //Right Arrow Key increases movement velocity
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                    StdDraw.pause(duration); //Waits for getting key input
                    velocity++; //Increases velocity +1
                    StdDraw.clear(); //Clears all objects on the screen
                    draw(objectList); //Draws game environment includes obstacles, targets and starting platform
                    writeInfo( velocity,angle);  //writes velocity, angle information in additon to play again and quite information
                    drawVector(angle,velocity,ballX,ballY); //Draws the bar representing velocity and direction alsa draws filled Ball
                    StdDraw.pause(50);
                }

                //Left Arrow Key decreases movement velocity
                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                    StdDraw.pause(duration); //Waits for getting key input
                    velocity--; //Decreases velocity -1
                    StdDraw.clear(); //Clears all objects on the screen
                    draw(objectList); //Draws game environment includes obstacles, targets and starting platform
                    drawVector(angle,velocity,ballX,ballY); //Draws the bar representing velocity and direction alsa draws filled Ball
                    writeInfo(velocity,angle);  //writes velocity, angle information in additon to play again and quite information
                    StdDraw.pause(50);
                }

                //Up Arrow Key increases movement angle
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                    StdDraw.pause(duration);
                    angle++; //Increases angle +1
                    StdDraw.clear(); //Clears all objects on the screen
                    draw(objectList); //Draws game environment includes obstacles, targets and starting platform
                    drawVector(angle,velocity,ballX,ballY); //Draws the bar representing velocity and direction alsa draws filled Ball
                    writeInfo(velocity,angle);  //writes velocity, angle information in additon to play again and quite information
                    StdDraw.pause(50);
                }

                //Down Arrow Key decreases movement anges
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                    StdDraw.pause(duration); //Waits for getting key input
                    angle--;  //Decreases angle -1
                    StdDraw.clear(); //Clears all objects on the screen
                    draw(objectList); //Draws game environment includes obstacles, targets and starting platform
                    drawVector(angle,velocity,ballX,ballY); //Draws the bar representing velocity and direction alsa draws filled Ball
                    writeInfo(velocity,angle); //writes velocity, angle information in additon to play again and quite information
                    StdDraw.pause(50);
                }

                //Spacebar starts the movement of the ball
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                    while (check){
                        StdDraw.filledCircle(ballX, ballY, 2);
                        StdDraw.show();

                        //Controls if the ball is inside the any game object
                        for (int i=0;i<objectList.size();i++){
                            if (objectList.get(i).isInside(ballX,ballY,objectList.get(i).w,objectList.get(i).h)){
                                //Checks if the ball is inside the obstacle
                                if (objectList.get(i).type==1){
                                    System.out.println("You Lost");
                                    StdDraw.text(ballX-5,ballY+10,"x="+ballX); //Displays X coordiantes when hit the object
                                    win = false;
                                    writeInfo( velocity,angle,win); //Displays winning situation and movement information
                                }
                                //Checks if the ball is inside the target
                                else {
                                    StdDraw.text(ballX-5,ballY+10,"x="+ballX); //Displays X coordiantes when hit the object
                                    System.out.println("You win!!");
                                    win = true;
                                    writeInfo( velocity,angle,win); //Displays winning situation and movement information
                                }
                                check=false; //Locks the keys
                                break;
                            }
                            //Checks if the ball inside the canvas
                            else if (ballY <= 0 || ballX>1250){
                                System.out.println("You Lost");
                                check=false; //Locks the keys
                                win = false;
                                writeInfo( velocity,angle,win); //Displays winning situation and movement information
                                break;
                            }

                            ballX = startingX+horizontalVelocity*(time); //Calculates current X coordinate of the ball
                            ballY = startingY+(verticalVelocity*(time)-((double)1/2*GRAVITY*Math.pow((time),2))); //Calculates current Y coordinate of the ball
                        }
                        time= time+0.04; //Calculates game time
                    }
                }
                StdDraw.show();

                //Key 'P' starts the game again
                if (StdDraw.isKeyPressed(KeyEvent.VK_P)){
                    check  = true;
                    StdDraw.clear();
                    play(objectList); //Restarts the game
                }

                //Key 'Q' ends the game
                else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)){
                    quit(); //Ends the game
                    break;
                }
            }

            //Checks user decision after hit the bll obstacle or target
            //Key 'P' starts the game again
            else if (StdDraw.isKeyPressed(KeyEvent.VK_P)){
                check  = true;
                StdDraw.clear();
                play(objectList); //Restarts the game
            }

            //Key 'Q' ends the game
            else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)){
                quit(); //Ends the game
                break;
            }
            StdDraw.setPenColor(Color.BLACK);

        }

    }
    public static void draw(ArrayList<GameObject> objectList){
        //Draws obstacles and targets
        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).draw();
        }
        StdDraw.setPenColor(60, 179, 113); //Starting platform color
        StdDraw.filledRectangle(0, 0, 55, 45); //Draws starting platform
        StdDraw.show();
    }

    /**
     * Draws a vector, which represnts movement direction and magnitude, also draws ball
     * @param angle initial angle of shoot
     * @param velocity initial scalar velocitiy magnitude
     * @param ballX X coordinate of the current position of the ball
     * @param ballY Y coordinate of the current position of the ball
     */
    public static void drawVector(double angle, double velocity,double ballX,double ballY){
        StdDraw.setPenColor(60, 179, 113);

        //Draws vector to show direction and magnitute of the movement
        StdDraw.line(50,50,50+(velocity*Math.cos(Math.toRadians(angle))),50+(velocity*Math.sin(Math.toRadians(angle))));
        StdDraw.show();
        StdDraw.setPenColor(Color.BLACK);

        //Draws the ball with 5 radius
        StdDraw.filledCircle(ballX, ballY, 5);
        StdDraw.show();
    }

    /**
     * Displays Angle and Velocity information on left corner,
     * Displays game menu information on the right corner
     * @param velocity initial scalar velocitiy magnitude
     * @param angle initial angle of shoot
     */
    public static void writeInfo(double velocity,double angle){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(60,370,"Velocity:"+velocity);
        StdDraw.text(50,350,"Angle:"+angle);

        StdDraw.text(1150,370,"Press P to play again");
        StdDraw.text(1150,350,"Press Q to quit");
    }

    /**
     * Displays Angle and Velocity information on left corner,
     * Displays the game winning situation below movement information
     * @param velocity initial scalar velocitiy magnitude
     * @param angle initial angle of shoot
     * @param win a boolean value, true means you win, false means you lost
     */
    public static void writeInfo(double velocity,double angle,boolean win){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(60,370,"Velocity:"+velocity);
        StdDraw.text(50,350,"Angle:"+angle);

        Font font2 = new Font("Arial", Font.BOLD, 55);
        StdDraw.setFont(font2);

        if (win){
            StdDraw.setPenColor(60, 179, 113);
            StdDraw.text(645,320,"You Win!");}
        else{
            StdDraw.setPenColor(196,61,61);
            StdDraw.text(645,320,"You Lost!");
        }
    }

    /**
     * Ends the game
     */
    public static void quit(){
        StdDraw.clear();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(625,200,"YOU LEFT FROM THE GAME");
        StdDraw.show();
    }

}
