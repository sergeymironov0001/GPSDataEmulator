package controller.timer;

/**
 * Интерфейс события
 * 
 * @author Mironov
 * 
 */
public interface IEvent extends Runnable {

	/**
	 * Возвращает время начала события
	 * 
	 * @return время начала события
	 */
	public Time getEventStartTime();
}
