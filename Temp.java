import java.io.*;
import java.sql.Timestamp;
import java.lang.Object;
import java.lang.Enum;
import java.util.*;
import java.lang.String;

public class Temp {

  public static ArrayList<String[]> computeBefore(ArrayList<Event> st, ArrayList<Event> et) {
    ArrayList<String[]> result = new ArrayList<String[]>();
    int index = st.size() - 1;
    for (int x = 0; x < st.size(); x++) {
      Event end = et.get(x);
      Event start = st.get(index);
      if (end.getEndTime().compareTo(st.get(st.size() - 1).getStartTime()) > 0) {
        return result;
      }
      while (end.getEndTime().compareTo(start.getStartTime()) < 0 && index >= 0) {
        String[] temp = {end.getEventID() + "", start.getEventID() + "", end.getEndTime() + ""};
        result.add(temp);
        index--;
        start = st.get(index);
      }
      index = st.size() - 1;
    }
    return result;
  }

		public static ArrayList<String[]> computeEquals(ArrayList<Event> st) {
			ArrayList<String[]> result = new ArrayList<String[]>();
			for (int x = 0; x < st.size() - 1; x++) {
				int y = x+1;
				while (st.get(x).getStartTime().compareTo(st.get(y).getStartTime()) == 0 && y < st.size()) {
					if (st.get(x).getEndTime().compareTo(st.get(y).getEndTime()) == 0) {
						String[] temp = {st.get(x).getEventID() + "", st.get(y).getEventID() + ""};
						result.add(temp);
					}
					y++;
				}
			}
			return result;
		}
		// make sure to change this so that it can leave the for loop as soon as possible
		public static ArrayList<String[]> computeMeets(ArrayList<Event> st, ArrayList<Event> et) {
			ArrayList<String[]> result = new ArrayList<String[]>();
			int index = 0;
			for (int x = 0; x < et.size(); x++) {
        if (index >= st.size()) {
          return result;
        }
        // figure out the logic behind this later
        while (index > - 1 && et.get(x).getEndTime().compareTo(st.get(index).getStartTime()) < 0) {
          index--;
        }
				while (index < st.size() && et.get(x).getEndTime().compareTo(st.get(index).getStartTime()) > 0) {
					index++;
				}
        int temp2 = index;
				while (temp2 < st.size() && et.get(x).getEndTime().compareTo(st.get(temp2).getStartTime()) == 0) {
					String[] temp = {et.get(x).getEventID() + "", st.get(temp2).getEventID() + "", et.get(x).getEndTime() + ""};
					result.add(temp);
					temp2++;
				}
			}
			return result;
		}

		public static ArrayList<String[]> computeOverlaps (ArrayList<Event> st) {
      ArrayList<String[]> result = new ArrayList<String[]>();
			for (int x = 0; x < st.size() - 1; x++) {
        for (int y = x + 1; y < st.size(); y++) {
          if (st.get(x).getStartTime().compareTo(st.get(y).getStartTime()) < 0 &&
          st.get(x).getEndTime().compareTo(st.get(y).getStartTime()) > 0) {
              String[] temp = {st.get(x).getEventID() + "", st.get(y).getEventID() + "",
                              st.get(y).getStartTime() + " - " + st.get(x).getEndTime()};
              result.add(temp);
          }
        }
      }
			return result;
		}

    public static ArrayList<String[]> computeDuring (ArrayList<Event> et) {
      ArrayList<String[]> result = new ArrayList<String[]>();
      for (int x = 0; x < et.size() - 1; x++) {
        for (int y = 0; y < et.size(); y++) {
          if (et.get(x).getEndTime().compareTo(et.get(y).getEndTime()) < 0 &&
            et.get(x).getStartTime().compareTo(et.get(y).getStartTime()) > 0) {
            String[] temp = {et.get(x).getEventID() + "", et.get(y).getEventID() + ""};
            result.add(temp);
          }
        }
      }
      return result;
    }

    public static ArrayList<String[]> computeStarts (ArrayList<Event> st) {
      ArrayList<String[]> result = new ArrayList<String[]>();
      int index = 1;
      for (int x = 0; x < st.size(); x++) {
        int temp2 = index;
        while (temp2 < st.size() && st.get(x).getStartTime().compareTo(st.get(temp2).getStartTime()) == 0) {
          if (st.get(x).getEndTime().compareTo(st.get(temp2).getEndTime()) < 0) {
            String[] temp = {st.get(x).getEventID() + "", st.get(temp2).getEventID() + ""};
            result.add(temp);
          }
          temp2++;
        }
        index = x + 1;
      }
      return result;
    }

    public static ArrayList<String[]> computeFinishes (ArrayList<Event> et) {
      ArrayList<String[]> result = new ArrayList<String[]>();
      int index = 1;
      for (int x = 0; x < et.size(); x++) {
        int temp2 = index;
        while (temp2 < et.size() && et.get(x).getStartTime().compareTo(et.get(temp2).getStartTime()) == 0) {
          if (et.get(x).getStartTime().compareTo(et.get(temp2).getStartTime()) > 0) {
            String[] temp = {et.get(x).getEventID() + "", et.get(temp2).getEventID() + ""};
            result.add(temp);
          }
          temp2++;
        }
        index = x + 1;
      }
      return result;
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
      long startTime = System.nanoTime();
      ArrayList<Event> eventArray = getDataFromCSVFile(fileName);
      ArrayList<Event> sortedArrayByST = eventArray;
      Collections.sort(sortedArrayByST, compEvent);

      ArrayList<Event> sortedArrayByET = eventArray;
      Collections.sort(sortedArrayByET, compEvent);
			String computation = args[1];
			if (computation.equals("before")) {
				ArrayList<String[]> before = computeBefore(sortedArrayByST, sortedArrayByET);
				toStringComputations2(before);
			}
			else if (computation.equals("equals")) {
				ArrayList<String[]> equals = computeEquals(sortedArrayByST);
				toStringComputations2(equals);
			}
			else if (computation.equals("meets")) {
				ArrayList<String[]> meets = computeMeets(sortedArrayByST, sortedArrayByET);
				toStringComputations2(meets);
			}
			else if (computation.equals("overlaps")) {
				ArrayList<String[]> overlaps = computeOverlaps(sortedArrayByST);
				toStringComputations2(overlaps);
			}
      else if (computation.equals("during")) {
        ArrayList<String[]> during = computeDuring(sortedArrayByET);
        toStringComputations2(during);
      }
      else if (computation.equals("starts")) {
        ArrayList<String[]> starts = computeStarts(sortedArrayByST);
        toStringComputations2(starts);
      }
      else if (computation.equals("finishes")) {
        ArrayList<String[]> finishes = computeFinishes(sortedArrayByET);
        toStringComputations2(finishes);
      }


      long endTime   = System.nanoTime();
      long totalTime = endTime - startTime; // Total duration of program
      System.out.println("Run time is: " + totalTime + " nanoseconds");

      // long startTime2 = System.nanoTime();
      // constructAATable(eventArray);
      // ArrayList<String[]> before2 = computeBeforeOrig();
      // // toStringComputations2(before2);
      // long endTime2 = System.nanoTime();
      // long totalTime2 = endTime2 - startTime2;
      // System.out.println("Run time2 is: " + totalTime2 + " nanoseconds");

    }
    else{
      System.out.println("Choose a CSV file");
    }
  }
}
