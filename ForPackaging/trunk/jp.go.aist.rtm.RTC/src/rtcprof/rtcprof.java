package rtcprof;

import java.util.Vector;
import java.lang.reflect.Field;
import java.io.File;
import java.net.URI;
import java.net.URLClassLoader;
import java.net.URL;



public class rtcprof {

    /**
     * 
     */
    private static Class getClassFromName(String name){
        String separator =  System.getProperty("file.separator");
        Class target = null;

        try {
            target = Class.forName(name);
        } catch (java.lang.NoClassDefFoundError e) {
            String messagetString = e.getMessage();
            String key = "wrong name: ";
            int index = messagetString.indexOf(key);
            String packageName 
                = messagetString.substring(index+key.length(),
                                               messagetString.length()-1);
            packageName = packageName.replace("/",".");
            packageName = packageName.trim();
            target = rtcprof.getClassFromName(packageName);
        } catch (Exception e) {
            // do nothing
        }
        return target;
    }

    /**
     * 
     */
    private static Class getClassFromName(URLClassLoader url, 
                                           String name){
        String separator =  System.getProperty("file.separator");
        Class target = null;

        try {
            target = url.loadClass(name);
        } catch (java.lang.NoClassDefFoundError e) {
            String messagetString = e.getMessage();
            String key = "wrong name: ";
            int index = messagetString.indexOf(key);
            String packageName 
                = messagetString.substring(index+key.length(),
                                               messagetString.length()-1);

            URL[] urls = url.getURLs();
            java.util.ArrayList al 
                    = new java.util.ArrayList(java.util.Arrays.asList(urls));
            for(int ic=0;ic<urls.length;++ic){
                String stringPath = new String();
                String stringUrl = new String();
                try{
                    stringUrl = urls[ic].toURI().getPath();
                }
                catch(Exception ex){
                    continue;
                } 
                int pointer = packageName.lastIndexOf(name);
                String stringPackageName = packageName.substring(0, pointer);
                if(stringUrl.endsWith(stringPackageName)){
                    int point = stringUrl.lastIndexOf(stringPackageName);
                    stringPath = stringUrl.substring(0, point);
                    File path = new File(stringPath);
                    try{
                        URI uri = path.toURI();
                        al.add(uri.toURL());
                    }
                    catch(java.net.MalformedURLException ex){
                       // do nothing
                    }
                }
            }
            URL[] addUrls = (URL[])al.toArray(new URL[]{});
            url = url.newInstance(addUrls, url);

            packageName = packageName.replace("/",".");
            packageName = packageName.trim();


            target = rtcprof.getClassFromName(url,packageName);
        } catch (Exception e) {
            // do nothing
        }
        return target;
    }

    /**
     *
     */
    private static URLClassLoader createURLClassLoader(String parent){
        File path = new File(parent);
        URL[] urls = new URL[1];
        try{
            URI uri = path.toURI();
            urls[0] = uri.toURL();
        }
        catch(java.net.MalformedURLException ex){
            return null;
        }
        URLClassLoader url = new URLClassLoader(urls);
        return url;
    }
    /**
     * フルパスで指定されたコンポーネント（jarファイルとclassファイル）の
     * スペックを読み込み表示する。
     *
     *   jarファイルが指定された場合、
     *   jarファイルと同じ名前のクラスファイル(コンポーネント)を探す。
     *
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: ");
            if (args.length != 0) {
                System.err.println(args[0]+" .class or .jar");
            }
            System.err.println("");
            System.exit(-1);
        }
        String separator =  System.getProperty("file.separator");
        Class target = null;
        File file = new File(args[0]);
        if(file.isAbsolute()) {
            URLClassLoader url = createURLClassLoader(file.getParent());
            if(url!=null){
                String name = file.getName();
                String extensions[] = {".class", ".jar"};
                for(int ic=0;ic<extensions.length;++ic){
                    if(name.endsWith(extensions[ic])){
                        int point = name.lastIndexOf(extensions[ic]);
                        name = name.substring(0, point);
                        if(extensions[ic].equals(".jar")){
                            name =  name+"."+name;
                        }
                        break;
                    }
                }
                target = getClassFromName(url,name);
            }
        }
        else{
            args[0] = args[0].replace(separator,".");
            args[0] = args[0].replace("..",".");
            target = rtcprof.getClassFromName(args[0]);
        }


        if(target != null){
            try {
                Field field = target.getField("component_conf");
                String[] data = (String[])field.get(null);
                for(int ic=0;ic<data.length/2;++ic){
                    System.out.println(data[ic*2] +": "+data[ic*2+1]);
                }
            }
            catch(Exception e){
                System.exit(1);
            }
            System.exit(0);
       }
       System.exit(1);
    }
}


