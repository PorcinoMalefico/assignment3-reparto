
public class Paziente implements Runnable {

	int k = (int) (Math.random()*10)+1;
	int reparto = (int) Math.floor(Math.random())*10;
	public int codice;

	public Paziente(int codice) {
		this.codice = codice;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" con priorità "+codice+" avviato con "+k+" accessi");
		
		while(k>0) {
			try {
				Thread.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName()+ " con priorità "+codice+": accesso "+k+" avviato");
			
			while(!Ospedale.askForAccess(this)) {
				try {
					Ospedale.accessq.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			synchronized(Ospedale.medici){
				switch(codice) {
					case 0:
						boolean free = false;
						int i=0;
						while(!free) {
							i = 0;
							while(!free && i<10) {
								if(Ospedale.medici[i] == 0) {
									free = true;
									Ospedale.medici[i] = 1;
								}
								i++;
							}
							if(!free) {
								try {
									Ospedale.medici.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
						try {
							Thread.sleep((long) (Math.random()*1000));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Ospedale.medici[i-1] = 0;
						
						Ospedale.visitDone(this);
						break;
				case 1:
					while(Ospedale.medici[reparto]!=0) {
						try {
							Ospedale.medici.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					Ospedale.medici[reparto] = 1;
					
					try {
						Thread.sleep((long) (Math.random()*1000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Ospedale.medici[reparto] = 0;
					
					Ospedale.visitDone(this);
					break;
				case 2:
					for(int j=0 ; j<10 ; j++) {
						while(Ospedale.medici[j] != 0) {
							try {
								Ospedale.medici.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						Ospedale.medici[j] = 1;
					}
					
					
					for(int j=0 ; j<10 ; j++) {
						Ospedale.medici[j] = 0;
					}
					
					Ospedale.visitDone(this);
					break;
				}
			}
			
			k--;
			System.out.println(Thread.currentThread().getName()+ " con priorità "+codice+": "+k+" accessi rimasti");
		}
		
		System.out.println(Thread.currentThread().getName()+ " con priorità "+codice+" terminato");
	}

}


