import java.io.*; 
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class CSVReader {
	private ArrayList<Event> eventList;

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
    data = new Object[array.size()][4];
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

      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
      ArrayList<Event> eventList=array;
      try {
		SortedCSVtoText(eventList);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}


    }
  // converts sorted CSV file into text file to be used by plotting program
  public static void SortedCSVtoText(ArrayList<Event> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("Lengths.txt"));
      StringBuilder sb = new StringBuilder();
      ArrayList<Integer[]> arrayOfLengths = new ArrayList<Integer[]>();
      // append the parameter information to the text file
      sb.append("Title Start-time vs End-time");
      sb.append('\n');
      sb.append("xTitle Start-time");
      sb.append('\n');
      sb.append("yTitle End-time");
      sb.append('\n');
      sb.append("xLower 0");
      sb.append('\n');
      sb.append("xUpper 1320000000000");
      sb.append('\n');
      sb.append("xInterval 100000000000");
      sb.append('\n');
      sb.append("yLower 0");
      sb.append('\n');
      sb.append("yUpper 1330000000000");
      sb.append('\n');
      sb.append("yInterval 100000000000");
      sb.append('\n');
      sb.append("Data");
      sb.append('\n');

      for (int x = 0; x < a.size(); x++) {
          sb.append((a.get(x)).getStartTime().getTime() + " " +  (a.get(x)).getEndTime().getTime());
          sb.append('\n');

      }
      pw.write(sb.toString());
      pw.close();
    }
}
