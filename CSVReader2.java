import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class CSVReader2 {

	public static void constructAATable(ArrayList<Event> st, String filename) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(filename));
		StringBuilder sb = new StringBuilder();

		int size = st.size();
		int i,j;
		//String[] array = {""};
		for (i = 0; i < size; i++) {
			Event x = st.get(i);
			for (j = i + 1; j < size; j++) {
				Event y = st.get(j);
				int xStartToYStart = x.getStartTime().compareTo(y.getStartTime());
				int xStartToYEnd   = x.getStartTime().compareTo(y.getEndTime());
				int xEndToYStart   = x.getEndTime().compareTo(y.getStartTime());
				int xEndToYEnd     = x.getEndTime().compareTo(y.getEndTime());

				// overlap, meet, < (before), fi (inverse finish), di (inverse during)
				if (xStartToYStart < 0) {
					// overlaps
					if (xEndToYStart > 0 && xEndToYEnd < 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("overlaps");
					}
					// meets
					else if (xEndToYStart == 0 && xEndToYEnd != 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("meets at " + y.getStartTime());
					}
					// before
					else if (xEndToYStart < 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("before");
					}
					// fi
					else if (xEndToYEnd == 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("finished by");
					}
					// di
					else if (xEndToYEnd > 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("di");
					}
					else {
						System.out.println("ID1: " + x.getEventID() + "ID2: " + y.getEventID() + " error1");
					}

				}
				// equals, starts, si (starts inverse)
				else if (xStartToYStart == 0) {
					// equals
					if (xEndToYEnd == 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("equals");
					}
					// starts
					else if (xEndToYEnd < 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("starts");
					}
					// si
					else if (xEndToYEnd > 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("started by");
					}
					else {
						System.out.println("ID1: " + x.getEventID() + "ID2: " + y.getEventID() + " error2");
					}
				}
				// oi (inverse overlap), mi (inverse meet), > (after), during, finishes
				else {
					// oi
					if (xEndToYEnd > 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("overlapped by");
					}
					// mi
					else if (xStartToYEnd == 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("mi");
					}
					// > (after)
					else if (xStartToYEnd > 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("after");
					}
					//during
					else if (xEndToYEnd < 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("during");
					}
					// finishes
					else if (xEndToYEnd == 0) {
						sb.append(Integer.toString(x.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append(Integer.toString(y.getEventID()));
						sb.append(',');
						sb.append('\t');
						sb.append("finishes");
					}
					else {
						System.out.println("ID1: " + x.getEventID() + "ID2: " + y.getEventID() + " error3");
					}
				}
				sb.append('\n');
			}

		}
		pw.write(sb.toString());
		pw.close();

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

	public static ArrayList<Event> getDataFromCSVFileRoss(String file, int idCol, int stCol, int etCol) {
		String csvFile = file;
		String line = "";
		String csvSplitBy = ",";
		ArrayList<Event> array = new ArrayList<Event>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] event = line.split(csvSplitBy);
				int id = Integer.parseInt(event[idCol].substring(1, event[idCol].length() - 1));
				int startTime = Integer.valueOf(event[stCol].substring(1, event[stCol].length() - 1));
				int endTime = Integer.valueOf(event[etCol].substring(1, event[etCol].length() - 1));
				Event temp = new Event(id, startTime, endTime);
				array.add(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return array;
	}

	public static DefaultCategoryDataset createDataset(ArrayList<Event> e ) {
		 DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		 for (int x = 0; x < e.size(); x++) {
			 dataset.addValue(e.get(x).getLength(), "length", e.get(x).getStartTime() + "");
		 }
		 return dataset;
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

  public static void main(String[] args) throws Exception{
			Scanner scan = new Scanner(System.in);
			System.out.print("CSV File: ");
			String fileName = scan.next();
			System.out.print("Output File: ");
			String output = scan.next();
			System.out.print("ID Column: ");
			int idCol = scan.nextInt();
			System.out.print("Start time Column: ");
			int stCol = scan.nextInt();
			System.out.print("End time Column: ");
			int etCol = scan.nextInt();
			long startTime = System.nanoTime();
			ArrayList<Event> eventArray = getDataFromCSVFileRoss(fileName, idCol, stCol, etCol);
			try {
				constructAATable(eventArray, output);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}


    long endTime   = System.nanoTime();
    long totalTime = endTime - startTime; // Total duration of program
    System.out.println("Run time is: " + totalTime + " nanoseconds");
		Chart_AWT chart = new Chart_AWT(
			 "Scatter Plot" ,
			 "Length vs. Start time",
			 eventArray);


		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
    }
}
