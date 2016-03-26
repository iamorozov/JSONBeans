package beans;

/**
 * Created by Morozov Ivan on 22.03.2016.
 */
public class DottetDolphin extends Dolphin {
    private String body;

    public DottetDolphin(){
        body = "dotted";
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
