package timer;

/**
 * Интерфейс события
 * 
 * @author Mironov
 * 
 */
public interface Event extends Runnable {
	/**
	 * Возвращает время начала события
	 * 
	 * @return время начала события
	 */
	public Time getEventStartTime();
}
