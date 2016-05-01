package beans;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dolphin dolphin = (Dolphin) o;

        if (number != dolphin.number) return false;
        if (name != null ? !name.equals(dolphin.name) : dolphin.name != null) return false;
        return dolphinarium != null ? dolphinarium.equals(dolphin.dolphinarium) : dolphin.dolphinarium == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (dolphinarium != null ? dolphinarium.hashCode() : 0);
        return result;
    }
}
