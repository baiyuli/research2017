import java.sql.Timestamp;
import java.lang.Object;

/*
 * Event class that defines an event object, allowing for tracking of events
 * through event id's.
 * @author Carlos Salas Ortega, Baiyu Li, Gianna Wu
 */
public class Event{
	public int id;
	public Integer startTime, endTime;
	public long length;


	// public Event(int id, Timestamp startTime, Timestamp endTime){
	// 	this.id 	   = id;
	// 	this.startTime = (Timestamp)startTime;
	// 	this.endTime = (Timestamp)endTime;
	// 	this.length = endTime.getTime() - startTime.getTime();
	// }

	public Event(int id, int startTime, int endTime) {
		this.id = id;
		this.startTime = (Integer)startTime;
		this.endTime = (Integer)endTime;
		this.length = endTime - startTime;

	}

	// any data type is an object - do typecasting
	int getEventID(){
		return id;
	}

	long getLength() {
		return this.length;
	}

// 	Integer getStartTime(){
// 		return this.startTime;
// 	}
//
// Integer getEndTime() {
// 	return this.endTime;
// }

	void setStartTime(int newStartTime){
		this.startTime = newStartTime;
	}

	void setEndTime(int newEndTime){
		this.endTime = newEndTime;
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
