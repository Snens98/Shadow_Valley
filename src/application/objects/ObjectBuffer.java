package application.objects;

import application.PMVMatrix;
import application.shader.ShaderProgram;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class ObjectBuffer {

    public static ArrayList<ObjectBuffer> list = new ArrayList<>(); // Liste der ObjectBuffer zum löschen beim
                                                                    // schließen des Programmes

    private int numOfObj = 1;                   //Jeder Buffer soll 1 Objekt buffern
    private int[] vaoName = new int[numOfObj];  //VAO erstellen

    private int[] vboName = {};                 // VBO erstellen
    private int[] iboName = {};                 // IBO erstellen

    private GL3 gl;
    private int numOfIndices;                   // Anzahl der Indecies des Objekts
    private PMVMatrix pmvMatrix;
    private ShaderProgram shaderProgram;
    private float[] vertices;                   // Alle Vertices des Objekts
    private int[] numOfVertices;                // Anzahl der Vertices des Objekts
    private VAO vao;                            // Zum erstellen von VAOs


    /**
     * Konstruktor zum anlegen eines Buffer Object
     */
    public ObjectBuffer(GL3 gl, PMVMatrix pmvMatrix, VAO vao, ShaderProgram shaderProgram,
                        int[] numOfVertices, float[] vertices, int numOfIndices){

        this.numOfVertices = numOfVertices;
        this.vertices = vertices;
        this.numOfIndices = numOfIndices;
        this.pmvMatrix = pmvMatrix;
        this.shaderProgram = shaderProgram;
        this.gl = gl;
        this.vao = vao;

        genAndBindBuffer(); // Buffer erstellen und setzen
        setLayout();        // Layout setzen

        list.add(this);     // Zur Liste hinzufügen
    }

    /**
     * VBO und IBO werden erstellt und gebunden.
     */
    private void genAndBindBuffer(){

        vboName = new int[1];
        gl.glGenBuffers(1, vboName, 0);

        iboName = new int[1];
        gl.glGenBuffers(1, iboName, 0);

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[0]);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.length * Float.BYTES,
                FloatBuffer.wrap(vertices), GL.GL_STATIC_DRAW);

        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[0]);
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, numOfVertices.length * Float.BYTES,
                IntBuffer.wrap(numOfVertices), GL.GL_STATIC_DRAW);
    }


    /**
     * Variables Layout zum einlesen der Verticies, Texturkoordinaten, NormalKoordinaten, Farben.
     */
    private void setLayout(){

        gl.glGenVertexArrays(numOfObj, vaoName, 0);
        gl.glBindVertexArray(vaoName[0]);

        //Position
        gl.glEnableVertexAttribArray(0);
        gl.glVertexAttribPointer(0, vao.getCount()[0], GL.GL_FLOAT, false, vao.getSize()[0], vao.getOffset()[0]);

        //Texture coordinates
        gl.glEnableVertexAttribArray(1);
        gl.glVertexAttribPointer(1, vao.getCount()[1], GL.GL_FLOAT, false, vao.getSize()[1], vao.getOffset()[1]);

        //Normals (color)
        gl.glEnableVertexAttribArray(2);
        gl.glVertexAttribPointer(2, vao.getCount()[2], GL.GL_FLOAT, false, vao.getSize()[2], vao.getOffset()[2]);
    }

    /**
     * Bindet VAO und IBO mit dem Objekt
     */
    public void setActive(){

        gl.glBindVertexArray(vaoName[0]);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[0]);
    }

    /**
     * Unbindet/Löscht VAO und VBO
     */
    public void setDeactivate(){

        gl.glDeleteVertexArrays(1, vaoName,0);
        gl.glDeleteBuffers(1, vboName, 0);
    }


    public GL3 getGL3() { return gl; }

    public int getNumOfIndices() {
        return numOfIndices;
    }

    public PMVMatrix getPMVMatrix() {
        return pmvMatrix;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}