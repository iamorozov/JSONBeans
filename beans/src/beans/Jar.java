package beans;

import javax.swing.*;
import java.awt.*;

public class Jar extends JComponent {

    private Bean[] beans;
    private int posX, posY;

    public Bean[] getBeans() {
        return beans;
    }

    public void setBeans(Bean[] beans) {
        this.beans = beans;
    }

    public Bean getBeans(int index) {
        return this.beans[index];
    }

    public void setBeans(int index, Bean bean) {
        this.beans[index] = bean;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawString("Jar with beans", posX, posY);
    }


    public void drawJar(int x, int y) {
        posX = x;
        posY = y;

        repaint();
    }
}
