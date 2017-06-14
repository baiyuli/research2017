import java.sql.Timestamp;
import java.lang.Object;

/*
 * Event class that defines an event object, allowing for tracking of events
 * through event id's.
 * @author Carlos Salas Ortega, Baiyu Li, Gianna Wu
 */
public class Event{
	int id, length;
	Timestamp startTime, endTime;

	public Event(int id, Timestamp startTime,Timestamp endTime, int length){
		this.id 	 		 = id;
		this.startTime = startTime;
		this.endTime   = endTime;
		this.length    = length;
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

	int getLength(){
		return length;
	}

}
