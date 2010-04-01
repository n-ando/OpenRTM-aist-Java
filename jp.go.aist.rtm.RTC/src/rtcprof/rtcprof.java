package rtcprof;

import java.util.Vector;
import java.lang.reflect.Field;
import java.io.File;


public class rtcprof {

    private static Class getClassFromName(String name){
        String separator =  "/";
        Class target = null;
        try {
            target = Class.forName(name);
        } catch (java.lang.NoClassDefFoundError e) {
            String messagetString = e.getMessage();
            System.err.println("    getMessage:"+messagetString);
            String key = "wrong name: ";
            int index = messagetString.indexOf(key);
            String packageName 
                = messagetString.substring(index+key.length(),
                                               messagetString.length()-1);
            packageName = packageName.replace(separator,".");
            packageName = packageName.trim();
            System.err.println("    packageName:"+packageName);
            target = rtcprof.getClassFromName(packageName);
        } catch (Exception e) {
            System.err.println("    Caught.Exception:"+e);
        }
        return target;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: ");
            if (args.length != 0) {
                System.err.println(args[0]+" .class ");
            }
            System.err.println("");
            System.exit(-1);
        }
        String separator =  System.getProperty("file.separator");
        Class target = null;
        File file = new File(args[0]);
        if(file.isAbsolute()) {
            args[0] = file.getName();
            String extensions[] = {".class", ".jar"};
            for(int ic=0;ic<extensions.length;++ic){
                if(args[0].endsWith(extensions[ic])){
                    int point = args[0].lastIndexOf(extensions[ic]);
                    args[0] =  args[0].substring(0, point);
                    if(extensions[ic].equals(".jar")){
                        args[0] =  args[0]+"."+args[0];
                    }
                    break;
                }
            }
        }
        else{
            args[0] = args[0].replace(separator,".");
            args[0] = args[0].replace("..",".");
        }

        target = rtcprof.getClassFromName(args[0]);

        if(target != null){
            try {
                Field field = target.getField("component_conf");
                String[] data = (String[])field.get(null);
                for(int ic=0;ic<data.length/2;++ic){
                    System.out.println(data[ic*2] +": "+data[ic*2+1]);
                }
            }
            catch(Exception e){
                System.err.println("    Caught.Exception. getFiled:"+e);
                System.exit(1);
            }
            System.exit(0);
       }
       System.exit(1);
    }
}


