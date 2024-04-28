package application.texture;

import com.jogamp.opengl.GL3;
import static com.jogamp.opengl.GL.GL_TEXTURE0;

public class Texture {

    /**
     *
     * @param gl
     */
    public static void init(GL3 gl) {

        gl.glActiveTexture(GL_TEXTURE0);

        new TextureManager(gl,"gras.jpg");
        new TextureManager(gl, "holz.jpg");
        new TextureManager(gl, "gras2.jpg");
        new TextureManager(gl, "weis.jpg");

    }
}
