package by.htp.jdom_reflection_homework;

public class ConcreteComponent implements ContainerComponent{

	public void init() {
		System.out.println("init method run");
		
	}

	public String execute(String params) {
		System.out.println("execute:params "+ params);
		return "return string return";
	}

}
