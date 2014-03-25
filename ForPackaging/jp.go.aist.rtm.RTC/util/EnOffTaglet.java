import java.util.Map;
import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

public class EnOffTaglet implements Taglet {
    private final static String TAG_NAME = ".en";

    public String getName() {
        return TAG_NAME;
    }

    public boolean inField() {
        return true;
    }

    public boolean inConstructor() {
        return true;
    }

    public boolean inMethod() {
        return true;
    }

    public boolean inOverview() {
        return true;
    }

    public boolean inPackage() {
        return true;
    }

    public boolean inType() {
        return true;
    }

    public boolean isInlineTag() {
        return true;
    }

    public static void register(Map tagletMap) {
        EnOffTaglet tag = new EnOffTaglet();
        tagletMap.put(TAG_NAME, tag);
    }

    public String toString(Tag tag) {
        return "";
    }

    public String toString(Tag[] tags) {
        return null;
    }
}
