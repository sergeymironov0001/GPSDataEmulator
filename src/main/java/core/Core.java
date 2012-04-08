package core;

import java.io.File;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.IController;
import controller.exceptions.ControllerException;

/**
 * Hello world!
 * 
 */
public class Core {
	private static ApplicationContext applicationContext;
	private static final String SERVICES_XML = "services.xml";
	private static final String SCENARIO = "src/xml/scenarios/scenario.xml";
	private IController controller;

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

	}

	public void start() {
		applicationContext = new ClassPathXmlApplicationContext(
				new String[] { SERVICES_XML });

		controller = applicationContext
				.getBean("controller", IController.class);

		// for (Transport transport : controller.getAllTransports()) {
		// System.out.println(transport.getNumber());
		// }
		//
		File scenario = new File(SCENARIO);
		try {
			if (scenario.exists()) {
				controller.loadScenario(scenario);
				controller.startScenario();
			}
		} catch (ControllerException e) {
			e.printStackTrace();
		}

		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
