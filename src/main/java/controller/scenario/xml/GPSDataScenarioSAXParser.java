package controller.scenario.xml;

import java.text.ParseException;
import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import controller.scenario.ObjectPositionChangeEvent;
import controller.scenario.ScenarioEvent;
import controller.timer.Time;

/**
 * Класс, реализующий парсинг xml сценария
 * 
 * @author Mironov
 * 
 */
public class GPSDataScenarioSAXParser extends DefaultHandler {
	// EventsTimer eventsTimer;
	Collection<ScenarioEvent> eventsCollection;
	String currentObjectID;

	public GPSDataScenarioSAXParser(Collection<ScenarioEvent> eventsCollection) {
		super();
		this.eventsCollection = eventsCollection;
	}

	public Collection<ScenarioEvent> getEvents() {
		return eventsCollection;
	}

	public void setEvents(Collection<ScenarioEvent> events) {
		this.eventsCollection = events;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("Start parse xml...");
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("Finish parse xml...");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		ScenarioEvent event = null;
		if (qName.equals("object")) {
			currentObjectID = attributes.getValue("id");
		} else if (qName.equals("changePosition")) {
			event = createObjectPositionChangeEvent(currentObjectID, attributes);
		}
		if (event != null) {
			getEvents().add(event);
			// eventsTimer.addEvent(event);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
	}

	/**
	 * Создает событие смены положения объекта
	 * 
	 * @param objectID
	 *            - идентификатор объекта, для которого создается событие
	 * @param positionChangeTegAttributes
	 *            - атрибуты xml тега, описывающего событие
	 * @return Событие смены положения объекта
	 */
	private ScenarioEvent createObjectPositionChangeEvent(String objectID,
			Attributes positionChangeTegAttributes) {
		ObjectPositionChangeEvent tmpEvent = null;
		try {
			tmpEvent = new ObjectPositionChangeEvent();
			tmpEvent.setObjectID(objectID);
			tmpEvent.setXCoordinate(Double
					.parseDouble(positionChangeTegAttributes.getValue("x")));
			tmpEvent.setYCoordinate(Double
					.parseDouble(positionChangeTegAttributes.getValue("y")));
			tmpEvent.setEventStartTime(Time.parse(positionChangeTegAttributes
					.getValue("time")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tmpEvent;
	}
}
