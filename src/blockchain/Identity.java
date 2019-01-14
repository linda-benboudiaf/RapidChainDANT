package blockchain;

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

	public static void main(String[] args) {
		Identity i = new Identity();
		System.out.println("Private Key °°°" + i.privateKey);
		System.out.println("Public Key °°°°" + i.publicKey);

	}
}