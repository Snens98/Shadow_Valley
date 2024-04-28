package application.material;

import java.util.ArrayList;

public class Material {

    public static ArrayList<MaterialManager> treeMat = new ArrayList<>();
    public static ArrayList<MaterialManager> Haus1Mat = new ArrayList<>();
    public static ArrayList<MaterialManager> Haus2Mat = new ArrayList<>();


    static float[] bodenKA = {0.10f, 0.10f, 0.10f}; // Ambient
    static float[] bodenKD = {0.8f, 0.8f, 0.8f};    // Diffuse
    static float[] bodenKS = {0.7f, 0.3f, 0.1f};    // Specular
    static float[] bodenKE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float bodenSH = 1.5f;                    // Shininess

    static float[] sunKA = {1.0f, 0.9f, 0.6f};    // Ambient
    static float[] sunKD = {1.0f, 0.9f, 0.6f};    // Diffuse
    static float[] sunKS = {1.0f, 0.9f, 0.6f};    // Specular
    static float[] sunKE = {1.0f, 0.9f, 0.6f};    // Emmision
    static float sunSH = 1.5f;                    // Shininess

    static float[] moonKA = {0.5f, 0.4f, 0.3f};    // Ambient
    static float[] moonKD = {0.5f, 0.4f, 0.3f};    // Diffuse
    static float[] moonKS = {0.5f, 0.4f, 0.3f};    // Specular
    static float[] moonKE = {0.5f, 0.4f, 0.3f};    // Emmision
    static float moonSH = 1.5f;                    // Shininess

    static float[] baum1KA = {0.3f, 0.0f, 0.0f};    // Ambient
    static float[] baum1KD = {1.0f, 0.0f, 0.0f};    // Diffuse
    static float[] baum1KS = {0.4f, 0.0f, 0.0f};    // Specular
    static float[] baum1KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float baum1SH = 1.5f;                    // Shininess
                            //Rot   gruen  blau
    static float[] baum2KA = {0.0f, 0,0f, 0.0f};    // Ambient
    static float[] baum2KD = {0.0f, 1.0f, 0.0f};    // Diffuse
    static float[] baum2KS = {0.0f, 0.0f, 0.0f};    // Specular
    static float[] baum2KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float baum2SH = 1.5f;                    // Shininess

    static float[] baum3KA = {0.3f, 0,0f, 0.05f};    // Ambient
    static float[] baum3KD = {1.0f, 1.0f, 0.1f};    // Diffuse
    static float[] baum3KS = {0.4f, 0.0f, 0.05f};    // Specular
    static float[] baum3KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float baum3SH = 1.5f;                    // Shininess

    static float[] baum4KA = {0.0f, 0,0f, 0.0f};    // Ambient
    static float[] baum4KD = {1.0f, 1.0f, 1.0f};    // Diffuse
    static float[] baum4KS = {0.0f, 0.0f, 0.0f};    // Specular
    static float[] baum4KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float baum4SH = 1.5f;                    // Shininess

    static float[] baum5KA = {0.0f, 0.0f, 0.0f};    // Ambient
    static float[] baum5KD = {0.2f, 1.0f, 0.0f};    // Diffuse
    static float[] baum5KS = {0.0f, 0.0f, 0.0f};    // Specular
    static float[] baum5KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float baum5SH = 1.5f;                    // Shininess





    static float[] haus11KA = {0.1f, 0.05f, 0.05f};    // Ambient
    static float[] haus11KD = {0.2f, 0.1f, 0.1f};    // Diffuse
    static float[] haus11KS = {0.1f, 0.05f, 0.05f};    // Specular
    static float[] haus11KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus11SH = 1.5f;                    // Shininess

    static float[] haus12KA = {0.2f, 0.05f, 0.05f};    // Ambient
    static float[] haus12KD = {0.8f, 0.2f, 0.2f};    // Diffuse
    static float[] haus12KS = {0.2f, 0.05f, 0.05f};    // Specular
    static float[] haus12KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus12SH = 1.5f;                    // Shininess

    static float[] haus13KA = {0.15f, 0.0f, 0.15f};    // Ambient
    static float[] haus13KD = {0.7f, 0.0f, 0.9f};    // Diffuse
    static float[] haus13KS = {0.2f, 0.0f, 0.02f};    // Specular
    static float[] haus13KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus13SH = 1.5f;                    // Shininess

