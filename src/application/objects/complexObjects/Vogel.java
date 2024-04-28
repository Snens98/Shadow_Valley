package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Vogel {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scale;

    private Object vogel;

    public Vogel() {}

    public Vogel(float posx, float posy, float posz, float rotDegree) {

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
        vogel = new Object(buffer[0]);
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
     * Flugbahn für den Vogel definieren.
     * rH: Horizontaler radius
     * rV: Vertikaler Radius
     * speed: Geschwindigkeit des Vogels
     */
    private float add = 0.0f;
    private float rot = 0.0f;
    public void flugbahn(float rH, float rV, float speed){

        this.translate(rH*(float)Math.cos(add+=speed), 25, rV*(float)Math.sin(add));
        this.rotate(rot-=speed);
    }


    private void create() {

        vogel.setTexture(TextureManager.list.get(3), 15.0f);
        vogel.setMaterial(ModelFromFile.list.get("vogel").getMaterial());
        vogel.render(GL_TRIANGLES);
    }
}
