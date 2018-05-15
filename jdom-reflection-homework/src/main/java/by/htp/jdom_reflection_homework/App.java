package by.htp.jdom_reflection_homework;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class App {
	private static final String FILENAME = "src/main/resources/myconfig.xml";

	public static void main(String[] args) {
		try {

			SAXBuilder parcer = new SAXBuilder();
			FileReader filereader;

			filereader = new FileReader(FILENAME);

			Document rdoc = null;
			try {
				rdoc = parcer.build(filereader);
			} catch (JDOMException | IOException e) {

				e.printStackTrace();
			} // start parse .xml-file

			Element elem = rdoc.getRootElement();

			Class tempClass = Class.forName(elem.getChild("object").getChildText("class"));

			String initMethodName = elem.getChild("object").getChild("initialization").getAttributeValue("method");

			String callMethodName = elem.getChild("object").getChild("call-method").getChildText("name");

			String callParam = elem.getChild("object").getChild("call-method").getChild("params").getChild("param")
					.getAttributeValue("value");

			Object refObj = null;
			refObj = tempClass.newInstance(); // get instance
			// find initialization- method search by cycle
			Method[] methods = tempClass.getDeclaredMethods();

			for (Method method : methods) {

				if (method.getName().equals(initMethodName)) {

					System.out.println(method.getName());

					try {
						method.setAccessible(true);
						method.invoke(refObj);
					
					} catch (IllegalArgumentException | InvocationTargetException e) {

						e.printStackTrace();
					}
				}
			}
			// simple call method by name and parameters

		
			Method callMethod = tempClass.getMethod(callMethodName, String.class);
			callMethod.setAccessible(true);
			callMethod.invoke(refObj, callParam);


		} catch (FileNotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
		}
	}
}
