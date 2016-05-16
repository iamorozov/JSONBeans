package beans;

public class Bean {

    private static int num = 0;

    private int number;

    public Bean() {
        number = num++;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
