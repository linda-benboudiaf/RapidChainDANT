package common;

import java.io.Serializable;

public interface Storable extends Serializable {
	
	void overwrite(Serializable obj);
	boolean isEmpty();
	String command();
}
