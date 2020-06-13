package articles;

import Tools.Redacted;
import Tools.TimeTools;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import users.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
    This class describes/encapsulates everything to be known about a certain comment
 */

public class Comment extends Post {
    protected Integer parent;
    protected int articleID;
    public Comment(Integer ID, User author, String content,Timestamp updated, int articleID){
        this.articleID = articleID;
        this.ID = ID;
        this.author = author;
        if (content == null){
            content = "";
        }
        this.content = Jsoup.clean(content,Whitelist.relaxed());
        lastUpdate = updated;
    }
    public Comment(User author, String content,int articleID){//Assume new comment
        this(null,author,content,TimeTools.getTimestampNow(),articleID);
    }
    public Comment(int id, int articleID){
        this(id,
                Redacted.getUser(),
                Redacted.comment,
                new Timestamp(0),
                articleID);
    }
    public void update(String content){
        if (content == null){
            content = "";
        }
        this.content = Jsoup.clean(content,Whitelist.relaxed());
        lastUpdate = TimeTools.getTimestampNow();
    }


    public int getArticleID() {
        return articleID;
    }

    public String getContent() {
        return content;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getAuthorName(){
        return author.getUsername();
    }


    @Override
    public JSONObject moreJSON(JSONObject jsonObject) {
        jsonObject.put("comment",getJSONComments());
        jsonObject.put("content",getContent());
        jsonObject.put("size",getComments().size());
        jsonObject.put("updated",getDisplayTime());
        if (author != null){
            jsonObject.put("authorAvatar",author.getAvatar());
        } else {
            jsonObject.put("authorAvatar",Redacted.getUser().getAvatar());
        }
        return jsonObject;
    }
}
