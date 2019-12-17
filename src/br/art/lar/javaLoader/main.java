package br.art.lar.javaLoader;


import java.lang.reflect.Method;

//*********************************************************************
//*********************************************************************
public class main {
	//*****************************************************************
    public static void main(String args[]) throws Exception {
		(new main()).init(args);
	}
	//*****************************************************************
    public void init(String args[]) { 
		try {
			String paths[] = args[0].split(":");
			String progClass = args[1];
			String progArgs[] = new String[args.length - 2];
			System.arraycopy(args, 2, progArgs, 0, progArgs.length);

			classLoader ccl = new classLoader(getClass().getClassLoader(),paths);
			Class clas = ccl.loadClass(progClass);
			Class mainArgType[] = { (new String[0]).getClass() };
			Method main = clas.getMethod("main", mainArgType);
			Object argsArray[] = { progArgs };
			main.invoke(null, argsArray);

		} catch (Exception e) {
			System.out.println("ERROR main: "+e);
		}
    }	
}
