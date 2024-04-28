package application.texture;

import application.objects.ObjectBuffer;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.Texture;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextureManager {

    public static ArrayList<TextureManager> list = new ArrayList<>(); // Liste aller Texturen
    private static int texturSlot = -1; // Anzahl Texturen
    private int slot;                   // Texturslot
    private GL3 gl;
    private Texture texture = null;     // Textur Objekt
    private int textureRepeat;          // Wie oft eine textur auf ein Objekt wiederholt wird

    /**
     * Neue Textur mit Konstruktor anlegen
     * @param gl
     * @param textureFileName Texturname
     */
    public TextureManager(GL3 gl, String textureFileName) {

        this.gl = gl;
        loadTextureFile(textureFileName);
        setTextureFilter();                 // Die Textur wird mit Filter bearbeitet

        texturSlot++;
        setTexturSlot(texturSlot);

        list.add(this);
    }


    /**
     * Textur wird aus dem Verzeichnis, Speicherort geladen
     * @param textureFileName Name der Textur (Dateiname)
     */
    private void loadTextureFile(String textureFileName){

        final String path = ".\\resources\\";

        try {
            File textureFile = new File(path+textureFileName);
            texture = TextureIO.newTexture(textureFile, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (texture != null)
            System.out.println("Texture loaded successfully from: " + path+textureFileName);
        else
            System.err.println("Error loading textue.");
    }

    /**
     * Hier werden Filter für die Textur angewendet
     */
    private void setTextureFilter(){

        texture.setTexParameteri(gl, GL3.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR_MIPMAP_LINEAR);
//      texture.setTexParameteri(gl, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);
        texture.setTexParameteri(gl, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        texture.setTexParameteri(gl, gl.GL_TEXTURE_WRAP_S, gl.GL_REPEAT);
        texture.setTexParameteri(gl, gl.GL_TEXTURE_WRAP_T, gl.GL_REPEAT);
        gl.glGenerateMipmap(gl.GL_TEXTURE_2D);
    }

    /**
     * Hier wird die Wiederholrate der Textur dem ObjectBuffer übergeben und zum Shader geladen
     * @param buffer
     */
    public void enableRepeatTexture(ObjectBuffer buffer){
        textureRepeat = gl.glGetUniformLocation(buffer.getShaderProgram().getShaderProgramID(), "texRepeat");
    }

    public void setTexturSlot(int slot){
        this.slot = slot;
    }

    public int getSlot(){
        return slot;
    }

    public int getTextureRepeat() {
        return textureRepeat;
    }
}
