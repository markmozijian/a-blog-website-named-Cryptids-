package Tools;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
/*
nuff said
 */
public class TimeTools {
    public static final long tenmins = 600000;//600 sec * 1000
    public static final long expire = 6000000;
    public static Timestamp getTimestampNow(){
        LocalDateTime local = LocalDateTime.now();
        return Timestamp.valueOf(local);
    }
    public static String getLocalDateStr(){
        LocalDateTime now = LocalDateTime.now();
        return localDateToString(now);
    }
    public static String localDateToString(LocalDateTime date){
        return String.format("%4d-%2d-%2d",date.getYear(),date.getMonthValue(),date.getDayOfMonth());
    }
}
