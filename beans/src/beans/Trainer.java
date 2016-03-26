package beans;

/**
 * Created by Morozov Ivan on 07.03.2016.
 */
public class Trainer {

    public Trainer(){}

    private String name;
    private Dolphin[] dolphins;


    public Dolphin[] getDolphins() {
        return dolphins;
    }

    public void setDolphins(Dolphin[] dolphins) {
        this.dolphins = dolphins;
    }

    public Dolphin getDolphins(int index)
            throws ArrayIndexOutOfBoundsException{
        return dolphins[index];
    }

    public void setDolphins(int index, Dolphin dolphin)
            throws ArrayIndexOutOfBoundsException{
        dolphins[index] = dolphin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
