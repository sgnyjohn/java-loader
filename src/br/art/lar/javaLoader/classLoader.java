package br.art.lar.javaLoader;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
//import java.lang.ClassLoader;

//*********************************************************************
//*********************************************************************
public class classLoader extends ClassLoader {
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
    private byte[] loadClassFileData(String name) throws IOException {
		name="../"+name;
		on("loadClassFileData="+name);
        //InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
        InputStream stream = new FileInputStream(name);
        int size = stream.available();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(stream);
        in.readFully(buff);
        in.close();
        return buff;
    }	
	//*****************************************************************
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("app.") || name.startsWith("br.")) {
            on("loadClass 1 "+name+" using CCLoader");
            return getClass(name);
        } else {
	        on("NÃO loadClass '" + name + "'");
		}
        return super.loadClass(name);
    }
	//*****************************************************************
    private Class getClass(String name) throws ClassNotFoundException {
		on("getClass "+name);
        String file = name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassFileData(file);
            // defineClass is inherited from the ClassLoader class
            // that converts byte array into a Class. defineClass is Final
            // so we cannot override it
            Class c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	//*****************************************************************
    public classLoader(ClassLoader parent) {
        super(parent);
		on("parent "+parent.getClass().getName());
    }	
	//*****************************************************************
	void on(String s) {
		System.out.println("br.art.lar.javaLoader.classLoader: "+s);
	}
}
