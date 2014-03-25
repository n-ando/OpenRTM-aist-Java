import java.util.Map;
import com.sun.javadoc.*;
import com.sun.tools.doclets.Taglet;

public class JaOnTaglet implements Taglet {
    private final static String TAG_NAME = ".ja";

    public String getName() {
        return TAG_NAME;
    }

    public boolean inField() {
        return false;
    }

    public boolean inConstructor() {
        return false;
    }

    public boolean inMethod() {
        return false;
    }

    public boolean inOverview() {
        return false;
    }

    public boolean inPackage() {
        return false;
    }

    public boolean inType() {
        return false;
    }

    public boolean isInlineTag() {
        return true;
    }

    public static void register(Map tagletMap) {
        JaOnTaglet tag = new JaOnTaglet();
        tagletMap.put(TAG_NAME, tag);
    }

    public String toString(Tag tag) {
        return tag.text();
    }

    public String toString(Tag[] tags) {
        return null;
    }
}
