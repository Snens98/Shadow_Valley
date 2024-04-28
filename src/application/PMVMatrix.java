package application;

import com.jogamp.common.nio.Buffers;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import java.nio.FloatBuffer;

/**
 * Ersetzt das PMV-Tool
 * Methoden aus der Joml-Bibliothek
 */
public class PMVMatrix {

    Matrix4fStack mMatrix = new Matrix4fStack(64);              // Model-Matrix (zum Objekte verschieben)
    Matrix4fStack vMatrix = new Matrix4fStack(16);              // View-Matrix (Kamera)
    Matrix4f mvMatrix =  new Matrix4f();                                // ModelView-Matrix (V * M)
    Matrix4f pMatrix = new Matrix4f();                                  // Projection-Matrix (Perspektivische Projektion)
    Matrix4f vpMatrix = new Matrix4f();                                 // View-Projection-Matrix (P * V - Kamera mit perspektivischer Projektion)
    Matrix4f mvpMatrix = new Matrix4f();                                // ModelViewProjection-Matrix  (P * V * M)
    FloatBuffer matrixBuffer = Buffers.newDirectFloatBuffer(16);      // Um die Matrizen in Shader zu laden

    public PMVMatrix(){}

    public Matrix4fStack getmMatrix() {
        return mMatrix;
    }

    public Matrix4fStack getvMatrix() {return vMatrix;}

    public Matrix4f getpMatrix() {
        return pMatrix;
    }

    public FloatBuffer getMatrixBuffer() {
        return matrixBuffer;
    }
}
