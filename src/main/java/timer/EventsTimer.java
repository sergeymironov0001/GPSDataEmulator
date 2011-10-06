package timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.Timer;

/**
 * Класс таймера, запускающего события согласно времени их начала
 * 
 * @author Mironov
 * 
 */
public class EventsTimer {
	/**
	 * Очередь прошедших событий
	 */
	private SortedSet<Event> pastEvents;
	/**
	 * Очередь будущих событий событий
	 */
	private SortedSet<Event> futureEvents;

	/**
	 * Текущее время таймера
	 */
	private Time currentTime;
	/**
	 * Время возникновения следующего события
	 */
	private Time nextStartEventTime;

	/**
	 * Таймер отсчитывающий время
	 */
	private Timer timer;

	public EventsTimer() {
		EventsComparator eventsComparator = new EventsComparator();
		pastEvents = new TreeSet<Event>(eventsComparator);
		futureEvents = new TreeSet<Event>(eventsComparator);
		currentTime = new Time();
		nextStartEventTime = new Time();
		timer = new Timer(Time.MILLISECONDS_IN_SECOND, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTime();
			}
		});

	}

	public Set<Event> getPastEvents() {
		return pastEvents;
	}

	public Set<Event> getFutureEvents() {
		return futureEvents;
	}

	public Time getCurrentTime() {
		return currentTime;
	}

	public Time getNextStartEventTime() {
		return nextStartEventTime;
	}

	synchronized public boolean addEvent(Event event) {
		if (!isRunning()) {
			if (event.getEventStartTime().compareTo(currentTime) < 0) {
				pastEvents.add(event);
			} else {
				futureEvents.add((event));
				// Если событие заняло первое место в очереди на выполнение,
				// то изменяем время запуска новго события
				if (futureEvents.first().equals(event)) {
					nextStartEventTime = event.getEventStartTime();
				}
			}
			return true;
		}
		return false;
	}

	synchronized public boolean removeEvent(Event event) {
		if (!isRunning()) {
			if (pastEvents.contains(event)) {
				pastEvents.remove(event);
				return true;
			}
			if (futureEvents.contains(event)) {
				futureEvents.remove(event);
				nextStartEventTime =
						futureEvents.isEmpty() ? futureEvents.first()
								.getEventStartTime() : new Time();
			}
		}
		return false;
	}

	synchronized public boolean clearEvents() {
		if (!isRunning()) {
			futureEvents.clear();
			pastEvents.clear();
			nextStartEventTime = new Time();
		}
		return false;
	}

	synchronized public void start() {
		if (!isRunning()) {
			timer.start();
		}
	}

	synchronized public void stop() {
		if (isRunning()) {
			timer.stop();
		}
	}

	synchronized public void reset() {
		currentTime.setHours(0);
		currentTime.setMinutes(0);
		currentTime.setSeconds(0);

		futureEvents.addAll(pastEvents);
		pastEvents.clear();
	}

	public boolean isRunning() {
		return timer.isRunning();
	}

	synchronized private void updateTime() {
		currentTime.addSeconds(1);
		checkEvents();
	}

	/**
	 * Проверяет есть ли событие, время запуска которого уже подошло, если такое
	 * есть, то запускает его
	 */
	private void checkEvents() {
		if (currentTime.equals(nextStartEventTime)) {
			Event currentEvent = futureEvents.first();
			futureEvents.remove(currentEvent);
			pastEvents.add(currentEvent);
			new Thread(currentEvent).start();
			nextStartEventTime = futureEvents.first().getEventStartTime();
			checkEvents();
		}
	}

	/**
	 * Класс, необходимый для того чтобы расположить события в множестве событий
	 * в хронологическом порядке
	 * 
	 * @author Mironov
	 * 
	 */
	private class EventsComparator implements Comparator<Event> {

		public int compare(Event o1, Event o2) {
			return o1.getEventStartTime().compareTo(o2.getEventStartTime());
		}
	}
}
