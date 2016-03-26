package beans;

/**
 * Created by Morozov Ivan on 07.03.2016.
 */
public class Dolphin {

    private static int num = 0;

    public Dolphin(){
        setNumber(num++);
    }

    private String name;

    private int number;

    private Dolphinarium dolphinarium;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Dolphinarium getDolphinarium() {
        return dolphinarium;
    }

    public void setDolphinarium(Dolphinarium dolphinarium) {
        this.dolphinarium = dolphinarium;
    }
}
