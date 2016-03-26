package jsonbeans;

import java.io.*;
import java.lang.reflect.Type;

/**
 * Created by Morozov Ivan on 06.03.2016.
 */



public class BeanInstantiator extends ClassLoader{

    JSONBeansManager manager;

    private static final String CLASS_FILE_LOCATION = "D:\\Dropbox\\Курсач 2 курс\\JSON Beans\\out\\production\\beans\\";

    private Class<?> classLoaded = null;

    public BeanInstantiator(JSONBeansManager manager){

        super();

        this.manager = manager;
    }

    public Class<?> getClassLoaded() {
        return classLoaded;
    }

    @Override
    public Class<?> loadClass(String name) {

        try {
            classLoaded = super.loadClass(name);
            manager.logMessage("Class " + name + " has been loaded");
        }
        catch (ClassNotFoundException e){
            manager.logMessage("Class is not in classpath, try to load by custom loader");
        }

        if(classLoaded == null){
            try{
                StringBuilder fullPath = new StringBuilder(CLASS_FILE_LOCATION);
                fullPath.append(name.replace('.', File.separatorChar));
                fullPath.append(".class");

                final InputStream inputStream = new FileInputStream(new File(fullPath.toString()));
                byte [] data = new byte[inputStream.available()];
                inputStream.read(data);

                classLoaded = defineClass(name, data, 0, data.length);
                resolveClass(classLoaded);

                manager.logMessage("Class " + name + " has been loaded");

                return classLoaded;
            }
            catch (FileNotFoundException e){
                manager.logMessage(e.toString());
                return null;
            }
            catch (IOException e){
                manager.logMessage(e.toString());
                return null;
            }
        }

        return classLoaded;
    }

    private Object instantiate(){
        if(classLoaded != null){

            Object instance;
            manager.logMessage("instantiation of " + classLoaded.getName() + " beginning...");

            try{
                instance = classLoaded.newInstance();
            }
            catch (InstantiationException e) {
                manager.logMessage(e.toString());
                return null;
            }
            catch (IllegalAccessException e){
                manager.logMessage(e.toString());
                return null;
            }

            manager.logMessage("instantiation completed succesfully...");
            return instance;
        }

        return null;
    }

    public Object loadAndInstantiateBean(String beanName){
        loadClass(beanName);
        return instantiate();
    }
}