    static float[] haus14KA = {0.2f, 0.2f, 0.2f};    // Ambient
    static float[] haus14KD = {1.0f, 1.0f, 1.0f};    // Diffuse
    static float[] haus14KS = {0.3f, 0.3f, 0.3f};    // Specular
    static float[] haus14KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus14SH = 1.5f;                    // Shininess




    static float[] haus21KA = {0.15f, 0.1f, 0.05f};    // Ambient
    static float[] haus21KD = {0.4f, 0.3f, 0.1f};    // Diffuse
    static float[] haus21KS = {0.25f, 0.2f, 0.05f};    // Specular
    static float[] haus21KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus21SH = 1.5f;                    // Shininess

    static float[] haus22KA = {0.2f, 0.05f, 0.05f};    // Ambient
    static float[] haus22KD = {0.6f, 0.1f, 0.1f};    // Diffuse
    static float[] haus22KS = {0.2f, 0.05f, 0.05f};    // Specular
    static float[] haus22KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus22SH = 1.5f;                    // Shininess

    static float[] haus23KA = {0.15f, 0.3f, 0.2f};    // Ambient
    static float[] haus23KD = {0.3f, 0.7f, 0.5f};    // Diffuse
    static float[] haus23KS = {0.2f, 0.5f, 0.02f};    // Specular
    static float[] haus23KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus23SH = 1.5f;                    // Shininess

    static float[] haus24KA = {0.1f, 0.2f, 0.3f};    // Ambient
    static float[] haus24KD = {0.5f, 0.6f, 0.6f};    // Diffuse
    static float[] haus24KS = {0.2f, 0.3f, 0.4f};    // Specular
    static float[] haus24KE = {0.0f, 0.0f, 0.0f};    // Emmision
    static float haus24SH = 1.5f;                    // Shininess



    /**
     * Material erstellen und in Liste speichern
     */
    public static void init() {


        MaterialManager boden = new MaterialManager(bodenKE, bodenKA, bodenKD, bodenKS, bodenSH, "boden");
        MaterialManager sonne = new MaterialManager(sunKE, sunKA, sunKD, sunKS, sunSH, "sonne");
        MaterialManager mond = new MaterialManager(moonKE, moonKA, moonKD, moonKS, moonSH, "mond");

        MaterialManager baumRot = new MaterialManager(baum1KE, baum1KA, baum1KD, baum1KS, baum1SH, "baumRot");
        MaterialManager baumGruen = new MaterialManager(baum2KE, baum2KA, baum2KD, baum2KS, baum2SH, "baumGruen");
        MaterialManager baumGelb = new MaterialManager(baum3KE, baum3KA, baum3KD, baum3KS, baum3SH, "baumGelb");
        MaterialManager baumWeis = new MaterialManager(baum4KE, baum4KA, baum4KD, baum4KS, baum4SH, "baumWeis");
        MaterialManager baumOrange = new MaterialManager(baum5KE, baum5KA, baum5KD, baum5KS, baum5SH, "baumOrange");

        treeMat.add(baumRot);
        treeMat.add(baumGruen);
        treeMat.add(baumGelb);
        treeMat.add(baumWeis);
        treeMat.add(baumOrange);





        MaterialManager haus11 = new MaterialManager(haus11KE, haus11KA, haus11KD, haus11KS, haus11SH, "haus11");
        MaterialManager haus12 = new MaterialManager(haus12KE, haus12KA, haus12KD, haus12KS, haus12SH, "haus12");
        MaterialManager haus13 = new MaterialManager(haus13KE, haus13KA, haus13KD, haus13KS, haus13SH, "haus13");
        MaterialManager haus14 = new MaterialManager(haus14KE, haus14KA, haus14KD, haus14KS, haus14SH, "haus14");

        Haus1Mat.add(haus11);
        Haus1Mat.add(haus12);
        Haus1Mat.add(haus13);
        Haus1Mat.add(haus14);





        MaterialManager haus21 = new MaterialManager(haus21KE, haus21KA, haus21KD, haus21KS, haus21SH, "haus21");
        MaterialManager haus22 = new MaterialManager(haus22KE, haus22KA, haus22KD, haus22KS, haus22SH, "haus22");
        MaterialManager haus23 = new MaterialManager(haus23KE, haus23KA, haus23KD, haus23KS, haus23SH, "haus23");
        MaterialManager haus24 = new MaterialManager(haus24KE, haus24KA, haus24KD, haus24KS, haus24SH, "haus24");

        Haus2Mat.add(haus21);
        Haus2Mat.add(haus22);
        Haus2Mat.add(haus23);
        Haus2Mat.add(haus24);

    }
}