package blockchain;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.google.gson.GsonBuilder;

import common.SerialStrategy;
import common.Serializable;

public class IdentitySerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Serializable obj) {
		Identity id = (Identity) obj;
		PrivateKey privateKey = id.privateKey;
		PublicKey publicKey = id.publicKey;
 
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		String pub  = x509EncodedKeySpec.getEncoded().toString();
 
		// Store Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		String priv = pkcs8EncodedKeySpec.getEncoded().toString();
		return priv + "%" + pub;
	}

	@Override
	public Serializable unserialize(InputStream str, Serializable target) {
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(path + "/public.key");
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
	
		// Read Private Key.
		File filePrivateKey = new File(path + "/private.key");
		fis = new FileInputStream(path + "/private.key");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
	
		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
	
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
	}

	@Override
	public String ext() {
		return "json";
	}

	@Override
	public Serializable unserialize(String str, Serializable target) {
		return new GsonBuilder()
				.enableComplexMapKeySerialization()
				.create()
				.fromJson(str, target.getClass());
	}


}
