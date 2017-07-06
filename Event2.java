import java.sql.Timestamp;
import java.lang.Object;

/*
 * Event class that defines an event object, allowing for tracking of events
 * through event id's.
 * @author Carlos Salas Ortega, Baiyu Li, Gianna Wu
 */
public class Event{
	int id;
	Object startTime, endTime;

	public Event(int id, Object startTime, Object endTime){
		this.id 	   = id;
		if (startTime instanceof Timestamp) {
			this.startTime = (Timestamp)startTime;
			this.endTime = (Timestamp)endTime;
		}
		else {
			this.startTime = (Integer)startTime;
			this.endTime = (Integer)endTime;
		}
	}

	// any data type is an object - do typecasting
	Integer getEventID(){
		return id;
	}

	Object getStartTime(){
		if (this.startTime instanceof Timestamp) {
			return (Timestamp) this.startTime;
		}
		else {
			return (Integer) this.startTime;
		}
	}

	Object getEndTime() {
		if (this.endTime instanceof Timestamp) {
			return (Timestamp) this.endTime;
		}
		else {
			return (Integer) this.endTime;
		}
	}

	void setStartTime(Object newStartTime){
		startTime = newStartTime;
	}

	void setEndTime(Object newEndTime){
		endTime = newEndTime;
	}

	// public String toStringStamp(){
	// 	String eventID = "Event ID: "   +  id + " ";
	// 	String sTime   = "Start Time: " +  startTimestamp.toString() + " ";
	// 	String eTime   =  "End Time:  " +  endTimestamp.toString() + "\n";
	//
	// 	return eventID + sTime + eTime;
	// }
	//
	//
	// public String toStringInt(){
	// 	String eventID = "Event ID: "   +  id + " ";
	// 	String sTime   = "Start Time: " +  startTimeInt + " ";
	// 	String eTime   =  "End Time:  " +  endTimeInt + "\n";
	//
	// 	return eventID + sTime + eTime;
	// }

}
