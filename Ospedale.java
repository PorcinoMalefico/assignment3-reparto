import java.util.concurrent.PriorityBlockingQueue;

public class Ospedale {
	static int npazienti;
	public static int[] medici = {0,0,0,0,0,0,0,0,0,0};
	public static PriorityBlockingQueue<Paziente> accessq = new PriorityBlockingQueue<Paziente>(100,(a,b) -> b.codice-a.codice);
	
	public Ospedale(int n) {
		Ospedale.npazienti = n;
	}
	
	public static boolean askForAccess(Paziente p) {
		
		if(accessq.isEmpty()) {
			return true;
		} else if(accessq.contains(p) && accessq.peek().equals(p)) {
			return true;
	    } else if(p.codice > accessq.peek().codice) {
			return true;
		} else {
			accessq.add(p);
			return false;
		}
	}
	
	public static void visitDone(Paziente p) {
		synchronized(accessq) {
			if(accessq.contains(p)) {
				accessq.remove(p);
			}
			accessq.notifyAll();
			Ospedale.medici.notifyAll();
		}
	}
}
