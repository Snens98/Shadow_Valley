package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Weg {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scaleX;
    private float scaleY;
    private float scaleZ;

    private Object weg;

    public Weg() {}

    public Weg(float posx, float posy, float posz, float rotDegree) {

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

        weg = new Object(buffer[0]);
    }


    public void scale(float scaleX, float scaleY, float scaleZ){

        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;

    }

    public void draw() {

        pmvMatrix.getmMatrix().pushMatrix();
        pmvMatrix.getmMatrix().translate(this.posX, this.posY, this.posZ);
        pmvMatrix.getmMatrix().translate(0, 0, 0);
        pmvMatrix.getmMatrix().rotate(this.rotDegree, 0, 1, 0);
        pmvMatrix.getmMatrix().scale(this.scaleX, this.scaleY, this.scaleZ);
        create();
        pmvMatrix.getmMatrix().popMatrix();
    }


    private void create() {

        weg.setTexture(TextureManager.list.get(3), 1.0f);
        weg.setMaterial(ModelFromFile.list.get("weg").getMaterial());
        weg.render(GL_TRIANGLES);
    }
}
