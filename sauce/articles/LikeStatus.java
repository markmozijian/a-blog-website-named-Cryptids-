package articles;


public class LikeStatus {
    protected int id;
    protected int articleID;
    protected int userID;
    protected int status;

    public void setId(int id) {
        this.id = id;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public int getArticleID() {
        return articleID;
    }

    public int getUserID() {
        return userID;
    }

    public int getStatus() {
        return status;
    }


}
