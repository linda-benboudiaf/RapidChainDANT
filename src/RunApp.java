import node.App;

public class RunApp {

	public static void main(String[] args) {
		new Thread(new App(3023)).start();
	}

}
