package blockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import common.Storable;

@SuppressWarnings("serial")
public class Identity implements Storable {
	public PrivateKey privateKey;
	public PublicKey publicKey;

	public Identity() {
	}

	public Identity(PrivateKey priv, PublicKey pub) {
		this.privateKey = priv;
		this.publicKey = pub;
	}

	public void generateKeyPair() {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); // 256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			// Set the public and private keys from the keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void load() {
		try {
			FileInputStream fin = new FileInputStream("id_rsa.ser"); // fichier cle privee
			ObjectInputStream ois = new ObjectInputStream(fin);
			privateKey = (PrivateKey) ois.readObject();
			fin = new FileInputStream("id_rsa.pub.ser");// fichier cle publique
			ois = new ObjectInputStream(fin);
			publicKey = (PublicKey) ois.readObject();
			fin.close();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();

		}

	}

	public void save() {
		try {
			FileOutputStream fout = new FileOutputStream("id_rsa.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.privateKey);

			fout = new FileOutputStream("id_rsa.pub.ser");
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this.publicKey);
			fout.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isEmpty() {
		return privateKey == null;
	}

	@Override
	public void overwrite(Serializable obj) {
		Identity i = (Identity) obj;
		privateKey = i.privateKey;
		publicKey = i.publicKey;

	}

	@Override
	public String command() {
		return "id";
	}

}