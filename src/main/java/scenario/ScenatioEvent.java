package scenario;

import timer.Event;
import timer.Time;

/**
 * Абстрактный класс, описывающий событие сценария
 * 
 * @author Mironov
 * 
 */
public abstract class ScenatioEvent implements Event {
	/**
	 * Идентификатор объекта
	 */
	protected String objectID;

	/**
	 * Время начала события
	 */
	protected Time eventStartTime;

	ScenatioEvent() {
		objectID = new String();
		eventStartTime = new Time();
	}

	ScenatioEvent(String objectID, Time eventStartTime) {
		this.objectID = objectID;
		this.eventStartTime = eventStartTime;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public Time getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Time eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public abstract void run();

	// @Override
	// public abstract boolean equals(Object obj);
}
