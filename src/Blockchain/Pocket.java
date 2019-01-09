package Blockchain;

public class Pocket {

		public static void main(String[] args) {
			
			Block block1 = new Block("Hi im the first block", "0");
			System.out.println("Hash for block 1 : " + block1.hash);
			
			Block block2 = new Block("Yo im the second block",block1.hash);
			System.out.println("Hash for block 2 : " + block2.hash);
			
			Block block3 = new Block("Hey im the third block",block2.hash);
			System.out.println("Hash for block 3 : " + block3.hash);
			
		}
}


