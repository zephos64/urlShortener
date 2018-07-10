import java.util.HashMap;

public class urlUtil {
    private HashMap<String, String> urlStorage;
    // linkId : url

    public urlUtil() {
        urlStorage = new HashMap<String, String>();
    }

    public boolean containsLinkId(String url) {
        return urlStorage.containsKey(url);
    }

    public String getShortenedUrl(String url) {
        return urlStorage.get(url);
    }

    public void setShortenedUrl(String linkid, String url) {
        urlStorage.put(linkid, url);
    }
}
