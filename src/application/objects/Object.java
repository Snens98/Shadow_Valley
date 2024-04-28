package application.objects;

import application.material.MaterialManager;
import application.texture.TextureManager;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import java.util.ArrayList;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;

/**
 * Erstellt Objekte
 */
public class Object {

    public static ArrayList<Object> list = new ArrayList<>(); // Liste aller Objekte

    final private GL3 gl;
    final private ObjectBuffer objBuffer;       //ObjectBuffer zum übertragen der Verticies der Objekte zur GK
    private MaterialManager materialManager;

    /**
     * Neue Objekte anlegen
     * @param objBuffer
     */
    public Object(ObjectBuffer objBuffer){

        this.gl = objBuffer.getGL3();
        this.objBuffer = objBuffer;
    }

    /**
     * Rendert das Objekt
     * @param drawmode welcher zeichenmodus verwendet werden soll
     */
    public void render(int drawmode){

        objBuffer.setActive(); // Benutzt den zum Objekt zugehörigen buffer

        gl.glUseProgram(objBuffer.getShaderProgram().getShaderProgramID()); // Benutzt das zum Objekt zugehöriges Shader-Programm

        // Lädt die ModelMatrix, ViewMatrix und ProjectionMatrix zum festgelegten Shader-programm über eine Uniform Variable
        gl.glUniformMatrix4fv(0, 1, false, objBuffer.getPMVMatrix().getmMatrix().get(objBuffer.getPMVMatrix().getMatrixBuffer()));
        gl.glUniformMatrix4fv(1, 1, false, objBuffer.getPMVMatrix().getvMatrix().get(objBuffer.getPMVMatrix().getMatrixBuffer()));
        gl.glUniformMatrix4fv(2, 1, false, objBuffer.getPMVMatrix().getpMatrix().get(objBuffer.getPMVMatrix().getMatrixBuffer()));

        // Wenn kein Material festgelegt ist, wird das default Material benutzt
        if(materialManager == null){
            MaterialManager.setDefaultMaterial(gl);
        }

        //Zeichnet das Element
        gl.glDrawElements(drawmode, objBuffer.getNumOfIndices(), GL.GL_UNSIGNED_INT, 0);

        //Setzt mit 0 keine Textur bzw. Textur wird für ein anderes Objekt frei.
        gl.glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Setzt das Material für das Objekt
     * @param materialManager
     */
    public void setMaterial(MaterialManager materialManager) {

        this.materialManager = materialManager;

        if (materialManager != null) { // Wenn es ein Material gibt, was geupdatet werden kann
            materialManager.updateMaterial(gl);
        }
    }

    /**
     * setzt die Tetxur für das Objekt
     * @param texture
     * @param repeat
     */
    public void setTexture(TextureManager texture, float repeat){

        texture.enableRepeatTexture(objBuffer); // Aktiviert die Texturwiederholung auf Objekten
        gl.glBindTexture(GL_TEXTURE_2D, texture.getSlot()+1); // Setzt eine spezifische Textur für das Objekt
        gl.glUniform1f(texture.getTextureRepeat(), repeat); // Gibt den Wiederholungswert der Textur dem Shader-programm weiter
    }
}
