package node;

import common.Log;

public class PeriodicPulls implements Runnable {
	protected int timeout;
	
	public PeriodicPulls() {
		this(15);
	}

	public PeriodicPulls(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public void run() {
		while(true) {
			PeerTable newPeers = (PeerTable) App.peers.requestAll(new PeerTable());
			Log.debug(newPeers, "periodic pull");
			App.peers.merge(newPeers);
			try {
				App.store.save("peers");
				Thread.sleep(timeout * 1000); // toutes les 15 secondes
			} catch (Exception e) {
				Log.error(e);
			}
			
		}
	}

}
