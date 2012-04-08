package controller.timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

/**
 * Класс таймера, запускающего события согласно времени их начала
 * 
 * @author Mironov
 * 
 */

// TODO обозреватели событий не оповещаются о событиях
public class EventsTimer {
	private List<IEventsObserver> observers;
	/**
	 * Очередь прошедших событий
	 */
	private List<IEvent> pastEvents;
	/**
	 * Очередь будущих событий событий
	 */
	private List<IEvent> futureEvents;

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
		pastEvents = new LinkedList<IEvent>();
		futureEvents = new LinkedList<IEvent>();
		currentTime = new Time();
		observers = new ArrayList<IEventsObserver>();
		nextStartEventTime = new Time();
		timer = new Timer(Time.MILLISECONDS_IN_SECOND, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTime();
			}
		});

	}

	public List<IEvent> getPastEvents() {
		return pastEvents;
	}

	public List<IEvent> getFutureEvents() {
		return futureEvents;
	}

	public Time getCurrentTime() {
		return currentTime;
	}

	public Time getNextStartEventTime() {
		return nextStartEventTime;
	}

	public List<IEventsObserver> getObservers() {
		return observers;
	}

	public void setObservers(List<IEventsObserver> observers) {
		this.observers = observers;
	}

	public void addObserver(IEventsObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(IEventsObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	private void notifyObservers(IEvent event) {
		for (IEventsObserver observer : observers) {
			observer.update(event);
		}
	}

	synchronized public boolean addEvent(IEvent event) {
		if (!isRunning()) {
			if (event.getEventStartTime().compareTo(currentTime) < 0) {
				pastEvents.add(event);
				Collections.sort(pastEvents, new EventsComparator());
			} else {
				futureEvents.add((event));
				// Если событие заняло первое место в очереди на выполнение,
				// то изменяем время запуска новго события
				if (futureEvents.get(0).equals(event)) {
					nextStartEventTime = event.getEventStartTime();
				}
				Collections.sort(futureEvents, new EventsComparator());
			}

			return true;
		}
		return false;
	}

	synchronized public boolean removeEvent(IEvent event) {
		if (!isRunning()) {
			if (pastEvents.contains(event)) {
				pastEvents.remove(event);
				return true;
			}
			if (futureEvents.contains(event)) {
				futureEvents.remove(event);
				nextStartEventTime = futureEvents.isEmpty() ? futureEvents.get(
						0).getEventStartTime() : new Time();
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
		Collections.sort(futureEvents, new EventsComparator());
		pastEvents.clear();
	}

	public boolean isRunning() {
		return timer.isRunning();
	}

	synchronized private void updateTime() {
		currentTime.addSeconds(1);
		System.out.println(currentTime);
		checkEvents();
	}

	/**
	 * Проверяет есть ли событие, время запуска которого уже подошло, если такое
	 * есть, то запускает его
	 */
	private void checkEvents() {
		if (currentTime.equals(nextStartEventTime) && !futureEvents.isEmpty()) {
			IEvent currentEvent = futureEvents.get(0);
			futureEvents.remove(currentEvent);
			pastEvents.add(currentEvent);
			new Thread(currentEvent).start();
			// TODO добавить оповещение наблюдателей событий
			if (!futureEvents.isEmpty()) {
				nextStartEventTime = futureEvents.get(0).getEventStartTime();
			}

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
	private class EventsComparator implements Comparator<IEvent> {

		public int compare(IEvent o1, IEvent o2) {
			return o1.getEventStartTime().compareTo(o2.getEventStartTime());
		}
	}
}
