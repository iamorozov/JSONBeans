package beans;

/**
 * Created by Morozov Ivan on 22.03.2016.
 */
public class StripedDolphin extends Dolphin {
    private String tail;

    public StripedDolphin(){
        tail = "Striped";
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
