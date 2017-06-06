import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;

public class Temp {



    public static void main(String[] args) {
      String text = "2011-10-02 18:48:05.123";
      String text2 = "2011-10-03 18:48:05.123";
      Timestamp ts = Timestamp.valueOf(text);
      Timestamp ts2 = Timestamp.valueOf(text2);
      System.out.println(ts);
      System.out.println(ts2);
      System.out.println(ts2.getTime() - ts.getTime() + " "); 
    }
}
