import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;
import java.io.Writer;

public class CSVReader {
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

	public static void computeBefore() {
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getEndTime().compareTo(temp1.getStartTime()) < 0) {
				alanAlgebraTable.get(x)[2] = 1;
				alanAlgebraTable.get(x)[3] = 0;
			}
			else if (temp1.getEndTime().compareTo(temp0.getStartTime()) > 0) {
				alanAlgebraTable.get(x)[2] = 0;
				alanAlgebraTable.get(x)[3] = 1;
			}
			else {
				alanAlgebraTable.get(x)[2] = 0;
				alanAlgebraTable.get(x)[3] = 0;
			}
		}
	}

	public static void computeEqual() {
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getStartTime().compareTo(temp1.getStartTime()) == 0 &&
					temp0.getEndTime().compareTo(temp0.getEndTime()) == 0) {
				alanAlgebraTable.get(x)[4] = 1;
			}
			else {
				alanAlgebraTable.get(x)[4] = 0;
			}
		}
	}

	public static ArrayList<Integer[]> computeMeet() {
		ArrayList<Integer[]> a = new ArrayList<Integer[]>();
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getEndTime().compareTo(temp1.getStartTime()) == 0) {
				alanAlgebraTable.get(x)[5] = 1;
				alanAlgebraTable.get(x)[6] = 0;
				Integer[] arr = {temp0.getEventID(), temp1.getEventID(), (int)(temp1.getEndTime().getTime())};

				String[] arr = {temp0.getEventID() + "", temp1.getEventID() + "", temp0.getEndTime() + ""};

				a.add(arr);

			}
			else if (temp1.getEndTime().compareTo(temp0.getStartTime()) == 0) {
				alanAlgebraTable.get(x)[5] = 0;
				alanAlgebraTable.get(x)[6] = 1;
				Integer[] arr = {temp1.getEventID(), temp0.getEventID(), (int)(temp0.getEndTime().getTime())};

				String[] arr = {temp1.getEventID()  + "", temp0.getEventID()  + "", temp1.getEndTime() + ""};

				a.add(arr);
			}
			else {
				alanAlgebraTable.get(x)[5] = 0;
				alanAlgebraTable.get(x)[6] = 0;
			}
		}
		return a;
	}

	public static void computeOverlaps() {
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getStartTime().compareTo(temp1.getStartTime()) < 0 &&
					temp0.getEndTime().compareTo(temp1.getEndTime()) < 0) {
				alanAlgebraTable.get(x)[7] = 1;
				alanAlgebraTable.get(x)[8] = 0;
			}
			else if (temp1.getStartTime().compareTo(temp0.getStartTime()) < 0 &&
							temp1.getEndTime().compareTo(temp0.getEndTime()) < 0) {
				alanAlgebraTable.get(x)[7] = 0;
				alanAlgebraTable.get(x)[8] = 1;
			}
			else {
				alanAlgebraTable.get(x)[7] = 0;
				alanAlgebraTable.get(x)[8] = 0;
			}
		}
	}

	public static void computeDuring() {
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getStartTime().compareTo(temp1.getStartTime()) > 0 &&
					temp0.getEndTime().compareTo(temp1.getEndTime()) < 0) {
				alanAlgebraTable.get(x)[9] = 1;
				alanAlgebraTable.get(x)[10] = 0;
			}
			else if (temp1.getStartTime().compareTo(temp0.getStartTime()) > 0 &&
							temp1.getEndTime().compareTo(temp0.getEndTime()) < 0) {
				alanAlgebraTable.get(x)[9] = 0;
				alanAlgebraTable.get(x)[10] = 1;
			}
			else {
				alanAlgebraTable.get(x)[9] = 0;
				alanAlgebraTable.get(x)[10] = 0;
			}
		}
	}

	public static void computeStarts() {
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getStartTime().compareTo(temp1.getStartTime()) == 0 &&
					temp0.getEndTime().compareTo(temp1.getEndTime()) < 0) {
				alanAlgebraTable.get(x)[11] = 1;
				alanAlgebraTable.get(x)[12] = 0;
			}
			else if (temp1.getStartTime().compareTo(temp0.getStartTime()) == 0 &&
							temp1.getEndTime().compareTo(temp0.getEndTime()) > 0) {
				alanAlgebraTable.get(x)[11] = 0;
				alanAlgebraTable.get(x)[12] = 1;
			}
			else {
				alanAlgebraTable.get(x)[11] = 0;
				alanAlgebraTable.get(x)[12] = 0;
			}
		}
	}

	public static void computeFinishes() {
		for (int x = 0; x < alanAlgebraTable.size(); x++) {
			Event temp0 = table.get(alanAlgebraTable.get(x)[0]);
			Event temp1 = table.get(alanAlgebraTable.get(x)[1]);

			if (temp0.getStartTime().compareTo(temp1.getStartTime()) < 0 &&
					temp0.getEndTime().compareTo(temp1.getEndTime()) == 0) {
				alanAlgebraTable.get(x)[13] = 1;
				alanAlgebraTable.get(x)[14] = 0;
			}
			else if (temp1.getStartTime().compareTo(temp0.getStartTime()) > 0 &&
							temp1.getEndTime().compareTo(temp0.getEndTime()) == 0) {
				alanAlgebraTable.get(x)[13] = 0;
				alanAlgebraTable.get(x)[14] = 1;
			}
			else {
				alanAlgebraTable.get(x)[13] = 0;
				alanAlgebraTable.get(x)[14] = 0;
			}
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
            int length = (int)(endTime.getTime() - startTime.getTime());
            Event temp = new Event(id, startTime, endTime, length);
						table.put(id, temp);
            array.add(temp);

        }

    } catch (IOException e) {
        e.printStackTrace();
    }
    return array;
  }

    //creates a csv file of the event ID and event lengths. it also returns an
    // ArrayList<String> object that contains the event ID and the event lengths

    public static ArrayList<Integer[]> computeLengths(ArrayList<Event> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("test.csv"));
      StringBuilder sb = new StringBuilder();
      ArrayList<Integer[]> arrayOfLengths = new ArrayList<Integer[]>();

    public static ArrayList<String[]> computeLengths(ArrayList<Event> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("test.csv"));
      StringBuilder sb = new StringBuilder();
      ArrayList<String[]> arrayOfLengths = new ArrayList<String[]>();

      for (int x = 0; x < a.size(); x++) {
          sb.append(a.get(x).getEventID());
          sb.append(',');
          String length = Long.toString(a.get(x).getLength());
          sb.append(length);
          sb.append('\n');

          Integer[] arr = {a.get(x).getEventID(), a.get(x).getEventID(), a.get(x).getLength()};

          String[] arr = {a.get(x).getEventID() + "", a.get(x).getEventID() + "", length};

          arrayOfLengths.add(arr);
      }
      pw.write(sb.toString());
      pw.close();
      return arrayOfLengths;
    }

  public static void toStringComputations(ArrayList<Integer[]> array) {
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

 public static Comparator<Integer[]> comparator = new Comparator<Integer[]>() {


	public static Comparator<String[]> compString = new Comparator<String[]>() {

 	public int compare(String[] s1, String[] s2) {
 	   //ascending order
 	   return s1[2].compareTo(s2[2]);

     }};

 public static Comparator<Integer[]> comparator = new Comparator<Integer[]>() {


	public int compare(Integer[] s1, Integer[] s2) {
	   //ascending order
	   return s1[2] - s2[2];

    }};

  public static void main(String[] args) {
      long startTime = System.nanoTime(); //Start time for duration of program
      
      String fileName = args[0];
      if(fileName.endsWith(".csv")){

      ArrayList<Event> array = getDataFromCSVFile(fileName);
      try {
				constructAATable(array);
				// computeBefore();
				// computeEqual();
				// computeMeet();
				// computeOverlaps();
				// computeDuring();
				// computeStarts();
				// computeFinishes();
				String computation = args[1];
				if (computation.equals("lengths")) {
					System.out.println("Events and their lengths (unsorted): ");

					ArrayList<Integer[]> lengthArray = computeLengths(array);
					toStringComputations(lengthArray);
					ArrayList<String[]> lengthArray = computeLengths(array);
					toStringComputations2(lengthArray);

					System.out.println("Events and their lengths (sorted): ");
					Collections.sort(lengthArray, compString);
					toStringComputations2(lengthArray);
				}
				else if (computation.equals("meets")) {
					System.out.println("Events and their meet times (unsorted): ");
					ArrayList<Integer[]> meetArray = computeMeet();
					toStringComputations(meetArray);
					System.out.println("Events and their meet times (sorted): ");
					Collections.sort(meetArray, comparator);
					toStringComputations(meetArray);
				}
				toStringComputations(alanAlgebraTable);

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
else{
  System.out.println("Choose a CSV file");
}

    long endTime   = System.nanoTime();
    long totalTime = endTime - startTime; // Total duration of program 
    System.out.println("Run time is: " + totalTime + "nanoseconds");
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
