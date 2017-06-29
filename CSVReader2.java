import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class CSVReader2 {
	// private ArrayList<Event> eventList = new ArrayList<Event>();
	private String[] columns = {"Event id X", "Event id Y", "X before Y", "X after Y", "X equal Y",
															"X meets Y", "X is met by Y", "X overlaps Y", "X overlapped by Y",
															"X during Y", "Y during X", "X starts Y", "X is started by Y", "X finishes Y", "X is finished by Y"};
	private static Hashtable<Integer, Event> table = new Hashtable<Integer, Event>();
	private static ArrayList<String[]> alanAlgebraTable = new ArrayList<String[]>();


	public static void constructAATable(ArrayList<Event> st) {
		for (int i = 0; i < st.size(); i++) {
			Event x = st.get(i);
			for (int j = 0; j < st.size(); j++) {
				Event y = st.get(j);
				// overlap, meet, and before
				if (x.getStartTime().compareTo(y.getStartTime()) < 0) {
					// overlaps
					if (x.getEndTime().compareTo(y.getStartTime()) > 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"overlaps", y.getStartTime() + ", " + x.getEndTime()};

										alanAlgebraTable.add(array);
					}
					// meets
					else if (x.getEndTime().compareTo(y.getStartTime()) == 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"meets", y.getStartTime() + ""};

										alanAlgebraTable.add(array);
					}
					// before
					else if (x.getEndTime().compareTo(y.getStartTime()) < 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"before"};

										alanAlgebraTable.add(array);
					}

				}
				// equals, starts
				else if (x.getStartTime().compareTo(y.getStartTime()) == 0) {
					// equals
					if (x.getEndTime().compareTo(y.getEndTime()) == 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"equals"};

										alanAlgebraTable.add(array);
					}
					// starts
					else if (x.getEndTime().compareTo(y.getEndTime()) < 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"starts"};

										alanAlgebraTable.add(array);
					}
				}
				// during, finishes
				else {
					//during
					if (x.getEndTime().compareTo(y.getEndTime()) < 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"during"};

										alanAlgebraTable.add(array);
					}
					// finishes
					else if (x.getEndTime().compareTo(y.getEndTime()) == 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"finishes"};
						alanAlgebraTable.add(array);
					}
				}

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
						// table.put(id, temp);
						array.add(temp);

				}

		} catch (IOException e) {
				e.printStackTrace();
		}
		return array;
	}

	public static void toCSV(ArrayList<String[]> a) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File("test.csv"));
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < a.size(); x++) {
			for (int y = 0; y < a.get(x).length; y++) {
			sb.append(a.get(x)[y]);
			sb.append(',');
			sb.append('\t');
			}
			sb.append('\n');
		}
		pw.write(sb.toString());
		pw.close();
	}


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
      long startTime = System.nanoTime();

      String fileName = args[0];
			ArrayList<Event> eventArray = getDataFromCSVFile(fileName);
			ArrayList<Event> sortedArrayByST = eventArray;
			Collections.sort(sortedArrayByST, compEvent);
			constructAATable(sortedArrayByST);
			try {
				toCSV(alanAlgebraTable);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			SortAATable(alanAlgebraTable);
	




    long endTime   = System.nanoTime();
    long totalTime = endTime - startTime; // Total duration of program
    System.out.println("Run time is: " + totalTime + " nanoseconds");
    }

    
    public static void SortAATable(ArrayList<String[]> a){
    

            
            long minStartTime = (Timestamp.valueOf(a.get(0)[1])).getTime();
            
            long maxEndTime   = (Timestamp.valueOf(a.get(a.size()-1)[5])).getTime();
          
            long interval     = (maxEndTime - minStartTime)/100; 
            ArrayList<String> concurrentIDs = new ArrayList<String>();
            Hashtable intervalAndIDs = new Hashtable();
            Enumeration times;
            int intervals;
            int numberOfIDs;
    
            
            for (long x = minStartTime; x < maxEndTime ; x = x+interval){
            	for (int y = 0 ; y < a.size() ; y++){
            		
            		if (Timestamp.valueOf(a.get(y)[1]).getTime()>= x && 
            			Timestamp.valueOf(a.get(y)[1]).getTime() < x+interval &&
            			!concurrentIDs.contains(a.get(y)[0])){
            				concurrentIDs.add(a.get(y)[0]);
            			
            				if (a.get(y)[6] == "equals" && !concurrentIDs.contains(a.get(y)[3])){
            					concurrentIDs.add(a.get(y)[3]);
            				}
            				if (a.get(y)[6] == "meets" && 
            					Timestamp.valueOf(a.get(y)[7]).getTime() < x+interval &&
            					!concurrentIDs.contains(a.get(y)[3])){
            					concurrentIDs.add(a.get(y)[3]);
            				}
            				if (a.get(y)[6] == "starts" && !concurrentIDs.contains(a.get(y)[3])){
            					concurrentIDs.add(a.get(y)[3]);
            				}
            				if (a.get(y)[6] == "during" && !concurrentIDs.contains(a.get(y)[3])){
            					concurrentIDs.add(a.get(y)[3]);
            				}
            				if (a.get(y)[6] == "meets" && 
                					Timestamp.valueOf(a.get(y)[7]).getTime() < x+interval &&
                					!concurrentIDs.contains(a.get(y)[3])){
                					concurrentIDs.add(a.get(y)[3]);
                				}
            	}
    }
            	intervalAndIDs.put(x, concurrentIDs.size());
            	concurrentIDs.clear();
    }
    System.out.println("hello");
              try{  SortedCSVtoText(intervalAndIDs, minStartTime, maxEndTime, interval, 13);
              		}
              catch (FileNotFoundException e){e.printStackTrace();}
  }

  // converts sorted CSV file into text file to be used by plotting program
  public static void SortedCSVtoText(Hashtable a, long minStartTime, long maxEndTime, long interval, int numEvents) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("concurrentEvents.txt"));
      StringBuilder sb = new StringBuilder();
      ArrayList<Integer[]> arrayOfLengths = new ArrayList<Integer[]>();
      // append the parameter information to the text file
      sb.append("Title Time vs Number of Events");
      sb.append('\n');
      sb.append("xTitle Time (ms)");
      sb.append('\n');
      sb.append("yTitle Number of Events");
      sb.append('\n');
      sb.append("xLower " + minStartTime);
      sb.append('\n');
      sb.append("xUpper " + maxEndTime);
      sb.append('\n');
      sb.append("xInterval " + interval);
      sb.append('\n');
      sb.append("yLower 0");
      sb.append('\n');
      sb.append("yUpper " + numEvents);
      sb.append('\n');
      sb.append("yInterval 1");
      sb.append('\n');
      sb.append("Data");
      sb.append('\n');

      for (long x = minStartTime ; x < maxEndTime ; x = x + interval) {
          
          sb.append(x + " " +  a.get(x));
          sb.append('\n');

      }
      pw.write(sb.toString());
      pw.close();
    }
}
