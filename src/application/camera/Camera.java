package application.camera;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Camera extends JFrame {

    private JLabel cameraScreen;
    private JButton spiegeln;
    private JSlider slider;
    private VideoCapture video;
    private float camSize = 0.0f;
    private Mat image, gray;
    private boolean clicked = false;
    public boolean right = false;
    public boolean left = false;

    /**
     * Fenster, Knopf und Schieberegler erstellen
     */
    public Camera()
    {
        setLayout(null); // Kein Layout

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        createButton();
        createSlider();

        setSize(new Dimension(640, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Schieberegler für die größe
     */
    private void createSlider(){

        slider = new JSlider();
        slider.setBounds(450, 470, 150, 50);
        slider.setMinimum(1);
        slider.setMaximum(100);
        add(slider);
    }

    /**
     * Knopf zum spiegeln
     */
    private void createButton(){

        spiegeln = new JButton("Spiegeln");
        spiegeln.setBounds(300, 480, 120, 40);
        add(spiegeln);

        spiegeln.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clicked = !clicked;
            }
        });
    }

    /**
     * Kamerabilder bearbeiten
     */
    private void editImage(){

        //Graubild erstellen
        Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);

        //Rauschen entfernen, wird aber unscharf
        Imgproc.blur(image, image, new Size(20, 20));

        //Grauwerte von 50 bis 255 werden zu weiß
        Core.inRange(image, new Scalar(51), new Scalar(255), gray);
        image.setTo(new Scalar(255), gray);

        //Grauwerte von 0 bis 50 werden zu schwarz
        Core.inRange(image, new Scalar(0), new Scalar(50), gray);
        image.setTo(new Scalar(0), gray);
    }


    /**
     * Wenn die Linien in der Kamera von etwas dunklen "berührt" werden
     */
    private void linesTouched(){

        // Rechte Position
        int r = image.width() / 5;
        for(int i = 0; i < image.height(); i++) {

            if (image.get(i, r)[0] <= 50) {

                if(!left) {right = true;} break;
            }else{
                right = false;
            }
        }

        // Linke Position
        int l = image.width() - image.width() / 5;
        for(int i = 0; i < image.height(); i++){

            if(image.get(i, l)[0] <= 50){

                if(!right) { left = true;} break;
            }else{
                left = false;
            }
        }
    }

    /**
     * Die Linien zeichnen
     */
    private void drawLines(){

        Imgproc.line (
                image,
                new Point(image.width() /5, 0),
                new Point(image.width() /5, image.height()),
                new Scalar(0, 0, 255),
                2);

        Imgproc.line (
                image,
                new Point(image.width() - (image.width() /5), 0),
                new Point(image.width() - (image.width() /5), image.height()),
                new Scalar(0, 0, 255),
                2);
    }


    /**
     * Kamerabild verkleinern/vergrößern
     */
    private void sizeImage(){

        camSize = (float) (slider.getValue() / 100.0);
        Imgproc.resize(image, image, new Size(0, 0), camSize, camSize, Imgproc.INTER_AREA); // größe
    }


    /**
     * Kamerabild spiegeln
     */
    private void spiegeln(){

        Core.flip(image, image, 1); //Spiegeln

        if(clicked) { // Wenn auf Butten geklickt

            Core.flip(image, image, 1); //Spiegeln
        }
    }

    /**
     * Kamera starten und Bild bearbeiten
     */
    public void startCamera()
    {
        video = new VideoCapture(0);
        image = new Mat();
        gray = new Mat();
        final MatOfByte buf = new MatOfByte();

        byte[] imageData;
        ImageIcon icon;
        icon = new ImageIcon();


        while (true) {

            video.read(image); // Bild in Matrix

            editImage();
            linesTouched();
            spiegeln();
            sizeImage();
            drawLines();

            Imgcodecs.imencode(".jpg", image, buf);
            imageData = buf.toArray();

            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
        }
    }
}