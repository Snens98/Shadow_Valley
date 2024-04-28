package application.objLoader;

import application.material.MaterialManager;
import de.hshl.obj.loader.OBJLoader;
import de.hshl.obj.loader.Resource;
import de.hshl.obj.loader.objects.Surface;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class ModelFromFile {

    // Alle Objekte werden in einer HashMap gespeichert
    public static HashMap<String, ModelFromFile> list = new HashMap<>();

    private MaterialManager materialManager;    // MaterialManager Objekt
    private int[] indices;                      // Abspeichern der indices der Modelle
    private float[] verticies;                  // Abspeichern der Verticies der Modelle

    private OBJLoader obj = new OBJLoader();    // OBJLoader Objekt aus der OBJLoader Bibliothek


    /**
     * Lädt die Objekte zum initialisieren
     * subObject: angabe über die zusammensetzung von Objekten aus mehreren unterobjekten.
     */
    public static void init(){

        new ModelFromFile(ModelFile.quader, 0, "quader");

        new ModelFromFile(ModelFile.baum, 1, "baum");
        new ModelFromFile(ModelFile.baum, 0, "baum2");

        new ModelFromFile(ModelFile.haus1, 0, "haus1Dach");
        new ModelFromFile(ModelFile.haus1, 1, "haus1Wand");

        new ModelFromFile(ModelFile.haus2, 0, "haus2Dach");
        new ModelFromFile(ModelFile.haus2, 1, "haus2Treppe");
        new ModelFromFile(ModelFile.haus2, 2, "haus2Wand");

        for(int i = 0; i < 7; i++) {
            new ModelFromFile(ModelFile.avatar, i, "avatar"+i);
        }

        new ModelFromFile(ModelFile.lagerfeuer, 0, "lagerfeuer0");
        new ModelFromFile(ModelFile.lagerfeuer, 1, "lagerfeuer1");

        new ModelFromFile(ModelFile.flamme, 0, "flamme0");
        new ModelFromFile(ModelFile.flamme, 1, "flamme1");
        new ModelFromFile(ModelFile.flamme, 2, "flamme2");
        new ModelFromFile(ModelFile.flamme, 3, "flamme3");
        new ModelFromFile(ModelFile.flamme, 4, "flamme4");

        new ModelFromFile(ModelFile.weg, 0, "weg");

        new ModelFromFile(ModelFile.bank, 0, "bank");

        new ModelFromFile(ModelFile.vogel, 0, "vogel");

        new ModelFromFile(ModelFile.brunnen, 0, "eimer");
        new ModelFromFile(ModelFile.brunnen, 1, "brunnendach");
        new ModelFromFile(ModelFile.brunnen, 2, "brunnen");
        new ModelFromFile(ModelFile.brunnen, 3, "wasser");

        new ModelFromFile(ModelFile.berg, 0, "berg");

        new ModelFromFile(ModelFile.laterne, 0, "laterne0");
        new ModelFromFile(ModelFile.laterne, 1, "laterne1");
        new ModelFromFile(ModelFile.laterne, 2, "laterne2");
        new ModelFromFile(ModelFile.laterne, 3, "laterne3");
    }


    /**
     * Hier werden die Verticies, Indices und Materialien der Objekte mit dem Objectloader (OBJLoader-3.0.0) gelsen.
     * @param file Speicherort
     * @param subObject Teilobjekte des Objekts z.B für mehrere verschiedene Materialien und Texturen
     * @param name Name
     */
    public ModelFromFile(Path file, int subObject, String name) {

        obj.setGenerateIndexedMeshes(true);

        try {

            Surface surface = obj.setLoadTextureCoordinates(true).setLoadNormals(true)
                    .loadSurfaces(Resource.file(file)).get(subObject);

            setSurfaceMaterial(surface);

            indices = surface.getMesh().getIndices();
            verticies = surface.getMesh().getVertices();

            list.put(name, this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.materialManager = materialManager;
        this.indices = indices;
        this.verticies = verticies;
    }

    /**
     * Material des Objekts setzen.
     * @param surface
     * @return
     */
    private MaterialManager setSurfaceMaterial(Surface surface){

        float[] matDiffuse = surface.getMaterial().getDiffuseColor().toFloatArray();
        float[] matAmbient = surface.getMaterial().getAmbientColor().toFloatArray();
        float[] matSpecular = surface.getMaterial().getSpecularColor().toFloatArray();
        float[] matEmission = surface.getMaterial().getEmissionColor().toFloatArray();
        float matShininess = surface.getMaterial().getSharpness().floatValue();

       return materialManager
               = new MaterialManager(matEmission, matAmbient, matDiffuse, matSpecular, matShininess, "name");
    }


    public MaterialManager getMaterial() {
        return materialManager;
    }

    public void setMaterial(MaterialManager materialManager) {
        this.materialManager = materialManager;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getVerticies() {
        return verticies;
    }
}
