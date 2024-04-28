package application.material;

import com.jogamp.opengl.GL3;

import java.util.ArrayList;
import java.util.HashMap;


public class MaterialManager {

    public static ArrayList<MaterialManager> list = new ArrayList<>();      //Arraylist, um mit Index darauf zuzugreifen
    public static HashMap<String,MaterialManager> Mlist = new HashMap<>();  //HashMap, um mit Namen darauf zuzugreifen

    private String name;

    private float[] emission;
    private float[] ambient;
    private float[] diffuse;
    private float[] specular;
    private float shininess;

    public MaterialManager() {}

    /**
     * Neues Material anlegen. Erweitert mit Attribut name, und speichern in Listen
     */
    public MaterialManager(float[] emission, float[] ambient, float[] diffuse, float[] specular, float shininess, String name) {
        this.emission = emission;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;

        this.name = name;
        list.add(this);
        Mlist.put(name, this);
    }

    /**
     * updatet das Material
     * @param gl
     */
    public void updateMaterial(GL3 gl){

        gl.glUniform4fv(7, 1, this.getEmission(), 0);
        gl.glUniform4fv(8, 1, this.getAmbient(), 0);
        gl.glUniform4fv(9, 1, this.getDiffuse(), 0);
        gl.glUniform4fv(10, 1, this.getSpecular(), 0);
        gl.glUniform1f(11, this.getShininess());
    }

    /**
     * Setzt das default Material, wenn kein Material angegeben ist.
     * @param gl
     */
    public static void setDefaultMaterial(GL3 gl) {

        float[] matEmission = {0.1f, 0.1f, 0.1f, 1.0f};
        float[] matAmbient = {0.3f, 0.3f, 0.3f, 1.0f};
        float[] matDiffuse = {0.5f, 0.5f, 0.5f, 1.0f};
        float[] matSpecular = {0.0f, 0.0f, 0.0f, 0.1f};
        float matShininess = 2.0f;

        gl.glUniform4fv(7, 1, matEmission, 0);
        gl.glUniform4fv(8, 1, matAmbient, 0);
        gl.glUniform4fv(9, 1, matDiffuse, 0);
        gl.glUniform4fv(10, 1, matSpecular, 0);
        gl.glUniform1f(11, matShininess);
    }

    public float[] getEmission() {
        return emission;
    }
    public float[] getAmbient() {
        return ambient;
    }
    public void setAmbient(float[] ambient) {
        this.ambient = ambient;
    }
    public float[] getDiffuse() {
        return diffuse;
    }
    public float[] getSpecular() {
        return specular;
    }
    public void setSpecular(float[] specular) {
        this.specular = specular;
    }
    public float getShininess() {
        return shininess;
    }
}
