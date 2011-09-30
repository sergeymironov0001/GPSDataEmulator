package timer;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс таймера, запускающего события согласно времени их начала
 * 
 * @author Mironov
 * 
 */
public class EventsTimer {
	/**
	 * Список событий
	 */
	private List<Event> events;

	public EventsTimer() {
		events = new LinkedList<Event>();
	}

	public void start() {

	}

	public void stop() {

	}

	public List<Event> getEvents() {
		return events;
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public void removeEvent(Event event) {
		events.remove(event);
	}

	public void clearEvents() {
		events.clear();
	}
}
