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
	String path[];
	ClassLoader parent;
	//*****************************************************************
	//*****************************************************************
	//*****************************************************************
    private byte[] loadClassFileData(String name) throws IOException {
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
		String sf = name.replace('.', File.separatorChar) + ".class";
		for (short i=0;i<path.length;i++) {
			if ((new File(path[i]+"/"+sf)).exists()) {
				on(".... loadClass 1 "+name+" using "+getClass());
				return getClass(name,path[i]+"/"+sf);
			} else {
		        on(".... NÃO loadClass '" + name + "' "+path[i]+"/"+sf );
			}
		}
        on("... NÃO loadClass '" + name + "'");
        return super.loadClass(name);
    }
	//*****************************************************************
    private Class getClass(String name,String file) throws ClassNotFoundException {
		on("getClass "+name);
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
    public classLoader(ClassLoader Parent,String Path[]) {
        super(Parent);
		parent = Parent;
		path = Path;
		on("parent "+parent.getClass().getName());
    }	
	//*****************************************************************
	void on(String s) {
		System.out.println("br.art.lar.javaLoader.classLoader: "+s);
	}
}
