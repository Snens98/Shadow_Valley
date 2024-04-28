package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Flamme {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scale;

    private Object flamme0, flamme1, flamme2, flamme3, flamme4;

    public Flamme() {}

    public Flamme(float posx, float posy, float posz, float rotDegree) {

        this.posX = posx;
        this.posY = posy;
        this.posZ = posz;
        this.rotDegree = rotDegree;
    }


    public void translate(float x, float y, float z) {

        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void rotate(float degree) {

        this.rotDegree = degree;
    }

    public void init(ObjectBuffer... buffer) {

        this.pmvMatrix = buffer[0].getPMVMatrix();

        flamme0 = new Object(buffer[0]);
        flamme1 = new Object(buffer[1]);
        flamme2 = new Object(buffer[2]);
        flamme3 = new Object(buffer[3]);
        flamme4 = new Object(buffer[4]);
    }


    public void scale(float scale){

        this.scale = scale;
    }

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
     * Setzt eine zufällige Größe der Flamme
     */
    public void setRandomScale(float scale, float begin, float end){

        this.scale((float) (scale + Math.random() * (begin - end)));
    }


    private void create() {

        flamme0.setTexture(TextureManager.list.get(3), 1.0f);
        flamme0.setMaterial(ModelFromFile.list.get("flamme0").getMaterial());
        flamme0.render(GL_TRIANGLES);

        flamme1.setTexture(TextureManager.list.get(3), 1.0f);
        flamme1.setMaterial(ModelFromFile.list.get("flamme1").getMaterial());
        flamme1.render(GL_TRIANGLES);

        flamme2.setTexture(TextureManager.list.get(3), 1.0f);
        flamme2.setMaterial(ModelFromFile.list.get("flamme2").getMaterial());
        flamme2.render(GL_TRIANGLES);

        flamme3.setTexture(TextureManager.list.get(3), 1.0f);
        flamme3.setMaterial(ModelFromFile.list.get("flamme3").getMaterial());
        flamme3.render(GL_TRIANGLES);

        flamme4.setTexture(TextureManager.list.get(3), 1.0f);
        flamme4.setMaterial(ModelFromFile.list.get("flamme4").getMaterial());
        flamme4.render(GL_TRIANGLES);
    }
}
