package scenario;

import timer.Time;

/**
 * Событие смены положения объекта
 * 
 * @author Mironov
 * 
 */
public class ObjectPositionChangeEvent extends ScenatioEvent {
	/**
	 * Координата x нового положения объекта
	 */
	private double xCoordinate;
	/**
	 * Координата у нового положения объекта
	 */
	private double yCoordinate;

	public ObjectPositionChangeEvent() {
		super();
	}

	ObjectPositionChangeEvent(String objectID, double xCoordinate,
			double yCoordinate, Time eventStartTime) {
		super(objectID, eventStartTime);
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public double getXCoordinate() {
		return xCoordinate;
	}

	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public void run() {
		System.out.println("Id: " + objectID + " x: " + xCoordinate + " y: "
				+ yCoordinate + " time: " + eventStartTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// стоит ли влючать проверку координат ?
		if (!(obj instanceof ObjectPositionChangeEvent)) {
			return false;
		}
		ObjectPositionChangeEvent event = (ObjectPositionChangeEvent) obj;
		if (this.getObjectID().equals(event.getObjectID())
				&& this.getEventStartTime().equals(
						((ObjectPositionChangeEvent) obj).getEventStartTime())) {
			return true;
		}

		return false;
	}
}
