package blockchain;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.google.gson.GsonBuilder;

import common.Log;
import common.SerialStrategy;
import common.Storable;

public class IdentitySerialStrategy extends SerialStrategy {

	@Override
	public String serialize(Storable obj) {
		Identity id = (Identity) obj;
		PrivateKey privateKey = id.privateKey;
		PublicKey publicKey = id.publicKey;
 
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		String pub  = new String(x509EncodedKeySpec.getEncoded());
 
		// Store Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		String priv = new String(pkcs8EncodedKeySpec.getEncoded());
		Log.debug(priv);
		Log.debug(pub);
		return priv + "%" + pub;
	}

	@Override
	public Storable unserialize(InputStream str, Storable target) {
	
		// Generate KeyPair.
		KeyFactory keyFactory;
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			Reader r = new InputStreamReader(str);
			int i;
			String id;
			StringBuffer buff = new StringBuffer();
			byte[] encodedPublicKey;
			byte[] encodedPrivateKey;
			while ((i = r.read()) != -1) {
				buff.append((char) i);
			}
			id = buff.toString();
			String[] pieces = id.split("%");
			String pubKey = pieces[1];
			String privKey = pieces[0];
			encodedPrivateKey = privKey.getBytes();
			encodedPublicKey = pubKey.getBytes();
			keyFactory = KeyFactory.getInstance("ECDSA", "BC");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					encodedPublicKey);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					encodedPrivateKey);
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			return new Identity(privateKey, publicKey);
		} catch (Exception e) {
			Log.error(e);
			return null;
		}
	}
	
	@Override
	public void serialize(OutputStream os, Storable obj) {
		try {
			String str = new GsonBuilder().setPrettyPrinting().create().toJson(obj);  
			os.write(str.getBytes());
			os.close();			
		}catch(IOException e) {
			Log.error(e);
		}
	}
	@Override
	public String ext() {
		return "json";
	}

	@Override
	public Storable unserialize(String str, Storable target) {
		return new GsonBuilder()
				.enableComplexMapKeySerialization()
				.create()
				.fromJson(str, target.getClass());
	}

}
