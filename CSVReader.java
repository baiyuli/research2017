import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.String;

public class CSVReader {

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

  // method to get the length of the event
    public static long getLengthOfEvent (String s, String e) {
      Timestamp start = Timestamp.valueOf(s);
      Timestamp end = Timestamp.valueOf(e);
      return end.getTime() - start.getTime();
    }
    //creates a csv file of the event ID and event lengths. it also returns an
    // ArrayList<String> object that contains the event ID and the event lengths
    public static ArrayList<String[]> lengthsToCSVFile(ArrayList<String[]> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("test.csv"));
      StringBuilder sb = new StringBuilder();
      ArrayList<String[]> arrayOfLengths = new ArrayList<String[]>();
      for (int x = 0; x < a.size(); x++) {
          sb.append(a.get(x)[0]);
          sb.append(',');
          long temp = getLengthOfEvent(a.get(x)[1], a.get(x)[2]);
          String length = Long.toString(temp);
          sb.append(length);
          sb.append('\n');
          String[] arr = {a.get(x)[0], length};
          arrayOfLengths.add(arr);
      }
      pw.write(sb.toString());
      pw.close();
      return arrayOfLengths;
    }

  public static void toString(ArrayList<String[]> array) {
    for (int x = 0; x < array.size(); x++) {
      for (int y = 0; y < array.get(x).length; y++) {
        System.out.print(array.get(x)[y] + " ");
      }
      System.out.println();
    }
  }


  /*Comparator for sorting the list by Student Name*/
 public static Comparator<String[]> lengthComparator = new Comparator<String[]>() {

	public int compare(String[] s1, String[] s2) {
	   String length1 = s1[1];
     String length2 = s2[1];

	   //ascending order
	   return length1.compareTo(length2);

    }};

  public static void main(String[] args) {
      ArrayList<String[]> array = getDataFromCSVFile("events.csv");
      toString(array);
      try {
        ArrayList<String[]> lengthArray = lengthsToCSVFile (array);
        toString(lengthArray);
        Collections.sort(lengthArray, lengthComparator);
        toString(lengthArray);
	SortedCSVtoText(lengthArray);
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }


    }
  // converts sorted CSV file into text file to be used by plotting program
  public static void SortedCSVtoText(ArrayList<String[]> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("Lengths.txt"));
      StringBuilder sb = new StringBuilder();
      ArrayList<String[]> arrayOfLengths = new ArrayList<String[]>();
      // append the parameter information to the text file
      sb.append("Title Length of Events");
      sb.append('\n');
      sb.append("xTitle Event");
      sb.append('\n');
      sb.append("yTitle Duration");
      sb.append('\n');
      sb.append("xLower 0");
      sb.append('\n');
      sb.append("xUpper " + a.size());
      sb.append('\n');
      sb.append("xInterval 1");
      sb.append('\n');
      sb.append("yLower "+ a.get(0)[1]);
      sb.append('\n');
      sb.append("yUpper " + a.get(a.size()-1)[1]);
      sb.append('\n');
      sb.append("yInterval 1000000000");
      sb.append('\n');
      sb.append("Data");
      sb.append('\n');

      for (int x = 0; x < a.size(); x++) {
          sb.append(x+1 + " " +  a.get(x)[1]);
          sb.append('\n');
          
      }
      pw.write(sb.toString());
      pw.close();
    }
}
