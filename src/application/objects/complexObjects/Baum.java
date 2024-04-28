package application.objects.complexObjects;

import application.material.Material;
import application.material.MaterialManager;
import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Baum {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private int mat = 0;    //Material

    private float rotDegree = 0;

    private float scale;

    private Object stamm;
    private Object blatt;

    public Baum() {}

    /**
     * Konstruktor
     * @param posx
     * @param posy
     * @param posz
     * @param rotDegree
     */
    public Baum(float posx, float posy, float posz, float rotDegree) {

        this.posX = posx;
        this.posY = posy;
        this.posZ = posz;
        this.rotDegree = rotDegree;
    }


    /**
     * Positionierung in der 3D-Welt
     *
     * @param x X-Positon in der 3D-Welt
     * @param y Y-Positon in der 3D-Welt
     * @param z z-Positon in der 3D-Welt
     */
    public void translate(float x, float y, float z) {

        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    /**
     * Rotationswert des Baums.
     * Ausgehend vom Punkt 0, 0, 0 der 3D-Welt
     *
     * @param degree Rotationswert in Grad (0 - 360)
     */
    public void rotate(float degree) {

        this.rotDegree = degree;
    }

    /**
     * Baum initialisieren
     * @param buffer Zusammengesetzte Objekte
     */
    public void init(ObjectBuffer... buffer) {

        this.pmvMatrix = buffer[0].getPMVMatrix();

        stamm = new Object(buffer[0]);
        blatt = new Object(buffer[1]);
    }


    /**
     * Baum vergrößern / verkleinern
     * @param scale Beispiel: 1.0 = Normalgröße | 2.0 Größe verdoppelt | 0.5 Größe halbiert
     */
    public void scale(float scale){

        this.scale = scale;
    }

    /**
     * Material setzen
     * @param i
     */
    public void setMaterial(int i){

        this.mat = i;
        stamm.setMaterial(Material.treeMat.get(i));
    }

    /**
     * Zeichnet den Baum in der 3D-Welt
     */
    public void draw() {

        pmvMatrix.getmMatrix().pushMatrix(); // Nur die aktuelle Matrix bearbeiten, um nur den Baum anzupassen und nicht die anderen Objekte
        pmvMatrix.getmMatrix().translate(this.posX, this.posY, this.posZ); // Baum in Welt verschieben
        pmvMatrix.getmMatrix().translate(0, 0, 0); // Baum wieder zu 0,0,0 der Welt verschieben, um anschließend normal zu rotieren und zu skalieren.
        pmvMatrix.getmMatrix().rotate(this.rotDegree, 0, 1, 0); // Baum um die Y-Achse Rotieren
        pmvMatrix.getmMatrix().scale(this.scale, this.scale, this.scale); // Baum an allen Achsen Skalieren
        create(); // Methode zum Material, Textur setzen und zum zeichnen des Baums mit der veränderten Matrix
        pmvMatrix.getmMatrix().popMatrix(); // Gibt die Matrix wieder frei
    }


    /**
     * Setzt die Textur, Material und rendert die Teilobjekte des Baums.
     */
    private void create() {

        stamm.setTexture(TextureManager.list.get(2), 1.0f);
        stamm.setMaterial(Material.treeMat.get(mat));
        stamm.render(GL_TRIANGLES);

        blatt.setTexture(TextureManager.list.get(3), 2.0f);
        blatt.setMaterial(ModelFromFile.list.get("baum").getMaterial());
        blatt.render(GL_TRIANGLES);

    }
}
