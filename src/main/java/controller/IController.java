package controller;

import java.io.File;
import java.util.List;

import controller.exceptions.ControllerException;
import domain.Transport;

/**
 * Интерфейс контроллера
 * 
 * @author Mironov S.V.
 * @since 08.04.2012
 * 
 */
public interface IController {
	public void loadScenario(File scenario) throws ControllerException;

	public void startScenario();

	public void stopScenario();

	public void resetScenario();

	public boolean scenarioIsRunning();

	public List<Transport> getAllTransports();
	
	public void updateTransportPosition(String transportID, double positionX,
			double positionY);
}
