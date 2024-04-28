package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Laterne {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scale;

    private Object kurzeStange;
    private Object langeStange;
    private Object kerze;
    private Object laterne;

    public Laterne() {}

    public Laterne(float posx, float posy, float posz, float rotDegree) {

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

        kurzeStange = new Object(buffer[0]);
        langeStange = new Object(buffer[1]);
        kerze = new Object(buffer[2]);
        laterne = new Object(buffer[3]);

    }


    public void scale(float scale){

        this.scale = scale;
    }

    public void set(float x, float y, float z, float r){

        this.translate(x, y, z);
        this.rotate(r);
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

        kurzeStange.setTexture(TextureManager.list.get(1), 1.0f);
        kurzeStange.setMaterial(ModelFromFile.list.get("laterne0").getMaterial());
        kurzeStange.render(GL_TRIANGLES);

        langeStange.setTexture(TextureManager.list.get(3), 1.0f);
        langeStange.setMaterial(ModelFromFile.list.get("laterne1").getMaterial());
        langeStange.render(GL_TRIANGLES);

        kerze.setTexture(TextureManager.list.get(3), 1.0f);
        kerze.setMaterial(ModelFromFile.list.get("laterne2").getMaterial());
        kerze.render(GL_TRIANGLES);

        laterne.setTexture(TextureManager.list.get(1), 1.0f);
        laterne.setMaterial(ModelFromFile.list.get("laterne3").getMaterial());
        laterne.render(GL_TRIANGLES);
    }
}
