package articles;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
    This abstract class represents the shared properties of both articles and comments
 */

public abstract class Post implements Comparable<Post>{

    protected Integer ID;
    protected User author;
    protected String content;
    protected Timestamp lastUpdate;
    protected List<Comment> comments = new ArrayList<>();
    protected boolean redacted = false;

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addComment(User commenter, String comment){
        comments.add(new Comment(commenter,comment,ID));
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public Timestamp getLastUpdate() {
        if (lastUpdate == null){
            return new Timestamp(0);
        }
        return lastUpdate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Integer getID() {
        return ID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public final JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",getID());
        if (author != null) {
            jsonObject.put("authorID", author.getID());
            jsonObject.put("authorName",author.getUsername());
        } else {
            jsonObject.put("authorID","");
            jsonObject.put("authorName","");
        }

        return moreJSON(jsonObject);
    }

    public abstract JSONObject moreJSON(JSONObject jsonObject);

    public JSONArray getJSONComments(){
        JSONArray array = new JSONArray();
        for (Comment comment:
                comments) {
            JSONObject obj = comment.toJSON();
            array.add(obj);
        }
        return array;
    }

    @Override
    public int compareTo(Post o) {
        return -1 * lastUpdate.compareTo(o.lastUpdate);
    }

    public int getSize(){
        int count = 0;
        if (this instanceof Comment)
            count++;
        for (Comment comment:
                this.getComments()) {
            count += comment.getSize();
        }
        return count;
    }

    public String getDisplayTime(){
        Timestamp timestamp = getLastUpdate();
        String[] str = timestamp.toString().split("[.]");
        return str[0];
    }

    public boolean isRedacted() {
        return redacted;
    }

    public void setRedacted(boolean redacted) {
        this.redacted = redacted;
    }
}
