package application.objects.complexObjects;

import application.material.Material;
import application.objLoader.ModelFromFile;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;

import static com.jogamp.opengl.GL.GL_TRIANGLES;

public class Haus2 {

    private application.PMVMatrix pmvMatrix;

    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;

    private float rotDegree = 0;

    private float scale;

    private int mat = 0;

    private Object dach;
    private Object wand;
    private Object treppe;

    public Haus2() {}

    public Haus2(float posx, float posy, float posz, float rotDegree) {

        this.posX = posx;
        this.posY = posy;
        this.posZ = posz;
        this.rotDegree = rotDegree;
    }

    public void setMaterial(int i){

        this.mat = i;
        dach.setMaterial(Material.Haus2Mat.get(i));
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

        dach = new Object(buffer[0]);
        wand = new Object(buffer[1]);
        treppe = new Object(buffer[2]);
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


    public void set(float scale, float rotate, float posx, float posy, float posz){

        this.scale(scale);
        this.rotate(rotate);
        this.translate(posx, posy, posz);
    }

    private void create() {

        dach.setTexture(TextureManager.list.get(1), 20.0f);
        dach.setMaterial(Material.Haus2Mat.get(mat));
        dach.render(GL_TRIANGLES);

        wand.setTexture(TextureManager.list.get(1), 20.0f);
        wand.setMaterial(ModelFromFile.list.get("haus2Wand").getMaterial());
        wand.render(GL_TRIANGLES);

        treppe.setTexture(TextureManager.list.get(1), 20.0f);
        treppe.setMaterial(ModelFromFile.list.get("haus2Treppe").getMaterial());
        treppe.render(GL_TRIANGLES);

    }
}
