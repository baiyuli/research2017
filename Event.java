import java.sql.Timestamp;
import java.lang.Object;

/*
 * Event class that defines an event object, allowing for tracking of events
 * through event id's.
 * @author Carlos Salas Ortega, Baiyu Li, Gianna Wu
 */
public class Event{
	int id;
	long length;
	Timestamp startTime, endTime;

	public Event(int id, Timestamp startTime,Timestamp endTime){
		this.id 	 		 = id;
		this.startTime = startTime;
		this.endTime   = endTime;
	}

	int getEventID(){
		return id;
	}

	Timestamp getStartTime(){
		return startTime;
	}

	Timestamp getEndTime(){
		return endTime;
	}

	void setStartTime(Timestamp newStartTime){
		startTime = newStartTime;
	}

	void setEndTime(Timestamp newEndTime){
		endTime = newEndTime;
	}

	public String toString(){
		String eventID = "Event ID: "   +  id + " ";
		String sTime   = "Start Time: " +  startTime.toString() + " ";
		String eTime   =  "End Time:  " +  endTime.toString() + "\n";

		return eventID + sTime + eTime;
	}

}
