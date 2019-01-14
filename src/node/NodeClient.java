package node;

import java.util.Scanner;

import blockchain.Sentance;

public class NodeClient implements Runnable {
	volatile String phrase;

	@Override
	public void run() {
		try (Scanner sc = new Scanner(System.in)) {

			phrase = sc.next();
			Sentance s = new Sentance(phrase); // ajout de la phrase a la DATA du block
			App.phrases.add(s);
			System.out.println(App.phrases.toString()); // affichage de test

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
