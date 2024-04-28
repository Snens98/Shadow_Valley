package application.objects;

/**
 * Um verschiedene VAOs zu erstellen (Für Komplexe Objekte z.B Mensch oder primitive Objekte z.B Sonne)
 */
public class VAO {

    private int[] num;
    private int[] count;
    private int[] size;
    private int[] offset;

    public VAO(){}

    public int[] getNum() {
        return num;
    }

    public void setNum(int[] num) {
        this.num = num;
    }

    public int[] getCount() {
        return count;
    }

    public void setCount(int[] count) {
        this.count = count;
    }

    public int[] getSize() {
        return size;
    }

    public void setSize(int[] size) {
        this.size = size;
    }

    public int[] getOffset() {
        return offset;
    }

    public void setOffset(int[] offset) {
        this.offset = offset;
    }
}
