package application.objects.complexObjects;

import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Lagerfeuer {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scale;

    private Object stein;
    private Object holz;

    public Lagerfeuer() {}

    public Lagerfeuer(float posx, float posy, float posz, float rotDegree) {

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

        stein = new Object(buffer[0]);
        holz = new Object(buffer[1]);
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

        stein.setTexture(TextureManager.list.get(3), 20.0f);
        stein.setMaterial(ModelFromFile.list.get("lagerfeuer0").getMaterial());
        stein.render(GL_TRIANGLES);

        holz.setTexture(TextureManager.list.get(3), 20.0f);
        holz.setMaterial(ModelFromFile.list.get("lagerfeuer1").getMaterial());
        holz.render(GL_TRIANGLES);

    }
}
