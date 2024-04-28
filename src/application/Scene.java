package application;
/*
 * Copyright 2012-2013 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY JogAmp Community ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JogAmp Community OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of JogAmp Community.
 */

import application.material.Material;
import application.material.MaterialManager;
import application.objLoader.ModelFromFile;
import application.camera.Camera;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.objects.VAO;
import application.objects.complexObjects.*;
import application.objects.primitiveObjects.BoxTex;
import application.objects.primitiveObjects.Sphere;
import application.shader.ShaderProgram;
import application.texture.Texture;
import application.texture.TextureManager;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import application.shader.ShaderFile;
import org.opencv.core.Core;

import java.awt.*;

import static com.jogamp.opengl.GL.*;

/**
 * Performs the OpenGL rendering
 * Uses the programme pipeline commands in the core profile only.
 * Thus, a vertex and fragment shader is used.
 *
 * Rotation and translation of the camera is included.
 * 	    Use keyboard: left/right/up/down-keys and +/-Keys
 * 	    Alternatively use mouse movements:
 * 	        press left/right button and move and use mouse wheel
 *
 * Loads a model using an OBJ-Loader
 * Serves as a template (start code) for setting up an OpenGL/Jogl application
 * which is using a vertex and fragment shader in the core profile.
 *
 * Please make sure setting the file path and names of the shaders correctly (see below).
 *
 * Based on a tutorial by Chua Hock-Chuan
 * http://www3.ntu.edu.sg/home/ehchua/programming/opengl/JOGL2.0.html
 *
 * and on an example by Xerxes Rånby
 * http://jogamp.org/git/?p=jogl-demos.git;a=blob;f=src/demos/es2/RawGL2ES2demo.java;hb=HEAD
 *
 * @author Karsten Lehn
 * @version 3.9.2015, 15.9.2015, 18.9.2015, 10.9.2017, 2.10.2018, 7.10.2018
 *
 */
public class Scene extends GLCanvas implements GLEventListener {

    private static final long serialVersionUID = 1L;

    private ShaderProgram shaderProgram;
    private InteractionHandler interactionHandler;

    private Camera camera;           //  Kamera Objekt für den Bildverarbeitungsteil mit OpenCV

    private LightSource light;      // Licht für das Licht der Sonne
    private Sonne sun;                // Sun Objekt für die Sonne (und auch Mond)

    private PMVMatrix pmvMatrix;    // pmvMatrix Objekt mithilfe der joml 1.10.2 bibliothek.
                                    // Das schon vorhandene PMV-Tool hat bei unser Idee nicht klappen wollen.


    /**
     * Standard constructor for object creation.
     */
    public Scene() {
        super();
        this.addGLEventListener(this);
        createAndRegisterInteractionHandler();
    }


    /**
     * Create the canvas with the requested OpenGL capabilities
     * @param capabilities The capabilities of the canvas, including the OpenGL profile
     */
    public Scene(GLCapabilities capabilities) {
        super(capabilities);
        this.addGLEventListener(this);
        createAndRegisterInteractionHandler();
    }

    /**
     * Helper method for creating an interaction handler object and registering it
     * for key press and mouse interaction call backs.
     */
    private void createAndRegisterInteractionHandler() {

        interactionHandler = new InteractionHandler();
        this.addKeyListener(interactionHandler);
        this.addMouseListener(interactionHandler);
        this.addMouseMotionListener(interactionHandler);
        this.addMouseWheelListener(interactionHandler);
    }


    /**
     * War schon vorhanden, jetzt nur als Methode ausgelagert.
     */
    private void handleErrors(GL3 gl, GLAutoDrawable drawable){

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
    }


    /**
     * updatet die Kameraposition
     * War schon vorhanden, nur mit der pmvMatrix aus joml ersetzt.
     */
    private void updateCameraPosition() {

        pmvMatrix.getvMatrix().identity();

        /*
        Versuch eine etwas andere kamera zu implementieren.
        Weitere Programmschnipsel sind in der InteractionHandler-Klasse unten.
         */

//        if(interactionHandler.isCtrlKeyPressed()){}

//        pmvMatrix.getvMatrix().lookAt(interactionHandler.getCameraPos(),
//        interactionHandler.getCameraPos().add(interactionHandler.getCameraFront()),
//        interactionHandler.getCameraUp());


        pmvMatrix.getvMatrix().lookAt(0f,0f, interactionHandler.getEyeZ(),
                    0f, 0f, 0f,
                    0f, 1.0f, 0f);

        pmvMatrix.getvMatrix().rotate(interactionHandler.getAngleXaxis(), 1f, 0f, 0f);
        pmvMatrix.getvMatrix().rotate(interactionHandler.getAngleYaxis(), 0f, 1f, 0f);

        pmvMatrix.getvMatrix().translate(interactionHandler.getxPosition(),
                interactionHandler.getyPosition(), interactionHandler.getzPosition());
    }



