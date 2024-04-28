package application.objects.complexObjects;

import application.material.MaterialManager;
import application.PMVMatrix;
import application.objects.Object;
import application.objects.ObjectBuffer;
import application.texture.TextureManager;
import com.jogamp.opengl.GL3;

public class CubePyramide {

    final private int cubes;
    private Object[] obj;
    private PMVMatrix pmvMatrix;

    private float posX, posY, posZ = 0;

    private  float rotDegree = 0, scale = 1;

    private float rotX = 0, rotY = 0, rotZ = 0;

    private int textureSlot = -1;
    private int materialSlot = -1;
    private float repeatTexture = 0.5f;


    public CubePyramide(int grundflaeche){

        this.cubes = grundflaeche;
    }

    public void setMaterial(int slot){

        this.materialSlot = slot;
    }

    public void setTexture(int slot, float repeatTexture){

        this.textureSlot = slot;
        this.repeatTexture = repeatTexture;
    }

    public void translate(float x, float y, float z){

        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public void scale(float scale){

        this.scale = scale;
    }

    public void rotate(float degree, float x, float y, float z){

        this.rotDegree = degree;
        this.rotX = x;
        this.rotY = y;
        this.rotZ = z;
    }

    public void init(ObjectBuffer... buffer) {

        obj = new Object[cubes];
        this.pmvMatrix = buffer[0].getPMVMatrix();

        // Cubes for Pyramide
        for(int i = 0; i < obj.length; i++) {
            obj[i] = new Object(buffer[0]);
        }
    }



    public void draw(GL3 gl){

        pmvMatrix.getmMatrix().pushMatrix();
        pmvMatrix.getmMatrix().translate(this.posX, this.posY, this.posZ);
        pmvMatrix.getmMatrix().translate(0, 0, 0);
        pmvMatrix.getmMatrix().rotate(this.rotDegree, this.rotX, this.rotY, this.rotZ);
        pmvMatrix.getmMatrix().scale(this.scale, this.scale, this.scale);
        create(gl);
        pmvMatrix.getmMatrix().popMatrix();
    }


    /**
     * Pyramide im Kreis bewegen
     */
    float i = 0.0f;
    public void kreisbahn(float posX, float posY, float posZ, float rH, float rV, float speed){

        this.translate(posX + (rH*(float)Math.cos(i+=speed)), posY, posZ + (rV*(float)Math.sin(i)));
        this.rotate(i, 0, 1, 0);
    }

    /**
     * Erstellt eine Pyramine aus Würfeln mit Material und Textur
     * @param gl
     */
    private void create(GL3 gl) {

        for(int l = 0; l < obj.length; l++) {

            for (int i = 0; i < obj.length - l*2; i++) {

                for (int j = 0; j < obj.length - l*2; j++) {

                    this.pmvMatrix.getmMatrix().pushMatrix();

                    this.pmvMatrix.getmMatrix().translate(((i * 1.0f) + l*1.0f), (l*1.0f + 1.0f), ((j * 1.0f) + l*1.0f));
                    pmvMatrix.getmMatrix().translate(-(cubes/2), 0, -(cubes/2));

                    obj[i].setMaterial(MaterialManager.list.get(3));
                    obj[i].setTexture(TextureManager.list.get(1), 0.5f);
                    obj[i].render(gl.GL_TRIANGLE_STRIP);

                    this.pmvMatrix.getmMatrix().popMatrix();
                }
            }
        }
    }
}
