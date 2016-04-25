package beans;


public class TestBean {


    //TODO: test class property

    //Numerical primitives
    private byte b;
    private short sh;
    private int i;
    private long l;
    private float f;
    private double d;

    //Other primitives

    private String str;
    private char ch;
    private boolean bool;
    private Class someClass;

    private Dolphin dolphin;

    private Integer[] intArray;

    public Integer[] getIntArray() {
        return intArray;
    }

    public void setIntArray(Integer[] intArray) {
        this.intArray = intArray;
    }

    public Integer getIntArray(int index)throws ArrayIndexOutOfBoundsException{
        return intArray[index];
    }

    public void setIntArray(int index, int value) throws ArrayIndexOutOfBoundsException{
        intArray[index] = value;
    }

    public Dolphin getDolphin() {
        return dolphin;
    }

    public void setDolphin(Dolphin dolphin) {
        this.dolphin = dolphin;
    }

    public Class getSomeClass() {
        return someClass;
    }

    public void setSomeClass(Class someClass) {
        this.someClass = someClass;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public byte getB() {
        return b;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public short getSh() {
        return sh;
    }

    public void setSh(short sh) {
        this.sh = sh;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}
