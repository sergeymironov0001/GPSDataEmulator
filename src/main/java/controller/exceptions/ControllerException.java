package controller.exceptions;

/**
 * Класс описывающий исключения в работе котроллера
 * 
 * @author Mironov S.V.
 * @since 08.04.2012
 */
public class ControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4055771557472070147L;

	public ControllerException(String message) {
		super(message);
	}

}
