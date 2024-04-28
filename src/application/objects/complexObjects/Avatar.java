package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Avatar {

    private application.PMVMatrix pmvMatrix;
    private float posX = 0, posY = 0, posZ = 0;
    private float rotDegree, scale;
    private Object koerper, oberteil, hose, schuhe, augelinks, augerechts, haare;

    public Avatar() {}

    /**
     * Konstruktor, neuen Avatar mit Werten erstellen
     */
    public Avatar(float posx, float posy, float posz, float rotDegree) {

        this.posX = posx;
        this.posY = posy;
        this.posZ = posz;
        this.rotDegree = rotDegree;
    }

    /**
     * Avatar in der Welt verschieben
     */
    public void translate(float x, float y, float z) {

        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    /**
     * Avatar rotieren
     */
    public void rotate(float degree) {

        this.rotDegree = degree;
    }

    /**
     * Avatar initialisieren
     */
    public void init(ObjectBuffer... buffer) {

        this.pmvMatrix = buffer[0].getPMVMatrix();

        koerper = new Object(buffer[0]);
        oberteil = new Object(buffer[1]);
        hose = new Object(buffer[2]);
        schuhe = new Object(buffer[3]);
        augelinks = new Object(buffer[4]);
        augerechts = new Object(buffer[5]);
        haare = new Object(buffer[6]);
    }

    /**
     * Avatar skalieren
     */
    public void scale(float scale) {

        this.scale = scale;
    }

    /**
     * Avatar zeichnen
     */
    public void draw() {

        pmvMatrix.getmMatrix().pushMatrix();
        pmvMatrix.getmMatrix().translate(this.posX, this.posY, this.posZ);
        pmvMatrix.getmMatrix().translate(0, 0, 0);
        pmvMatrix.getmMatrix().rotate(this.rotDegree, 0, 1, 0);
        pmvMatrix.getmMatrix().scale(this.scale, this.scale, this.scale);
        create();
        pmvMatrix.getmMatrix().popMatrix();
    }


    /**
     * Position und rotation setzen
     */
    public void set(float x, float y, float z, float r) {

        this.translate(x, y, z);
        this.rotate(r);
    }

    /**
     * Teilobjekte vom Avatar erstellen mit Materialien und Texturen
     */
    private void create() {

        koerper.setTexture(TextureManager.list.get(3), 1.0f);
        koerper.setMaterial(ModelFromFile.list.get("avatar0").getMaterial());
        koerper.render(GL_TRIANGLES);

        oberteil.setTexture(TextureManager.list.get(3), 1.0f);
        oberteil.setMaterial(ModelFromFile.list.get("avatar1").getMaterial());
        oberteil.render(GL_TRIANGLES);

        hose.setTexture(TextureManager.list.get(3), 1.0f);
        hose.setMaterial(ModelFromFile.list.get("avatar2").getMaterial());
        hose.render(GL_TRIANGLES);

        schuhe.setTexture(TextureManager.list.get(3), 1.0f);
        schuhe.setMaterial(ModelFromFile.list.get("avatar3").getMaterial());
        schuhe.render(GL_TRIANGLES);

        augelinks.setTexture(TextureManager.list.get(3), 1.0f);
        augelinks.setMaterial(ModelFromFile.list.get("avatar4").getMaterial());
        augelinks.render(GL_TRIANGLES);

        augerechts.setTexture(TextureManager.list.get(3), 1.0f);
        augerechts.setMaterial(ModelFromFile.list.get("avatar5").getMaterial());
        augerechts.render(GL_TRIANGLES);

        haare.setTexture(TextureManager.list.get(3), 1.0f);
        haare.setMaterial(ModelFromFile.list.get("avatar6").getMaterial());
        haare.render(GL_TRIANGLES);
    }

    /**
     * Avatar im Kreis bewegen
     */
    private float add = 0.0f;
    private float rot = 0.0f;

    public void kreisBewegung(float posx, float posy, float posz, float rH, float rV, float speed) {

        this.translate(posx + (rH * (float) Math.cos(add += speed)), posy, posz + (rV * (float) Math.sin(add)));
        this.rotate(rot -= speed);
    }



    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }
}