    //Lichteigenschafften für die Sonne
    float[] lightPosition = {-1.0f, -1.0f, -1.0f, 1.0f};
    float[] lightAmbientColor = {1.0f, 1.0f, 0.4f, 1.0f};
    float[] lightDiffuseColor = {1.0f, 1.0f, 0.4f, 1.0f};
    float[] lightSpecularColor = {0.6f, 0.6f, 0.2f, 0.8f};

    /**
     * Hier wird die Sonne initialisiert.
     */
    private void initSun(){

        light = new LightSource(lightPosition, lightAmbientColor,
                lightDiffuseColor, lightSpecularColor);
    }


    /**
     * Hier wird das VAO für alle komplexen Objekte gesetzt.
     * ALle Komplexen Objekte werden mit "GL_TRIANGLES" gezeichnet.
     * @return
     */
    private VAO setVAO(){

        VAO vao;

        int[] count = {3, 2, 3};
        int[] size = {8 * Float.BYTES, 8 * Float.BYTES, 8 * Float.BYTES};
        int[] offset = {0, 3*4, 5*4};

        vao = new VAO();
        vao.setCount(count);
        vao.setSize(size);
        vao.setOffset(offset);

        return vao;
    }

    /**
     * Hier wird das VAO für alle "einfachen" Objekte gesetzt.
     * ALle einfache Objekte werden mit "GL_TRIANGLE_STRIP" gezeichnet. Z.B die Sonne als Sphere.
     * @return
     */
    private VAO setVAOSun(){

        VAO vao;

        int[] count = {3, 3, 3};
        int[] size = {9 * Float.BYTES, 9 * Float.BYTES, 9 * Float.BYTES};
        int[] offset = {0, 3*4, 6*4};

        vao = new VAO();
        vao.setCount(count);
        vao.setSize(size);
        vao.setOffset(offset);

        return vao;
    }



    //Sonne
    Sphere sphere = new Sphere(30,30);
    Sphere sphere2 = new Sphere(30,30);

    ObjectBuffer haus1_1, haus1_2;
    ObjectBuffer haus2dach, haus2wand, haus2treppe;
    ObjectBuffer boden, cube, bSphere, bSphere2;
    ObjectBuffer baum, baum2;
    ObjectBuffer avatar0, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6;
    ObjectBuffer lagerfeuer0, lagerfeuer1;
    ObjectBuffer flamme0, flamme1, flamme2, flamme3, flamme4;
    ObjectBuffer wegO;
    ObjectBuffer bankO;
    ObjectBuffer vogelO;
    ObjectBuffer brunnen0, brunnen1, brunnen2, brunnen3;
    ObjectBuffer bergO;
    ObjectBuffer laterne0, laterne1, laterne2, laterne3;


