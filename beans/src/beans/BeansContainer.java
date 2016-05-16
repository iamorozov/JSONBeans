package beans;

import javax.swing.*;
import java.awt.*;

public class BeansContainer extends JComponent {

    private int posX, posY;

    private Jar firstJar;
    private Jar secondJar;


    public Jar getFirstJar() {
        return firstJar;
    }

    public void setFirstJar(Jar firstJar) {
        this.firstJar = firstJar;
    }

    public Jar getSecondJar() {
        return secondJar;
    }

    public void setSecondJar(Jar secondJar) {
        this.secondJar = secondJar;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        definePosition();
        g.drawString("BeansContainer", posX, posY);

        firstJar.drawJar(posX / 2, posY * 2);
        secondJar.drawJar(posX + posX / 2, posY * 2);
    }

    private void definePosition() {
        posX = getParent().getSize().width / 2 - 50;
        posY = getParent().getSize().height / 5;
    }
}


