package jsonbeans;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import beans.*;
import watch.VCWatch;

/**
 * Created by Morozov Ivan on 04.03.2016.
 */
public class JSONBeansManager extends JPanel {

    public final int HEIGHT = 400;
    public final int WIDTH = 800;

    private JTextArea log;
    private BeanInstantiator instantiator = new BeanInstantiator(this);

    JSONBeansManager(){

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setLayout(new BorderLayout());

        log = new JTextArea();
        add(log);
        log.setBorder(BorderFactory.createTitledBorder("Log"));
        log.setEditable(false);


        //test!!!
        loadAndInstantiateBean();
    }

    public void logMessage(String message){
        log.append(message + "\n");
    }

    private void loadAndInstantiateBean() {

//        logMessage("Loading and instantiating begins...");
//
//        Dolphin dolphin1 = (Dolphin)instantiator.loadAndInstantiateBean("beans.Dolphin");
//        Dolphin dolphin2 = (Dolphin)instantiator.loadAndInstantiateBean("beans.Dolphin");
//        Dolphin dolphin3 = (Dolphin)instantiator.loadAndInstantiateBean("beans.Dolphin");
//
//        dolphin1.setName("Amicus");
//        dolphin2.setName("Fidget");
//        dolphin3.setName("Flipper");
//
//        Pool pool = (Pool)instantiator.loadAndInstantiateBean("beans.Pool");
//
//        pool.setDolphins(new Dolphin[]{dolphin1, dolphin2, dolphin3});
//
//        Dolphinarium dolphinarium = (Dolphinarium)instantiator.loadAndInstantiateBean("beans.Dolphinarium");
//
//        dolphinarium.setPool(pool);

        VCWatch watch = (VCWatch)instantiator.loadAndInstantiateBean("watch.VCWatch");

        JSONEncoder jsonEncoder = new JSONEncoder();
        try{
            jsonEncoder.saveJSON(watch);

            logMessage(jsonEncoder.JSONasString());
        }
        catch (JSONSerializationException e){
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("JSON Beans");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(new JSONBeansManager());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
