import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.*;
import java.sql.Timestamp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class RandomTimeStampGenerator {

	static int dataPoints;
	static ArrayList<Event> eventList;
	static ArrayList<Timestamp> timeStampList;

	public static void main(String[] args) throws FileNotFoundException{		
	 	dataPoints = (Integer.parseInt(args[0])) * 2;
	 	eventList = new ArrayList<Event>();
		timeStampList = generateTimes(dataPoints);
		putOntoList();
		compare();


	for (int i = 0; i < eventList.size() ;i++ ) {
			System.out.println(eventList.get(i).toString());
		}

		PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("StartTime");
        sb.append(",");
        sb.append("EndTime");
        sb.append('\n');

    for (int i = 0; i < eventList.size(); i++){
        sb.append(eventList.get(i).getEventID() + "");
        sb.append(',');
        sb.append(eventList.get(i).getStartTime().toString());
        sb.append(',');
        sb.append(eventList.get(i).getEndTime().toString());
        sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
	}


	public static void compare(){
		for(int i = 0; i < eventList.size(); i++){
			Timestamp tempStart = eventList.get(i).getStartTime();
			Timestamp tempEnd   = eventList.get(i).getEndTime();
			if(tempStart.after(tempEnd)) {
				eventList.get(i).setStartTime(tempEnd);
				eventList.get(i).setEndTime(tempStart);
			}

		}
		
	}

	public static void putOntoList(){

	for(int i = 0; i < timeStampList.size()/2; i++){
		Event temp = new Event(i,timeStampList.get(i),timeStampList.get(i + (dataPoints /2)));
		eventList.add(temp);
	}
}
	
	public static ArrayList<Timestamp> generateTimes(int numDataPoints){
	ArrayList<Timestamp> tempList = new ArrayList<Timestamp>();
	long offset    = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
	long end       = Timestamp.valueOf("2017-01-01 00:00:00").getTime();
	long diff      = end - offset + 1;

	for(int i = 0; i < numDataPoints; i++){
		Timestamp temp = new Timestamp(offset + (long)(Math.random() * diff));
		tempList.add(temp);
	}
	return tempList;
	}

}
