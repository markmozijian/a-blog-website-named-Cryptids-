package Tools;

import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.Arrays;
/*
Tools relating to parsing
 */
public class ParseTools {
    public static boolean isInt(String str){
        if (str == null)
            return false;
        int len = str.length();
        if (len <= 0)
            return false;
        char x;
        x = str.charAt(0);
        int start = 0;
        if (x == '-')
            start = 1;
        for (int i = start; i < len; i++) {
            x = str.charAt(i);
            if (x < '0' || x > '9')
                return false;
        }
        return true;
    }
    public static Whitelist filter(){
        return Whitelist.relaxed().preserveRelativeLinks(true)
                .addProtocols("img", "src", "http", "https", "data", "cid")
                .addAttributes("iframe","width","height","frameborder","allow","allowfullscreen","src")
                .addAttributes("p","style","class")
                .addAttributes("span","style","class")
                .addAttributes("div","style","class");
    }
    public static String extractIMG(String html){
        Document document = Jsoup.parseBodyFragment(html);
        Elements imgs = document.getElementsByTag("img");
        return imgs.outerHtml();
    }
    public static String extractEmbedded(String html){
        Document document = Jsoup.parseBodyFragment(html);
        Elements elements = document.select("iframe,videos,embed");
        return elements.outerHtml();
    }
    public static String[] splitByNewLine(String str){
        return str.split("\\r?\\n");
    }
    public static JSONArray toJSONArray(String string){
        JSONArray array = new JSONArray();
        String[] stuff = ParseTools.splitByNewLine(string);
        array.addAll(Arrays.asList(stuff));
        return array;
    }
}
