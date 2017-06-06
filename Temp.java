import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class Temp {

  public static long getLengthOfEvent (String s, String e) {
    Timestamp start = Timestamp.valueOf(s);
    Timestamp end = Timestamp.valueOf(e);
    return end.getTime() - start.getTime();
  }

  public static ArrayList<String[]> getDataFromCSVFile(String file){
    String csvFile = file;
    String line = "";
    String cvsSplitBy = ",";
    ArrayList<String[]> array = new ArrayList<String[]>();
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] event = line.split(cvsSplitBy);

            array.add(event);

        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println(array.toString());
    return array;
  }

  public static void main(String[] args) {
      getDataFromCSVFile("events.csv");
    }

}
