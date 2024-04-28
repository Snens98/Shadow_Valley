package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Brunnen {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scale;

    private Object eimer;
    private Object brunnendach;
    private Object brunnen;
    private Object wasser;


    public Brunnen() {}

    public Brunnen(float posx, float posy, float posz, float rotDegree) {

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

        eimer = new Object(buffer[0]);
        brunnen = new Object(buffer[1]);
        brunnendach = new Object(buffer[2]);
        wasser = new Object(buffer[3]);

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


    private void create() {

        brunnen.setTexture(TextureManager.list.get(2), 20.0f);
        brunnen.setMaterial(ModelFromFile.list.get("brunnen").getMaterial());
        brunnen.render(GL_TRIANGLES);

        brunnendach.setTexture(TextureManager.list.get(1), 20.0f);
        brunnendach.setMaterial(ModelFromFile.list.get("brunnendach").getMaterial());
        brunnendach.render(GL_TRIANGLES);

        eimer.setTexture(TextureManager.list.get(0), 20.0f);
        eimer.setMaterial(ModelFromFile.list.get("eimer").getMaterial());
        eimer.render(GL_TRIANGLES);

        wasser.setTexture(TextureManager.list.get(3), 20.0f);
        wasser.setMaterial(ModelFromFile.list.get("wasser").getMaterial());
        wasser.render(GL_TRIANGLES);
    }
}


