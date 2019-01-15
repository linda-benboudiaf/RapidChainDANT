package blockchain;

@SuppressWarnings("serial")
public class Head extends Block {
	protected boolean checked = false;
	protected boolean valid = false;

	public Head(Sentance data, String previousHash) {
		super(data, previousHash);
	}
	
	public Head(Block block) {
		this.overwrite(block);
	}

	public Head() {
	}
	
	public void setChecked() {
		checked = true;
	}
	
	public void setValid() {
		valid = true;
	}
	
	public boolean fullyValid() {
		return valid && checked;
	}
	

}
