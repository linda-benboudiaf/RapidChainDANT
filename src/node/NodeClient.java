package node;

import java.util.Scanner;

import blockchain.Sentance;

public class NodeClient implements Runnable {

	@Override
	public void run() {
		try (Scanner sc = new Scanner(System.in)) {

			String phrase = sc.nextLine();
			Sentance s=new Sentance(App.id.publicKey, phrase); // ajout de la phrase a la DATA du block
			App.phrases.add(s);
		}
		new Thread(this).start();
	}

}
