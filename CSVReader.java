import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.String;

public class CSVReader {

  public static ArrayList<Event> getDataFromCSVFile(String file){
    String csvFile = file;
    String line = "";
    String csvSplitBy = ",";
    ArrayList<Event> array = new ArrayList<Event>();
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] event = line.split(csvSplitBy);
            int id = Integer.parseInt(event[0]);
            Timestamp startTime = Timestamp.valueOf(event[1]);
            Timestamp endTime = Timestamp.valueOf(event[2]);
            int length = (int)(endTime.getTime() - startTime.getTime());
            Event temp = new Event(id, startTime, endTime, length);
            array.add(temp);

        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    return array;
  }

    //creates a csv file of the event ID and event lengths. it also returns an
    // ArrayList<String> object that contains the event ID and the event lengths
    public static ArrayList<Integer[]> lengthsToCSVFile(ArrayList<Event> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("test.csv"));
      StringBuilder sb = new StringBuilder();
      ArrayList<Integer[]> arrayOfLengths = new ArrayList<Integer[]>();
      for (int x = 0; x < a.size(); x++) {
          sb.append(a.get(x).getEventID());
          sb.append(',');
          String length = Long.toString(a.get(x).getLength());
          sb.append(length);
          sb.append('\n');
          Integer[] arr = {a.get(x).getEventID(), a.get(x).getLength()};
          arrayOfLengths.add(arr);
      }
      pw.write(sb.toString());
      pw.close();
      return arrayOfLengths;
    }

  public static void toStringLengths(ArrayList<Integer[]> array) {
    for (int x = 0; x < array.size(); x++) {
      for (int y = 0; y < array.get(x).length; y++) {
        System.out.print(array.get(x)[y] + " ");
      }
      System.out.println();
    }
  }

  public static void toStringEvent(ArrayList<Event> a) {
    for (int x = 0; x < a.size(); x++) {
      System.out.print(a.get(x).getEventID() + " ");
      System.out.print(a.get(x).getStartTime() + " ");
      System.out.print(a.get(x).getEndTime() + " ");
      System.out.print(a.get(x).getLength() + " ");
      System.out.println();
    }
  }


  /*Comparator for sorting the list by Student Name*/
 public static Comparator<Integer[]> lengthComparator = new Comparator<Integer[]>() {

	public int compare(Integer[] s1, Integer[] s2) {
	   //ascending order
	   return s1[1] - s2[1];

    }};

  public static void main(String[] args) {
      ArrayList<Event> array = getDataFromCSVFile("events.csv");
      toStringEvent(array);
      try {
        ArrayList<Integer[]> lengthArray = lengthsToCSVFile (array);
        toStringLengths(lengthArray);
        Collections.sort(lengthArray, lengthComparator);
	       SortedCSVtoText(lengthArray);
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }


    }
  // converts sorted CSV file into text file to be used by plotting program
  public static void SortedCSVtoText(ArrayList<Integer[]> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("Lengths.txt"));
      StringBuilder sb = new StringBuilder();
      ArrayList<Integer[]> arrayOfLengths = new ArrayList<Integer[]>();
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
