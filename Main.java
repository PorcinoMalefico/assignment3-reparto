import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Argomenti:
 * int v : numero di pazienti in codice verde   (priorità 0)
 * int g : numero di pazienti in codice giallo  (priorità 1)
 * int r : numero di pazienti in codice rosso   (priorità 2)
 */

public class Main {

	public static void main(String[] args) {
		int tverde = Integer.parseInt(args[0]);
		int tgiallo = Integer.parseInt(args[1]);
		int trosso = Integer.parseInt(args[2]);
		int tot = tverde+tgiallo+trosso;
		
		Ospedale gestore = new Ospedale(tot);
		
		ExecutorService pool = Executors.newFixedThreadPool(tot);
		
		for(int i=0; i<trosso ; i++) {
			pool.execute(new Paziente(2));
		}
		for(int i=0; i<tgiallo ; i++) {
			pool.execute(new Paziente(1));
		}
		for(int i=0; i<tverde ; i++) {
			pool.execute(new Paziente(0));
		}
		
	}

}
