package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.IModel;

import org.xml.sax.SAXException;

import controller.exceptions.ControllerException;
import controller.scenario.ObjectPositionChangeEvent;
import controller.scenario.ScenarioEvent;
import controller.scenario.xml.GPSDataScenarioSAXParser;
import controller.timer.EventsTimer;
import domain.Point;
import domain.Transport;

/**
 * Реализация контроллера
 * 
 * @author Mironov S.V.
 * @since 08.04.2012
 * 
 */
public class Controller implements IController {
	private EventsTimer eventsTimer;
	private IModel model;

	public Controller() {
		setEventsTimer(new EventsTimer());
	}

	public IModel getModel() {
		return model;
	}

	public void setModel(IModel model) {
		this.model = model;
	}

	public void loadScenario(File scenario) throws ControllerException {
		if (scenarioIsRunning()) {
			throw new ControllerException("Please stop scenario first!");
		}
		getEventsTimer().clearEvents();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		GPSDataScenarioSAXParser scenarioParser = new GPSDataScenarioSAXParser(
				new ArrayList<ScenarioEvent>());
		try {
			parser = factory.newSAXParser();
			parser.parse(scenario, scenarioParser);
			for (ScenarioEvent event : scenarioParser.getEvents()) {
				ObjectPositionChangeEvent objectPositionChangeEvent = (ObjectPositionChangeEvent) event;
				objectPositionChangeEvent.setController(this);
				getEventsTimer().addEvent(objectPositionChangeEvent);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startScenario() {
		getEventsTimer().start();
	}

	public void stopScenario() {
		getEventsTimer().stop();
	}

	public void resetScenario() {
		getEventsTimer().reset();
	}

	public boolean scenarioIsRunning() {
		return getEventsTimer().isRunning();
	}

	private EventsTimer getEventsTimer() {
		return eventsTimer;
	}

	private void setEventsTimer(EventsTimer eventsTimer) {
		this.eventsTimer = eventsTimer;
	}

	synchronized public void updateTransportPosition(String transportID,
			double positionX, double positionY) {
		Transport transport = getModel().getTransport(transportID);
		if (transport != null) {
			transport.setPosition(new Point(positionX, positionY));
			getModel().saveTransport(transport);
			System.out.println("Object with id:" + transport.getNumber()
					+ " move to: " + positionX + " , " + positionY);
		}
	}

	public List<Transport> getAllTransports() {
		return getModel().getAllTransports();
	}
}
