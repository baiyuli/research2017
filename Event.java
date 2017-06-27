import java.sql.Timestamp;
import java.lang.Object;

/*
 * Event class that defines an event object, allowing for tracking of events
 * through event id's.
 * @author Carlos Salas Ortega, Baiyu Li, Gianna Wu
 */
public class Event{
	int id, startTimeInt, endTimeInt;
	long length;
	Timestamp startTimestamp, endTimestamp;

	public Event(int id, Timestamp startTimestamp,Timestamp endTimestamp){
		this.id 	   = id;
		this.startTimestamp = startTimestamp;
		this.endTimestamp   = endTimestamp;
	}

	public Event(int id, int startTimeInt, int endTimeInt){
		this.id        = id;
		this.startTimeInt = startTimeInt;
		this.endTimeInt   = endTimeInt;

	}
	int getEventID(){
		return id;
	}

	Timestamp getStartTimestamp(){
		return startTimestamp;
	}

	Timestamp getEndTimestamp(){
		return endTimestamp;
	}

	int getStartTimeInt(){
		return startTimeInt;
	}

	int getEndTimeInt(){
		return endTimeInt;
	}

	void setStartTimestamp(Timestamp newStartTimestamp){
		startTimestamp = newStartTimestamp;
	}

	void setEndTimestamp(Timestamp newEndTimestamp){
		endTimestamp = newEndTimestamp;
	}

	void setStartTimeInt(int newStartTimeInt){
		startTimeInt = newStartTimeInt;
	}

	void setEndTimeInt(int newEndTimeInt){
		endTimeInt = newEndTimeInt;
	}

	public String toStringStamp(){
		String eventID = "Event ID: "   +  id + " ";
		String sTime   = "Start Time: " +  startTimestamp.toString() + " ";
		String eTime   =  "End Time:  " +  endTimestamp.toString() + "\n";

		return eventID + sTime + eTime;
	}


	public String toStringInt(){
		String eventID = "Event ID: "   +  id + " ";
		String sTime   = "Start Time: " +  startTimeInt + " ";
		String eTime   =  "End Time:  " +  endTimeInt + "\n";

		return eventID + sTime + eTime;
	}

}
