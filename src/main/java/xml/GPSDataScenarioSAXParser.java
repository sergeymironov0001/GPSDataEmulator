package xml;

import java.text.ParseException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import scenario.ObjectPositionChangeEvent;
import scenario.ScenatioEvent;
import timer.Event;
import timer.EventsTimer;
import timer.Time;

/**
 * Класс, реализующий парсинг xml сценария
 * 
 * @author Mironov
 * 
 */
public class GPSDataScenarioSAXParser extends DefaultHandler {
	EventsTimer eventsTimer;
	String currentObjectID;

	public GPSDataScenarioSAXParser(EventsTimer eventsTimer) {
		super();
		this.eventsTimer = eventsTimer;
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
		Event event = null;
		if (qName.equals("object")) {
			currentObjectID = attributes.getValue("id");
		} else if (qName.equals("changePosition")) {
			event =
					createObjectPositionChangeEvent(currentObjectID, attributes);
		}
		if (event != null) {
			eventsTimer.addEvent(event);
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
	private ScenatioEvent createObjectPositionChangeEvent(String objectID,
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
