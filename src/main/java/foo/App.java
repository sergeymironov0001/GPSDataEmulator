package foo;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import timer.EventsTimer;
import xml.GPSDataScenarioSAXParser;

/**
 * Hello world!
 * 
 */
public class App {

	public void Increment(Short sS) {
		sS = new Short((short) (sS.intValue() + 1));
		// sS = new Short((short) (sS + 1));
		System.out.println(sS);
	}

	public void result(Integer x) {
		Short sX = new Short((short) x.shortValue());
		Increment(sX);
		x = new Integer(123);
		System.out.println("New value is " + sX);
	}

	public static void main(String[] args) {
		EventsTimer eventsTimer = new EventsTimer();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			GPSDataScenarioSAXParser scenarioParser =
					new GPSDataScenarioSAXParser(eventsTimer);

			parser.parse(new File("src/xml/scenarios/scenario.xml"),
					scenarioParser);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		eventsTimer.start();

		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