    /**
     * Hier werden alle verwendeten Objekte
     * (einfache Objekte oder auch Objekte, die aus mehreren einzelnen Objekten und/oder verschiedenen Materialien und Texturen zusammengesetzt sind)
     * für das initialisieren und zeichen vorbereitet.
     * @param gl
     */
    private void BufferObjects(GL3 gl) {

        boden = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, BoxTex.makeBoxIndicesForTriangleStrip(), BoxTex.makeBoxVertices(1000.0f, 6.0f, 800.0f), BoxTex.noOfIndicesForBox());
        cube = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, BoxTex.makeBoxIndicesForTriangleStrip(), BoxTex.makeBoxVertices(1.0f, 1.0f, 1.0f), BoxTex.noOfIndicesForBox());

        baum = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("baum").getIndices(), ModelFromFile.list.get("baum").getVerticies(), ModelFromFile.list.get("baum").getIndices().length);
        baum2 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("baum2").getIndices(), ModelFromFile.list.get("baum2").getVerticies(), ModelFromFile.list.get("baum2").getIndices().length);

        bSphere = new ObjectBuffer(gl, pmvMatrix, setVAOSun(), shaderProgram, sphere.makeIndicesForTriangleStrip(), sphere.makeVertices(50), sphere.getNoOfIndices());
        bSphere2 = new ObjectBuffer(gl, pmvMatrix, setVAOSun(), shaderProgram, sphere2.makeIndicesForTriangleStrip(), sphere2.makeVertices(40), sphere2.getNoOfIndices());

        haus1_1 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("haus1Dach").getIndices(), ModelFromFile.list.get("haus1Dach").getVerticies(), ModelFromFile.list.get("haus1Dach").getIndices().length);
        haus1_2 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("haus1Wand").getIndices(), ModelFromFile.list.get("haus1Wand").getVerticies(), ModelFromFile.list.get("haus1Wand").getIndices().length);

        haus2dach = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("haus2Dach").getIndices(), ModelFromFile.list.get("haus2Dach").getVerticies(), ModelFromFile.list.get("haus2Dach").getIndices().length);
        haus2wand = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("haus2Wand").getIndices(), ModelFromFile.list.get("haus2Wand").getVerticies(), ModelFromFile.list.get("haus2Wand").getIndices().length);
        haus2treppe = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("haus2Treppe").getIndices(), ModelFromFile.list.get("haus2Treppe").getVerticies(), ModelFromFile.list.get("haus2Treppe").getIndices().length);

        avatar0 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar0").getIndices(), ModelFromFile.list.get("avatar0").getVerticies(), ModelFromFile.list.get("avatar0").getIndices().length);
        avatar1 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar1").getIndices(), ModelFromFile.list.get("avatar1").getVerticies(), ModelFromFile.list.get("avatar1").getIndices().length);
        avatar2 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar2").getIndices(), ModelFromFile.list.get("avatar2").getVerticies(), ModelFromFile.list.get("avatar2").getIndices().length);
        avatar3 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar3").getIndices(), ModelFromFile.list.get("avatar3").getVerticies(), ModelFromFile.list.get("avatar3").getIndices().length);
        avatar4 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar4").getIndices(), ModelFromFile.list.get("avatar4").getVerticies(), ModelFromFile.list.get("avatar4").getIndices().length);
        avatar5 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar5").getIndices(), ModelFromFile.list.get("avatar5").getVerticies(), ModelFromFile.list.get("avatar5").getIndices().length);
        avatar6 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("avatar6").getIndices(), ModelFromFile.list.get("avatar6").getVerticies(), ModelFromFile.list.get("avatar6").getIndices().length);

        lagerfeuer0 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("lagerfeuer0").getIndices(), ModelFromFile.list.get("lagerfeuer0").getVerticies(), ModelFromFile.list.get("lagerfeuer0").getIndices().length);
        lagerfeuer1= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("lagerfeuer1").getIndices(), ModelFromFile.list.get("lagerfeuer1").getVerticies(), ModelFromFile.list.get("lagerfeuer1").getIndices().length);

        flamme0 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("flamme0").getIndices(), ModelFromFile.list.get("flamme0").getVerticies(), ModelFromFile.list.get("flamme0").getIndices().length);
        flamme1= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("flamme1").getIndices(), ModelFromFile.list.get("flamme1").getVerticies(), ModelFromFile.list.get("flamme1").getIndices().length);
        flamme2 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("flamme2").getIndices(), ModelFromFile.list.get("flamme2").getVerticies(), ModelFromFile.list.get("flamme2").getIndices().length);
        flamme3= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("flamme3").getIndices(), ModelFromFile.list.get("flamme3").getVerticies(), ModelFromFile.list.get("flamme3").getIndices().length);
        flamme4= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("flamme4").getIndices(), ModelFromFile.list.get("flamme4").getVerticies(), ModelFromFile.list.get("flamme4").getIndices().length);

        wegO = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("weg").getIndices(), ModelFromFile.list.get("weg").getVerticies(), ModelFromFile.list.get("weg").getIndices().length);

        bankO = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("bank").getIndices(), ModelFromFile.list.get("bank").getVerticies(), ModelFromFile.list.get("bank").getIndices().length);

        vogelO = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("vogel").getIndices(), ModelFromFile.list.get("vogel").getVerticies(), ModelFromFile.list.get("vogel").getIndices().length);

        brunnen0= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("eimer").getIndices(), ModelFromFile.list.get("eimer").getVerticies(), ModelFromFile.list.get("eimer").getIndices().length);
        brunnen1 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("brunnendach").getIndices(), ModelFromFile.list.get("brunnendach").getVerticies(), ModelFromFile.list.get("brunnendach").getIndices().length);
        brunnen2= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("brunnen").getIndices(), ModelFromFile.list.get("brunnen").getVerticies(), ModelFromFile.list.get("brunnen").getIndices().length);
        brunnen3= new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("wasser").getIndices(), ModelFromFile.list.get("wasser").getVerticies(), ModelFromFile.list.get("wasser").getIndices().length);

        laterne0 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("laterne0").getIndices(), ModelFromFile.list.get("laterne0").getVerticies(), ModelFromFile.list.get("laterne0").getIndices().length);
        laterne1 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("laterne1").getIndices(), ModelFromFile.list.get("laterne1").getVerticies(), ModelFromFile.list.get("laterne1").getIndices().length);
        laterne2 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("laterne2").getIndices(), ModelFromFile.list.get("laterne2").getVerticies(), ModelFromFile.list.get("laterne2").getIndices().length);
        laterne3 = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("laterne3").getIndices(), ModelFromFile.list.get("laterne3").getVerticies(), ModelFromFile.list.get("laterne3").getIndices().length);

        bergO = new ObjectBuffer(gl, pmvMatrix, setVAO(), shaderProgram, ModelFromFile.list.get("berg").getIndices(), ModelFromFile.list.get("berg").getVerticies(), ModelFromFile.list.get("berg").getIndices().length);
    }


    private Haus1[] haus = new Haus1[11];
    private Haus2[] haus2 = new Haus2[11];
    private Baum[] tree = new Baum[195];
    private Avatar[] avatar = new Avatar[35];
    private Laterne[] laterne = new Laterne[10];
    private Vogel[] vogel = new Vogel[8];
    private CubePyramide pyramide = new CubePyramide(5);

    private Object Osphere;
    private Object Osphere2;

    private Berg berg;
    private Lagerfeuer lagerfeuer;
    private Flamme flamme;
    private Object ground;

    private Weg weg;
    private Bank bank;
    private Brunnen brunnen;

    /**
     * Hier wird jedes Objekt initialisiert.
     * Wie u.a Startwerte, Positionierung, Material, Textur standardmäßig festgelegt.
     */
    private void initObjects(){

        Osphere = new Object(bSphere); //Sonne
        sun = new Sonne();

        Osphere2 = new Object(bSphere2); //Mond


        // Pyramide
        pyramide.init(cube);
        pyramide.translate(45, -2.5f, 60);
        pyramide.scale(5);

        // Boden
        ground = new Object(boden);


        //Mensch
        for(int i = 0; i < avatar.length; i++) {
            avatar[i] = new Avatar();
            avatar[i].init(avatar0, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6);
            avatar[i].scale((float) (0.5 + Math.random() * (0.8 - 0.5)));
        }
        avatar[1].set(10, 0.5f, 30, -0.1f);
        avatar[2].set(8, 0.5f, 28, -0.5f);
        avatar[3].set(18, 0.5f, -28, 1.0f);
        avatar[4].set(14, 0.5f, -25, 1.4f);

        //Erstellt 10 Mensch, die im Kreis stehen
        menschenKreis(97, 0.5f, -10, 5, 15, 15, 1);

        avatar[15].set(24, 0.5f, -5, 3.4f);
        avatar[16].set(40, 0.5f, -20, 4.9f);
        avatar[17].set(-54, 0.5f, -25, 6.1f);
        avatar[18].set(74, 0.5f, 25, 5.7f);
        avatar[19].set(14, 0.5f, 17, 3.5f);
        avatar[20].set(-14, 0.5f, -17, -3.5f);
        avatar[21].set(93, 0.5f, 117, -5.5f);
        avatar[22].set(114, 0.5f, -117, 4.3f);
        avatar[23].set(40, 0.5f, -27, -3.5f);
        avatar[24].set(94, 0.5f, 110, -3.9f);
        avatar[25].set(93, 0.5f, 90, -4.8f);
        avatar[26].set(91, 0.5f, 80, -3.7f);
        avatar[27].set(93, 0.5f, 75, 3.6f);
        avatar[28].set(94, 0.5f, 70, -3.5f);
        avatar[29].set(93, 0.5f, 65, 3.4f);
        avatar[30].set(91, 0.5f, 60, 3.3f);
        avatar[31].set(89, 0.5f, 50, 3.6f);
        avatar[32].set(86, 0.5f, 63, 3.4f);
        avatar[33].set(84, 0.5f, 69, 3.6f);
        avatar[34].set(80, 0.5f, 46, 3.5f);

        // baum
        for(int i = 0; i < tree.length; i++) {
            tree[i] = new Baum(0, 0, -8, 0);
            tree[i].init(baum, baum2);
            tree[i].setMaterial(0 + (int)(Math.random() * (((Material.treeMat.size()-1) - 0) +1)));
            tree[i].scale((float) ((2 + (int)(Math.random() * ((3 - 3) + 2)))) * 0.25f);
        }

        //Kreis aus Bäumen
        baumKreis(97, 0.5f, -10, 0, 15, 25, 1);

        //Baumreihe
        baumReihe(16, 23, 100, 100, 28);
        baumReihe(24, 29, 100, 80, 29);
        baumReihe(30, 33, 120, 60, 32);
        baumReihe(34, 38, 130, 40, 27);
        baumReihe(39, 41, 95, 20, 35);
        baumReihe(42, 46, 96, 0, 28);
        baumReihe(47, 51, 97, -20, 27);
        baumReihe(52, 59, -10, -40, 29);
        baumReihe(60, 66, 0, -60, 28);
        baumReihe(67, 69, 10, -80, 38);
        baumReihe(70, 75, -40, -32, 19);
        baumReihe(76, 79, -56, -15, 26);
        baumReihe(80, 81, -90, -5, 30);
        baumReihe(82, 83, -90, 10, 26);
        baumReihe(84, 85, -80, 25, 29);
        baumReihe(86, 87, -99, 65, 29);
        baumReihe(88, 91, -80, 80, 28);
        baumReihe(92, 99, -200, 100, 32);
        baumReihe(100, 117, -250, 120, 35);
        baumReihe(118, 132, -250, 140, 40);
        baumReihe(133, 137, -250, 20, 31);
        baumReihe(138, 141, -260, 0, 34);
        baumReihe(142, 144, -260, -25, 36);
        baumReihe(145, 149, -250, -55, 40);
        baumReihe(150, 151, -210, -90, 40);
        baumReihe(152, 158, -220, -110, 33);
        baumReihe(155, 159, -210, -100, 42);
        baumReihe(160, 162, -180, -160, 44);
        baumReihe(163, 171, -140, -190, 48);
        baumReihe(172, 180, -90, -220, 44);
        baumReihe(181, 187, -30, -240, 38);
        baumReihe(188, 194, 0, -260, 34);



        // Haus
        for(int i = 0; i < haus.length; i++) {

            haus[i] = new Haus1();
            haus[i].init(haus1_1, haus1_2);
            haus[i].setMaterial(0 + (int)(Math.random() * (((Material.Haus1Mat.size()-1) - 0) +1)));

        }

        haus[0].set(0.9f, 1.8f, 0, 0.5f, 10);
        haus[1].set(0.75f, 90f, 0, 0.5f, -80);
        haus[2].set(0.9f, 90f, 245, 0.5f, -145);
        haus[3].set(1.1f, 90f, 285, 0.5f, -165);
        haus[4].set(1.0f, 59.5f, -110, 0.5f, -20);
        haus[5].set(0.9f, 1.5f, 50, 0.5f, 0);
        haus[6].set(0.85f, 5f, -50, 0.5f, -25);
        haus[7].set(0.8f, 5.1f, 0, 0.5f, 55);
        haus[8].set(0.9f, 6.6f, 115, 0.5f, 50);
        haus[9].set(1.15f, 5.4f, -90, 0.5f, 80);
        haus[10].set(1.12f, 4.4f, 300, 0.5f, 70);


        //Haus2
        for(int i = 0; i < haus2.length; i++) {

            haus2[i] = new Haus2();
            haus2[i].init(haus2dach, haus2wand, haus2treppe);
            haus2[i].scale(1.0f);
        }

        haus2[0].set(1.0f, 279f, 340, 1.5f, -200);
        haus2[1].set(1.15f, 279f, -50, 1.5f, -75);
        haus2[2].set(1.1f, 2f, 65, 1.5f, -155);
        haus2[3].set(0.9f, 1f, 125, 1.5f, -160);
        haus2[4].set(1.2f, 1f, 170, 1.5f, -150);
        haus2[5].set(1.0f, 5.1f, 100, 1.5f, -100);
        haus2[6].set(0.9f, 3.8f, 240, 1.5f, -65);
        haus2[7].set(0.9f, 7.4f, -170, 1.5f, 40);
        haus2[8].set(0.96f, 6.5f, 295, 1.5f, -20);
        haus2[9].set(1.15f, 7.6f, 380, 1.5f, 20);
        haus2[10].set(1.32f, 5.5f, 400, 1.5f, -180);


        //Lagerfeuer
        lagerfeuer = new Lagerfeuer(100, 0.7f, -10, 0);
        lagerfeuer.init(lagerfeuer0, lagerfeuer1);
        lagerfeuer.scale(2.75f);


        //Flamme
        flamme = new Flamme(97.1f, 1.0f, -10, 0);
        flamme.init(flamme0, flamme1, flamme2, flamme3, flamme4);
        flamme.scale(1.1f);


        //Weg -2.1f
        weg = new Weg(0.0f, 0.4f, 0.0f, 0.0f);
        weg.init(wegO);
        weg.scale(2.0f, 0.1f, 2.0f);

        //Bank
        bank = new Bank(110, 2, -11, 0);
        bank.init(bankO);
        bank.scale(1.5f);


        //Vogel
        for(int i = 0; i < vogel.length; i++) {

            vogel[i] = new Vogel();
            vogel[i].init(vogelO);
        }
        float size = 1.0f;
        for (int i = 0; i < vogel.length; i++) {
            vogel[i].scale(size+=0.25);
        }


        //Brunnen
        brunnen = new Brunnen(-40, 0.5f, 20, 0);
        brunnen.init(brunnen0, brunnen1, brunnen2, brunnen3);
        brunnen.scale(1.5f);


        //Berg
        berg = new Berg();
        berg.init(bergO);
        berg.translate(-300f, 10, -50);
        berg.scale(38, 25, 25);

        //Laternen
        for(int i = 0; i < laterne.length; i++) {
            laterne[i] = new Laterne();
            laterne[i].init(laterne0, laterne1, laterne2, laterne3);
            laterne[i].scale(2.5f);
        }

        laterne[0].set(18, -16, 20, 0);
        laterne[1].set(30, -16, 17, 0);
        laterne[2].set(-10, -16, 30, 0);
        laterne[3].set(-30, -16, 35, 0);
        laterne[4].set(100, -16, 70, -1.5f);
        laterne[5].set(88, -16, 40, -1.5f);
        laterne[6].set(-88, -16, 45, 1.0f);
        laterne[7].set(-120, -16, 66, 0.5f);
        laterne[8].set(-90, -16, 0, 2.0f);
        laterne[9].set(-90, -16, -20, 2.0f);
    }


    /**
     * Hier werden alle Objekte gezeichnet.
     * @param gl
     */
    private void displayObjects(GL3 gl){

        // Pyramide
        pyramide.kreisbahn(45, -2.5f, 60,0.0f, 0.0f, 0.005f);
        pyramide.draw(gl);

        //Baeume
        for(int i = 0; i < tree.length; i++) {
            tree[i].draw();
        }

        //Haus1 & Haus2
        for(int i = 0; i < haus.length; i++) {
            haus[i].draw();
            haus2[i].draw();
        }


        //Avatar
        avatar[0].kreisBewegung(-40, 0.5f, 20, 6, 6, 0.02f);

        for(int i = 0; i < avatar.length; i++) {
            avatar[i].draw();
        }


        //Lagerfeuer
        lagerfeuer.draw();
        flamme.setRandomScale(3.8f, 4.8f, 3.5f);
        flamme.draw();

        //Weg
        weg.draw();

        //Bank
        bank.draw();

        //Vogel
        vogel[0].flugbahn(290f,90.0f, -0.015f);
        vogel[1].flugbahn(50f,140.0f, 0.025f);
        vogel[2].flugbahn(80.0f,100.0f, 0.022f);
        vogel[3].flugbahn(200f,130.0f, 0.015f);
        vogel[4].flugbahn(290f,190.0f, -0.025f);
        vogel[5].flugbahn(220f,290.0f, 0.015f);
        vogel[6].flugbahn(420f,180.0f, -0.014f);
        vogel[7].flugbahn(360f,240.0f, 0.01f);

        for(int i = 0; i < vogel.length; i++) {
            vogel[i].draw();
        }

        //brunnen
        brunnen.draw();

        //Berg
        berg.draw();

        //Laterne
        for(int i = 0; i < laterne.length; i++) {
            laterne[i].draw();
        }
        // Boden
        drawGround(gl);

        //Sonne
        drawSun(gl);
        drawMoon(gl);
    }


    /**
     * Hier wird der Bildverarbeitungsteil mit der Bibliothek OpenCV-454 gestartet.
     *
     */
    private void startOpenCV(){

        camera = new Camera();

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // verwendete Library wird geladen

        EventQueue.invokeLater(new Runnable() {
            @Override public void run()
            {
                // Das Programm wird hier in einem neuen Thread ausgeführt, um die Performance
                // der anderen Programmteile nicht zu reduzieren
                new Thread(new Runnable() {
                    @Override public void run()
                    {
                        camera.startCamera();
                    }
                }).start(); //Thread wird gestartet
            }
        });
    }


    /**
     * War schon vorgegeben. Teile in Methoden ausgelagert und neue Teile zur initialisierung hinzugefügt.
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {

        GL3 gl = drawable.getGL().getGL3();
        handleErrors(gl, drawable);
        startOpenCV();

        pmvMatrix = new PMVMatrix();
        shaderProgram = new ShaderProgram(gl);
        shaderProgram.loadShaderAndCreateProgram(".\\resources\\", ShaderFile.vertexShader, ShaderFile.fragmentShader);

        interactionHandler.setEyeZ(100);

        // Switch on back face culling
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);

        // Switch on depth test.
        gl.glEnable(GL.GL_DEPTH_TEST);

        Material.init();
        ModelFromFile.init();
        Texture.init(gl);
        BufferObjects(gl);
        initSun();
        initObjects();

        // Hintergrundfarbe: Blau für den Himmel
        gl.glClearColor(0.0f, 0.4f, 0.98f, 1.0f);
    }




    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called by the OpenGL animator for every frame.
     * @param drawable The OpenGL drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {

        // Retrieve the OpenGL graphics context
        GL3 gl = drawable.getGL().getGL3();

        // Clear color and depth buffer
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        updateCameraPosition(); //Updatet die Kamera

        sun.SunLight(camera, lightPosition);                                       // Sonnenlicht mit Kamera steuern
        sun.setDayNight(lightAmbientColor, lightDiffuseColor, lightSpecularColor); // Tag-Nacht Licht

        light.updateLight(gl);      // Licht updaten

        displayObjects(gl);         // Zeigt alle Objekte an

//        fps();         //Berechnet die FPS und gibt sie in der Konsole aus
    }


    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called when the OpenGL window is resized.
     * @param drawable The OpenGL drawable
     * @param x x-coordinate of the viewport
     * @param y y-coordinate of the viewport
     * @param width width of the viewport
     * @param height height of the viewport
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();

        // width und height mit 1.25 multipliziert, um den gesamten Bildschirm zu füllen
        gl.glViewport(0, 0, (int) (width*1.25), (int) (height*1.25));

        pmvMatrix.getpMatrix().identity();
        pmvMatrix.getpMatrix().perspective(45f, (float) width/ (float) height, 0.1f, 2000f);
    }

    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called when OpenGL canvas ist destroyed.
     * @param drawable the drawable
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {

        // Retrieve the OpenGL graphics context
        GL3 gl = drawable.getGL().getGL3();

        // Detach and delete shader program
        gl.glUseProgram(0);
        shaderProgram.deleteShaderProgram();

        //VAO und VBO werden hier vor schließung des Programms deaktiviert
        for(ObjectBuffer objBuffer : ObjectBuffer.list){
            objBuffer.setDeactivate();
        }
        System.exit(0);
    }



    /**
     * Zeichnet den Boden der 3D-Welt mit Texture und Material
     * @param gl
     */
    private void drawGround(GL3 gl) {

        pmvMatrix.getmMatrix().pushMatrix();
        pmvMatrix.getmMatrix().translate(0, -2.5f, 0f);
        ground.setTexture(TextureManager.list.get(0), 100f);
        ground.setMaterial(MaterialManager.Mlist.get("boden"));
        ground.render(GL_TRIANGLE_STRIP);
        pmvMatrix.getmMatrix().popMatrix();
    }


    /**
     * Zeichnet die Sonne
     * @param gl
     */
    private void drawSun(GL3 gl) {

        pmvMatrix.getmMatrix().pushMatrix();

        pmvMatrix.getmMatrix().translate(10, sun.getCosSun(), sun.getSinSun());

        Osphere.setTexture(TextureManager.list.get(3), 1f);
        Osphere.setMaterial(MaterialManager.Mlist.get("sonne"));
        Osphere.render(GL_TRIANGLE_STRIP);
        pmvMatrix.getmMatrix().popMatrix();
    }

    /**
     * Zeichnet den Mond
     * @param gl
     */
    private void drawMoon(GL3 gl) {

        pmvMatrix.getmMatrix().pushMatrix();

        //Gleiche berechnung wie bei der Sonne nur negativ für die Position auf der anderen Halbkugel
        pmvMatrix.getmMatrix().translate(10, -sun.getCosSun(), -sun.getSinSun());

        Osphere2.setTexture(TextureManager.list.get(3), 1f);
        Osphere2.setMaterial(MaterialManager.Mlist.get("sonne"));
        Osphere2.render(GL_TRIANGLE_STRIP);
        pmvMatrix.getmMatrix().popMatrix();
    }


    /**
     * Erstellt eine Reihe aus Bäumen
     * @param baumA Anfangsbaum aus dem Array der Bäume
     * @param baumB Endbaum aus dem Array der Bäume
     * @param posX
     * @param posZ
     * @param abstand
     */
    private void baumReihe(int baumA, int baumB, int posX, int posZ, int abstand){

        for(int i = baumA; i <= baumB; i++){
            tree[i].translate(posX+= abstand, 0.5f, posZ);
        }
    }


    /**
     * Erstellt ein Kreis aus Bäumen
     * @param posX X-Position in der Welt
     * @param posY Y-Position in der Welt
     * @param posZ Z-Position in der Welt
     * @param baumA Anfangsbaum aus dem Array der Bäume
     * @param baumB Endbaum aus dem Array der Bäume
     * @param radius Radius des Kreises
     * @param abstand Abstand der Bäume zueinander
     */
    private void baumKreis(float posX, float posY, float posZ, int baumA, int baumB, float radius, float abstand){

        float add = 0.0f;
        float rot = 0.0f;
        for(int i = baumA; i < baumB; i++) {
            tree[i].rotate(rot -= 2.0f);
            tree[i].translate(posX + (radius * (float) Math.cos(add += abstand)), posY, posZ + (radius * (float) Math.sin(add)));
        }
    }


    /**
     * Erstellt ein Kreis aus Menschen
     * @param posX X-Position in der Welt
     * @param posY Y-Position in der Welt
     * @param posZ Z-Position in der Welt
     * @param avatarA Anfangsavatar aus dem Array der Avatare
     * @param avatarB Endavatar aus dem Array der Avatare
     * @param radius Radius des Kreises
     * @param abstand Abstand der Avatare zueinander
     */
    private void menschenKreis(float posX, float posY, float posZ, int avatarA, int avatarB, float radius, float abstand){

        float add = 0.0f;
        float rot = 0.0f;
        for(int i = avatarA; i < avatarB; i++) {
            avatar[i].translate(posX + (radius * (float) Math.cos(add += abstand)), posY, posZ + (radius * (float) Math.sin(add)));
            avatar[i].rotate(rot -= 15.0f);
        }
    }


    /**
     * Berechnet und gibt die FPS in der Konsole aus
     */
    private long firstFrame, currentFrame;
    private int frames, fps;
    private void fps(){

        frames++;
        currentFrame = System.currentTimeMillis();
        if(currentFrame > firstFrame + 1000){
            firstFrame = currentFrame;
            fps = frames;
            frames = 0;
        }
        System.out.println(fps + " fps");
//        System.out.println(frames + " frames");
    }
}
