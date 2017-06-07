/*
 * Event class that defines an event object, allowing for tracking of events
 * through event id's. 
 * @author Carlos Salas Ortega, Baiyu Li, Gianna Wu
 */
public class Event {
	int id,int startTime, int endTime, int length;
	
	public Event(int id, int startTime,int endTime, int length){
		this.id 	   = id; 
		this.startTime = startTime;
		this.endTime   = endTime; 
		this.length    = length;
	}

	int getEventID(){
		return id; 
	}

	int getStartTime(){
		return startTime;
	}

	int getEndTime(){
		return endTime;
	}

	int getLength(){
		return length;
	}


}