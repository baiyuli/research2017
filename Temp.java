import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class Temp {
	// private ArrayList<Event> eventList = new ArrayList<Event>();
	private String[] columns = {"Event id X", "Event id Y", "X before Y", "X after Y", "X equal Y",
															"X meets Y", "X is met by Y", "X overlaps Y", "X overlapped by Y",
															"X during Y", "Y during X", "X starts Y", "X is started by Y", "X finishes Y", "X is finished by Y"};
	private static Hashtable<Integer, Event> table = new Hashtable<Integer, Event>();
	private static ArrayList<Integer[]> alanAlgebraTable = new ArrayList<Integer[]>();


	public static void constructAATable(ArrayList<Event> e) {
		for (int x = 0; x < e.size() - 1; x++) {
			for (int y = x + 1; y < e.size(); y++) {
				Integer[] temp = new Integer[15];
				temp[0] = e.get(x).getEventID();
				temp[1] = e.get(y).getEventID();
				alanAlgebraTable.add(temp);
			}
		}
	}


  public static void toStringComputations(ArrayList<Integer[]> array) {
    for (int x = 0; x < array.size(); x++) {
      for (int y = 0; y < array.get(x).length; y++) {
        System.out.print(array.get(x)[y] + " ");
      }
      System.out.println();
    }
  }

	public static void toStringComputations2(ArrayList<String[]> array) {
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
            long length = endTime.getTime() - startTime.getTime();
            Event temp = new Event(id, startTime, endTime, length);
            table.put(id, temp);
            array.add(temp);

        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    return array;
  }


  /*Comparator for sorting the list by Student Name*/
	public static Comparator<String[]> compString = new Comparator<String[]>() {

 	public int compare(String[] s1, String[] s2) {
 	   //ascending order
 	   return s1[2].compareTo(s2[2]);

     }};

 public static Comparator<Event> compEvent = new Comparator<Event>() {

	public int compare(Event e1, Event e2) {
	   //ascending order
	   return e1.getStartTime().compareTo(e2.getStartTime());

    }};

  public static void main(String[] args) {
    String fileName = args[0];
    if(fileName.endsWith(".csv")){

      ArrayList<Event> eventArray = getDataFromCSVFile(fileName);
      ArrayList<Event> sortedArrayByST = eventArray;
      Collections.sort(sortedArrayByST, compEvent);
      toStringEvent(sortedArrayByST);
    }
    else{
      System.out.println("Choose a CSV file");
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
