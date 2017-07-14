import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class CSVReader {

private static ArrayList<String[]> alanAlgebraTable = new ArrayList<String[]>();

// add length 
	public static void constructAATable(ArrayList<Event> st) {
		for (int i = 0; i < st.size(); i++) {
			Event x = st.get(i);
			for (int j = i + 1; j < st.size(); j++) {
				Event y = st.get(j);
				// overlap, meet, < (before), fi (inverse finish), di (inverse during)
				if (x.startTime.compareTo(y.startTime) < 0) {
					// overlaps
					if (x.endTime.compareTo(y.startTime) > 0 && x.endTime.compareTo(y.endTime) < 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", y.getEventID() + "", y.startTime + "", y.endTime + "",
										"overlaps", y.startTime + ", " + x.endTime};
						alanAlgebraTable.add(array);
					}
					// meets
					else if (x.endTime.compareTo(y.startTime) == 0 && x.endTime.compareTo(y.endTime) != 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", y.getEventID() + "", y.startTime + "", y.endTime + "", "meets", y.startTime + ""};
						alanAlgebraTable.add(array);
					}
					// before
					else if (x.endTime.compareTo(y.startTime) < 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "",	y.getEventID() + "", y.startTime + "", y.endTime + "", "before"};
						alanAlgebraTable.add(array);
					}
					// fi
					else if (x.endTime.compareTo(y.endTime) == 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "",	y.getEventID() + "", y.startTime + "", y.endTime + "", "fi"};
						alanAlgebraTable.add(array);
					}
					// di
					else if (x.endTime.compareTo(y.endTime) > 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "",	y.getEventID() + "", y.startTime + "", y.endTime + "", "di"};
						alanAlgebraTable.add(array);
					}
					else {
						System.out.println("ID1: " + x.getEventID() + "ID2: " + y.getEventID() + " error1");
					}

				}
				// equals, starts, si (starts inverse)
				else if (x.startTime.compareTo(y.startTime) == 0) {
					// equals
					if (x.endTime.compareTo(y.endTime) == 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", y.getEventID() + "", y.startTime + "", y.endTime + "", "equals"};
						alanAlgebraTable.add(array);
					}
					// starts
					else if (x.endTime.compareTo(y.endTime) < 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", + y.getEventID() + "", y.startTime + "", y.endTime + "", "starts"};
						alanAlgebraTable.add(array);
					}
					// si
					else if (x.endTime.compareTo(y.endTime) > 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", + y.getEventID() + "", y.startTime + "", y.endTime + "", "si"};
						alanAlgebraTable.add(array);
					}
					else {
						System.out.println("ID1: " + x.getEventID() + "ID2: " + y.getEventID() + " error2");
					}
				}
				// oi (inverse overlap), mi (inverse meet), > (after), during, finishes
				else {
					// oi
					if (x.endTime.compareTo(y.endTime) > 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", + y.getEventID() + "", y.startTime + "", y.endTime + "", "oi"};
						alanAlgebraTable.add(array);
					}
					// mi
					else if (x.startTime.compareTo(y.endTime) == 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", + y.getEventID() + "", y.startTime + "", y.endTime + "", "mi"};
						alanAlgebraTable.add(array);
					}
					// > (after)
					else if (x.startTime.compareTo(y.endTime) > 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", + y.getEventID() + "", y.startTime + "", y.endTime + "", "after"};
						alanAlgebraTable.add(array);
					}
					//during
					else if (x.endTime.compareTo(y.endTime) < 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", y.getEventID() + "", y.startTime + "", y.endTime + "", "during"};
						alanAlgebraTable.add(array);
					}
					// finishes
					else if (x.endTime.compareTo(y.endTime) == 0) {
						String[] array = {x.getEventID() + "", x.startTime + "", x.endTime + "", y.getEventID() + "", y.startTime + "", y.endTime + "", "finishes"};
						alanAlgebraTable.add(array);
					}
					else {
						System.out.println("ID1: " + x.getEventID() + "ID2: " + y.getEventID() + " error3");
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
      System.out.print(a.get(x).startTime + " ");
      System.out.print(a.get(x).endTime + " ");
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
						// Timestamp startTime = Timestamp.valueOf(event[1]);
						// Timestamp endTime = Timestamp.valueOf(event[2]);
						int startTime = Integer.valueOf(event[1]);
						int endTime = Integer.valueOf(event[2]);
						Event temp = new Event(id, startTime, endTime);
						// table.put(id, temp);
						array.add(temp);

				}

		} catch (IOException e) {
				e.printStackTrace();
		}
		return array;
	}

	public static ArrayList<Event> getDataFromCSVFileRoss(String file) {
		String csvFile = file;
		String line = "";
		String csvSplitBy = ",";
		ArrayList<Event> array = new ArrayList<Event>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] event = line.split(csvSplitBy);
				int id = Integer.parseInt(event[0].substring(1, event[0].length() - 1));
				int startTime = Integer.valueOf(event[2].substring(1, event[2].length() - 1));
				int endTime = Integer.valueOf(event[4].substring(1, event[4].length() - 1));
				Event temp = new Event(id, startTime, endTime);
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
		sb.append("E1 ID, \t E1 ST, \t E1 ET, \t E2 ST, \t E2 ET, \t AAC \n");
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
			   return e1.startTime.compareTo(e2.startTime);

		    }};

  public static void main(String[] args) {
      long startTime = System.nanoTime();

      String fileName = args[0];
			ArrayList<Event> eventArray = getDataFromCSVFileRoss(fileName);
			String output = args[1];
			constructAATable(eventArray);
			try {
				toCSV(alanAlgebraTable, output);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}


    long endTime   = System.nanoTime();
    long totalTime = endTime - startTime; // Total duration of program
    System.out.println("Run time is: " + totalTime + " nanoseconds");
    }
<<<<<<< HEAD


 public static void generateCsvFile(ArrayList<String[]> eventArray)
    {
        String output = "Event ID, Start Time, End Time \n";

for(int i = 0; i < eventArray.size;i++){
        for (String[] events in eventArray) {
            output += table.get(events[i]).getEventID() + ", " 
             		+ table.get(events[i]).getStartTime + ", "
             		+ table.get(events[i]).getEndTime   + "\n";
        }

        return output;
    	}
	}
}
public static void generateCsvFile(String fileName, ArrayList<String[]> eventArray)
    {
           try
           {
                FileWriter writer = new FileWriter(fileName);

                writer.append("Email");
                writer.append(',');
                writer.append("Name");
                writer.append('\n');

                for (String[] events in eventArray) {
                     writer.append(user.getEmail());
                     writer.append(',');
                     writer.append(user.getName());
                     writer.append('\n');
                }

                writer.flush();
                writer.close();
           } catch(IOException e) {
                 e.printStackTrace();
           } 
      }



  // converts sorted CSV file into text file to be used by plotting program
  public static void SortedCSVtoText(ArrayList<Strings> a) throws FileNotFoundException{
      PrintWriter pw = new PrintWriter(new File("EventsBefore.txt"));
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

    
=======
>>>>>>> 7bf15b4c7808101093eddb13fb571475aef5b5ea
}
