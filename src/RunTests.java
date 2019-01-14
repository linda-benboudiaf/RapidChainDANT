import node.App;

public class RunTests {

	public static void main(String[] args) {
		new Thread(new App(3023, true)).start();
	}

}
