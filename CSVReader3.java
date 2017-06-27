import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class CSVReader3 {

private static ArrayList<String[]> alanAlgebraTable = new ArrayList<String[]>();


	public static void constructAATable(ArrayList<Event> st) {
		for (int i = 0; i < st.size(); i++) {
			Event x = st.get(i);
			for (int j = i + 1; j < st.size(); j++) {
				Event y = st.get(j);
				// overlap, meet, and before
				if (x.getStartTime().compareTo(y.getStartTime()) < 0) {
					// overlaps
					if (x.getEndTime().compareTo(y.getStartTime()) > 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"overlaps", y.getStartTime() + ", " + x.getEndTime()};
										alanAlgebraTable.add(array);

						String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is overlapped by"};
						alanAlgebraTable.add(array2);
					}
					// meets
					else if (x.getEndTime().compareTo(y.getStartTime()) == 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "meets", y.getStartTime() + ""};

										alanAlgebraTable.add(array);
										String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is met by"};
										alanAlgebraTable.add(array2);
					}
					// before
					else if (x.getEndTime().compareTo(y.getStartTime()) < 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",	y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", "before"};
						alanAlgebraTable.add(array);

						String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is after"};
						alanAlgebraTable.add(array2);
					}

				}
				// equals, starts
				else if (x.getStartTime().compareTo(y.getStartTime()) == 0) {
					// equals
					if (x.getEndTime().compareTo(y.getEndTime()) == 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "equals"};
						alanAlgebraTable.add(array);

						String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is equal to"};
						alanAlgebraTable.add(array2);
					}
					// starts
					else if (x.getEndTime().compareTo(y.getEndTime()) < 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", + y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", "starts"};
						alanAlgebraTable.add(array);

						String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is started by"};
						alanAlgebraTable.add(array2);
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
										String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is during"};
										alanAlgebraTable.add(array2);
					}
					// finishes
					else if (x.getEndTime().compareTo(y.getEndTime()) == 0) {
						String[] array = {x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "",
										y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "",
										"finishes"};
						alanAlgebraTable.add(array);

						String[] array2 = {y.getEventID() + "", y.getStartTime() + "", y.getEndTime() + "", x.getEventID() + "", x.getStartTime() + "", x.getEndTime() + "", "is finished by"};
						alanAlgebraTable.add(array2);
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
						Event temp = new Event(id, startTime, endTime);
						// table.put(id, temp);
						array.add(temp);

				}

		} catch (IOException e) {
				e.printStackTrace();
		}
		return array;
	}

	public static void toCSV(ArrayList<String[]> a, String filename) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(filename));
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
			// ArrayList<Event> sortedArrayByST = eventArray;
			// Collections.sort(sortedArrayByST, compEvent);
			// constructAATable(sortedArrayByST);
			String output = args[1];
			constructAATable(eventArray);
			// try {
			// 	toCSV(alanAlgebraTable, output);
			// }
			// catch (FileNotFoundException e) {
			// 	e.printStackTrace();
			// }


    long endTime   = System.nanoTime();
    long totalTime = endTime - startTime; // Total duration of program
    System.out.println("Run time is: " + totalTime + " nanoseconds");
    }
}
