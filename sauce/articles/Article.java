package articles;

import Tools.ParseTools;
import Tools.PathFinder;
import Tools.Redacted;
import Tools.TimeTools;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import users.User;

import java.sql.Timestamp;

/*
    This class describes/encapsulates everything to be known about a certain article
 */

public class Article extends Post {
    protected String title;
    protected String mainPicture;
    public final String DEFAULT_PIC = "main-bg.jpg";
    public String mainMedia;

    public Article(User author, String title, String content){
        this.author = author;
        this.title = Jsoup.clean(title, Whitelist.simpleText());
        this.content = Jsoup.clean(content, ParseTools.filter());
        this.lastUpdate = TimeTools.getTimestampNow();
    }

    public Article(int id){
        this.ID = id;
        this.author = Redacted.getUser();
        this.title = Redacted.title;
        this.content = Redacted.content;
        this.lastUpdate = new Timestamp(0);
    }

    public Article(int ID, User author, String title, String content,Timestamp updated){
        this(author,title,content);
        this.ID = ID;
        lastUpdate = updated;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getMainPicture() {
        if (mainPicture == null || mainPicture.length() <= 0){
            return DEFAULT_PIC;
        }
        return PathFinder.existsElse(PathFinder.mainPicPath,mainPicture,DEFAULT_PIC);
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getMainMedia() {
        return mainMedia;
    }

    public void setMainMedia(String mainMedia) {
        this.mainMedia = mainMedia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Article){
            return this.ID == ((Article) obj).ID;
        }
        return false;
    }

    public JSONObject moreJSON(JSONObject jsonObject){
        jsonObject.put("title",getTitle());
        jsonObject.put("mainPicture",getMainPicture());
        jsonObject.put("content",getContent());
        jsonObject.put("size",getSize());
        jsonObject.put("updated",getDisplayTime());
        return jsonObject;
    }

}
