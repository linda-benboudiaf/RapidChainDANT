package blockchain;
import java.security.*;
import com.google.gson.*;
public class Hash {
	//On applique un hash type SHA256 sur une chaine de caractère. 
	public static String ApplyHash(String input) {
		//Lever l'exception.
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8")); 
			StringBuffer hexString = new StringBuffer(); 
			for(int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]); 
				if (hex.length() == 1) {hexString.append(0);}
					hexString.append(hex); 
			}
			return hexString.toString(); 
		}
		catch(Exception e) {
			throw new RuntimeException(e); 
		}
	}
	public static String getJson(Object o) {
		// Création objet JSON
		return new GsonBuilder().setPrettyPrinting().create().toJson(o);
	}
	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}	
}