package application;

import java.awt.*;
import java.awt.event.*;

/**
 * Java class for handling the keyboard and mouse interaction.
 * Intented to be used for an OpenGL scene renderer.
 * @author Karsten Lehn
 * @version 23.8.2017, 10.9.2017, 22.9.2018
 */

public class InteractionHandler implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

    // Constant for debugging purposes
    private static final boolean VERBOSE = false;

    // Variables for camera distance
    private float eyeZ = 2f;
    private float eyeZInc = 0.01f;
    // Variables for scene rotation
    private float angleXaxis = 0f;
    private float angleYaxis = 0f;
    private float angleXaxisInc = 0.5f;
    private float angleYaxisInc = 0.5f;


    // Variables for scene translation
    private float xPosition = 0f;
    private float yPosition = 0f;
    private float zPosition = 0f;  //Hinzugefügt

    private float xPositionInc = 2f;
    private float yPositionInc = 2f;
    private float zPositionInc = 2f; // //Hinzugefügt


    // Variables for keyboard control
    private boolean ctrlKeyPressed = false;
    // Variables for mouse control
    private boolean leftMouseButtonPressed = false;
    private boolean rightMouseButtonPressed = false;
    private Point lastMouseLocation;
    // Taking care of the screen size (mapping of mouse coordinates to angle/translation)
    private final float mouseRotationFactor = 0.01f;
    private final float mouseTranslationFactor = 0.1f;
    private final float mouseWheelScrollFactor = 1000f;

    /**
     * Standard constructor for creation of the interaction handler.
     */
    public InteractionHandler() {}



    public boolean isCtrlKeyPressed() {
        return ctrlKeyPressed;
    }

    public void setCtrlKeyPressed(boolean ctrlKeyPressed) {
        this.ctrlKeyPressed = ctrlKeyPressed;
    }



    public float getEyeZ() {
        return eyeZ;
    }

    public void setEyeZ(float eyeZ) {
        this.eyeZ = eyeZ;
    }

    public float getEyeZInc() {
        return eyeZInc;
    }

    public void setEyeZInc(float eyeZInc) {
        this.eyeZInc = eyeZInc;
    }

    public float getAngleXaxis() {
        return angleXaxis;
    }

    public void setAngleXaxis(float angleXaxis) {
        this.angleXaxis = angleXaxis;
    }

    public float getAngleYaxis() {
        return angleYaxis;
    }

    public void setAngleYaxis(float angleYaxis) {
        this.angleYaxis = angleYaxis;
    }

    public float getAngleXaxisInc() {
        return angleXaxisInc;
    }

    public void setAngleXaxisInc(float angleXaxisInc) {
        this.angleXaxisInc = angleXaxisInc;
    }

    public float getAngleYaxisInc() {
        return angleYaxisInc;
    }

    public void setAngleYaxisInc(float angleYaxisInc) {
        this.angleYaxisInc = angleYaxisInc;
    }

    public float getxPosition() {
        return xPosition;
    }

    public float getzPosition() {
        return zPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public void setzPosition(float zPosition) {
        this.zPosition = zPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getxPositionInc() {
        return xPositionInc;
    }

    public void setxPositionInc(float xPositionInc) {
        this.xPositionInc = xPositionInc;
    }

    public void setzPositionInc(float zPositionInc) {
        this.zPositionInc = zPositionInc;
    }

    public float getyPositionInc() {
        return yPositionInc;
    }

    public void setyPositionInc(float yPositionInc) {
        this.yPositionInc = yPositionInc;
    }

    public float getMouseRotationFactor() {
        return mouseRotationFactor;
    }

    public float getMouseTranslationFactor() {
        return mouseTranslationFactor;
    }

    public float getMouseWheelScrollFactor() {
        return mouseWheelScrollFactor;
    }

    @Override
    /**
     * Implements a method from the interface KeyListener
     * Handles all key input.
     */
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_CONTROL:
                ctrlKeyPressed = true;
                break;

                // InteractionHandler Kasse
                // Code verändert. Durch die Welt mit W, A, S, D bewegen können
            case KeyEvent.VK_A:
                    xPosition += getEyeZ()/20; //+= getEyeZ(), weil je weiter die Kamera weg ist,
                break;                         //desto schneller soll man sich bewegen können.
            case KeyEvent.VK_D:
                    xPosition -= getEyeZ()/20;
                break;

            case KeyEvent.VK_W:
                zPosition += getEyeZ()/20;
                break;

            case KeyEvent.VK_S:
                zPosition -= getEyeZ()/20;
                break;
            case KeyEvent.VK_UP:
                    yPosition -= getEyeZ()/20;
                break;
            case KeyEvent.VK_DOWN:
                    yPosition += getEyeZ()/20;
                break;



            case KeyEvent.VK_MINUS:
                eyeZ = eyeZ - eyeZInc;
                break;
            case KeyEvent.VK_PLUS:
                eyeZ = eyeZ + eyeZInc;
                break;
        }
    }

    @Override
    /**
     * Implements one method of the interface KeyListener
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            ctrlKeyPressed = false;
        }
    }

    @Override
    /**
     * Implements one method of the interface KeyListener
     */
    public void keyTyped(KeyEvent e) { }


    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mousePressed(MouseEvent e) {
        int pressedButton = e.getButton();
        lastMouseLocation = e.getLocationOnScreen();
        if (VERBOSE) {
            System.out.print("Mouse pressed event. ");
            switch (pressedButton) {
                case MouseEvent.BUTTON1:
                    System.out.print("Left mouse button pressed.");
                    break;
                case MouseEvent.BUTTON2:
                    System.out.print("Mouse wheel or middle button pressed.");
                    break;
                case MouseEvent.BUTTON3:
                    System.out.print("Right mouse button pressed.");
                    break;
                case MouseEvent.NOBUTTON:
                    System.out.print(" No button detected.");
                    break;
                default:
                    System.out.print("Unknown button pressed.");
            }
            System.out.println(" At location: " + lastMouseLocation);
        }
        switch (pressedButton) {
            case MouseEvent.BUTTON1:
                leftMouseButtonPressed = true;
                break;
            case MouseEvent.BUTTON3:
                rightMouseButtonPressed = true;
                break;
        }
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseReleased(MouseEvent e) {
        int releasedButton = e.getButton();
        if (VERBOSE) {
            System.out.print("Mouse pressed event. ");
            switch (releasedButton) {
                case MouseEvent.BUTTON1:
                    System.out.println("Left mouse button released.");
                    break;
                case MouseEvent.BUTTON2:
                    System.out.println("Mouse wheel or middle button released.");
                    break;
                case MouseEvent.BUTTON3:
                    System.out.println("Right mouse button released.");
                    break;
                case MouseEvent.NOBUTTON:
                    System.out.println(" No button detected.");
                    break;
                default:
                    System.out.println("Unknow button pressed.");
            }
        }
        switch (releasedButton) {
            case MouseEvent.BUTTON1:
                leftMouseButtonPressed = false;
                break;
            case MouseEvent.BUTTON3:
                rightMouseButtonPressed = false;
                break;
        }
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    /**
     * Implements one method of the interface MouseListener
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Implements one method of the interface MouseMotionListener
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point currentMouseLocation = e.getLocationOnScreen();
        if (VERBOSE) {
            System.out.print("Mouse dragged event.");
            System.out.println(" At mouse location: " + currentMouseLocation);
        }
        double deltaX = currentMouseLocation.getX() - lastMouseLocation.getX();
        double deltaY = currentMouseLocation.getY() - lastMouseLocation.getY();
        lastMouseLocation = currentMouseLocation;
        // holding the left mouse button rotates the scene
        if (leftMouseButtonPressed) {
            angleYaxis += angleYaxisInc * mouseRotationFactor * -deltaX;
            angleXaxis += angleXaxisInc * mouseRotationFactor * -deltaY;
        }
        // holding the right mouse button translates the scene
        if (rightMouseButtonPressed) {
            xPosition += xPositionInc * mouseTranslationFactor * -deltaX;
            yPosition += yPositionInc * mouseTranslationFactor * +deltaY;
        }
    }

    /**
     * Implements one method of the interface MouseMotionListener
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Implements one method of the interface MouseMWheelMovedListener
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (VERBOSE) {
            System.out.print("Mouse wheel moved event.");
            System.out.println(" Wheel rotation: " + e.getPreciseWheelRotation());
        }
        eyeZ += eyeZInc * mouseWheelScrollFactor * e.getPreciseWheelRotation();
    }
}



//   case KeyEvent.VK_W:
//           xPosition += xPositionInc;
//           cameraPos.mulAdd(5, cameraPos); //w
//
//           break;
//           case KeyEvent.VK_S:
//           xPosition -= xPositionInc;
//           cameraPos.sub(cameraFront.mul(0.5f), cameraPos); //s
//
//    double deltaX = currentMouseLocation.getX() - lastMouseLocation.getX();
//    double deltaY = currentMouseLocation.getY() - lastMouseLocation.getY();
//        lastMouseLocation = currentMouseLocation;
//                // holding the left mouse button rotates the scene
//                if (leftMouseButtonPressed) {
//                angleYaxis += angleYaxisInc * mouseRotationFactor * -deltaX;
//                angleXaxis += angleXaxisInc * mouseRotationFactor * -deltaY;
//
//                if(angleXaxis >= 1.5){
//                angleXaxis = 1.5f;
//                }
//
//                if(angleXaxis <= -1.5){
//                angleXaxis = -1.5f;
//                }
//
//                if(angleYaxis >= 6.3){
//                angleYaxis = 0f;
//                }
//
//                if(angleYaxis <= -6.3){
//                angleYaxis = 0f;
//                }
//
//                float X = (float) ((360 / -6.3)*angleXaxis);
//                float Y = (float) ((360 / -6.3)*angleYaxis);
//
//
//                front.x = (float) (Math.cos(Math.toRadians(-X)) * Math.cos(Math.toRadians(Y)));
//                front.y = (float) Math.sin(Math.toRadians(-X));
//                front.z = (float) (Math.cos(Math.toRadians(-X)) * Math.sin(Math.toRadians(Y)));
//                front.normalize();
//
//
//
//
//
//    Vector3f cameraPos   = new Vector3f(0.0f, 0.0f,  3.0f);
//    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
//    Vector3f cameraUp    = new Vector3f(0.0f, 1.0f,  0.0f);
//
//
//    public Vector3f getCameraPos() {
//        return cameraPos;
//    }
//
//    public void setCameraPos(Vector3f cameraPos) {
//        this.cameraPos = cameraPos;
//    }
//
//    public Vector3f getCameraFront() {
//        return cameraFront;
//    }
//
//    public void setCameraFront(Vector3f cameraFront) {
//        this.cameraFront = cameraFront;
//    }
//
//    public Vector3f getCameraUp() {
//        return cameraUp;
//    }
//
//    public void setCameraUp(Vector3f cameraUp) {
//        this.cameraUp = cameraUp;
//    }
