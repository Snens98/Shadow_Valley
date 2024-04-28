package application.objects.complexObjects;

import application.camera.Camera;

public class Sonne {

    private float sunSpeed = 0.001f; //Geschwindigkeit der Sonne
    private float speed = 0.0f; // Wert entweder 0.0 oder Wert von sunSpeed

    private int pm = -1;        // Vorzeichen für links und rechts

    private float f1 = 0.0f;    //Variable zum aufsummieren der Werte für (Cos und Sin)

    private float sin = 0.0f;   // Sinuswert
    private float cos = 0.0f;   // Cosinuswert

    private float rH = 800;     // Horizontaler Radius
    private float rV = 800;     // Vertikaler Radius

    /**
     * Berechnung des Sonnenlichts
     * @param camera
     * @param lightPosition
     */
    public void SunLight(Camera camera, float[] lightPosition){

        // Für die Geschwindigkeit der Sonne
        if(camera.right || camera.left){ // Wenn sich im rechten oder linken Bereich der Kamera (Webcam) etwas dunkles befindet...
            speed = sunSpeed;             // wird die Sonnengeschwindigkeit gesetzt
        }else{                             // Wenn nicht...
            speed = 0.0f;                   // Geschwindigkeit gleich 0.0
        }

        // Für die Richtung der Sonne (negativ oder positiv)
        if(camera.right){
            pm = (+1);
        }else if(camera.left){
            pm = (-1);
        }

        // Berechnung der Lichtbewegung der Sonne.
        sin = (float) Math.sin(f1+=speed*pm);
        cos = (float) Math.cos(f1+=speed*pm);

        lightPosition[0] = -0.0f;
        lightPosition[1] = -(float) (cos * rH);
        lightPosition[2] = -(float) (sin * rV);
        lightPosition[3] = 1.0f;
    }


    /**
     * Tag-Nacht-Zyklus
     * @param lightAmbientColor
     * @param lightDiffuseColor
     * @param lightSpecularColor
     */
    public void setDayNight(float[] lightAmbientColor, float[] lightDiffuseColor, float[] lightSpecularColor) {

        if ((cos <= -0.3 && cos >= -1.0)) { //Wenn sich die Sonne unten befindet...

            for (int i = 0; i < 4; i++) { // Nacht
                lightAmbientColor[i] = 0.6f;
                lightDiffuseColor[i] = 0.6f;
                lightSpecularColor[i] = 0.6f;
            }
        } else {                            //Wenn nicht
//           ist es Tag
            lightAmbientColor[0] = 1.0f;
            lightAmbientColor[1] = 1.0f;
            lightAmbientColor[2] = 0.4f;
            lightAmbientColor[3] = 1.0f;

            lightDiffuseColor[0] = 1.0f;
            lightDiffuseColor[1] = 1.0f;
            lightDiffuseColor[2] = 0.4f;
            lightDiffuseColor[3] = 1.0f;

            lightSpecularColor[0] = 0.6f;
            lightSpecularColor[1] = 0.6f;
            lightSpecularColor[2] = 0.2f;
            lightSpecularColor[3] = 0.8f;
        }
    }


    /**
     * Cosinus-Wert für die Umlaufbahn der Sonne
     * @return
     */
    public float getCosSun(){

        float sunCos;
        return sunCos = (float) (Math.cos(f1+=(speed*pm)) * rH);
    }

    /**
     * Sinus-Wert für die Umlaufbahn der Sonne
     * @return
     */
    public float getSinSun(){

        float sunSin;
        return sunSin = (float) (Math.sin(f1+=(speed*pm)) * rV);
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}



