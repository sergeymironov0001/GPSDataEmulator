package timer;

import java.util.Date;

/**
 * Интерфейс события
 * 
 * @author Mironov
 * 
 */
public interface Event {
	/**
	 * Возвращает время начала события
	 * 
	 * @return время начала события
	 */
	public Date getEventStartTime();

	/**
	 * Метод запускающий событие
	 */
	public void startEvent();

	/**
	 * Метод определяющий являются ли события эквивалентными
	 * 
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj);
}
