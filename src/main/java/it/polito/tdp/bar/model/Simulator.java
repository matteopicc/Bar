package it.polito.tdp.bar.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	//Modello : Lista di tavoli
	private List<Tavolo>tavoli;
	
	//Parametri della simulazione
	private int NUM_EVENTI = 2000;//eventi generati
	private int T_ARRIVO_MAX = 10; //massimo distacco di tempo tra l'arrivo di un gruppo e quello di un altro(tra 1 e 10 minuti)
	private int NUM_PERSONE_MAX = 10; //massimo numero di persone in un gruppo
	private int DURATA_MIN = 60; //tempo minimo di occupazione di un tavolo
	private int DURATA_MAX = 120; //tempo massimo di occupazione di un tavolo
	private double TOLLERANZA_MAX = 0.9;//massima possibilità che un cliente accetti di consumare al bancone invece che andare via se non ci sono tavoli liberi
	private double OCCUPAZIONE_MAX = 0.5;//il tavolo viene assegnato se il gruppo occupa almeno il 50% dei posti disponibili
	
	//Coda degli eventi
	private PriorityQueue<Event>queue;
	
	//Statistiche per ritornare i risultati. Possiamo scegliere fra una serie di variabili nel simulatore o di avere una classe statistiche che metta insieme questi valori
	private Statistiche statistiche;
	
	private void creaTavolo(int quantità, int dimensione) {//crea un singolo tavolo. Va richiamato più volte per creare tutti i tavoli del bar
		for(int i = 0; i<quantità; i++) {
			this.tavoli.add(new Tavolo(dimensione, false));
		}
		
	}
	
	private void creaTavoli() {//metodo per creare i tavoli
		creaTavolo(2,10);// crea 2 tavoli da 10 posti
		creaTavolo(4,8);// crea 4 tavoli da 8 posti
		creaTavolo(4,6);// crea 4 tavoli da 6 posti
		creaTavolo(5,4);// crea 5 tavoli da 4 posti
		
		//ordiniamo i tavoli per dimensione
		Collections.sort(this.tavoli, new Comparator<Tavolo>() {

			@Override
			public int compare(Tavolo o1, Tavolo o2) {
				return o1.getPosti() - o2.getPosti();
			}
			
		});
		
	}
	
	private void creaEventi() {//creaiamo gli eventi 
		Duration arrivo = Duration.ofMinutes(0); // inizio del tempo
		for(int i = 0; i<this.NUM_EVENTI;i++) {
			int nPersone = (int)(Math.random()* NUM_PERSONE_MAX +1); //tirare a caso il numero di persone
			Duration durata = Duration.ofMinutes(this.DURATA_MIN + (int)(Math.random()*(this.DURATA_MAX -this.DURATA_MIN +1 )));
			double tolleranza = (int)(Math.random()* NUM_PERSONE_MAX); // tirare a caso la tolleranza del gruppo
			
			Event e = new Event(arrivo, EventType.ARRIVO_GRUPPO_CLIENTI, nPersone, durata, tolleranza,null);
			this.queue.add(e);//aggiungo l'evento alla coda
			arrivo = arrivo.plusMinutes((int)(Math.random()*this.T_ARRIVO_MAX +1));//aggiungo tempo a caso tra 1 e 10 minuti
		}
	}
	
	
	//Il simulatore ha una parte di inizializzazione e una parte di run
	
	//Parte di inizializazione
	public void init() {
		this.queue = new PriorityQueue<Event>(); // creare la coda
		this.statistiche = new Statistiche();//creare le statistiche
		
		creaTavoli();
		creaEventi();
	}
	
	public void run() {
		//finche .a coda ha eventi, li prendo singolarmente e li eseguo
		while(!queue.isEmpty()) {
			Event e = queue.poll(); // prendo un evento dalla coda
			processaEvento(e);
		}
	}
	
	private void processaEvento(Event e) {
		// se abbiamo eventi di tipologia diversa, la prima cosa da fare è uno switch sulle varie tipologie per dire in che caso siamo
		switch(e.getType()) {
		case ARRIVO_GRUPPO_CLIENTI:
			//conto i clienti totali
			this.statistiche.incrementaClienti(e.getnPersone());
			
			//cerco un tavolo. 2 vincoli: il tavolo piu piccolo che c'è; i clienti occupano almeno il 50% dei posti
			Tavolo tavolo = null; // iniziamo con tavolo = null
			for(Tavolo t : this.tavoli) {
				if(! t.isOccupato() && t.getPosti()>= e.getnPersone() && t.getPosti()*this.OCCUPAZIONE_MAX <= e.getnPersone()) {
					tavolo = t;
					break;
				}
			}
			if(tavolo != null) {
				System.out.format("Torvato un tavolo %d posti per %d persone",tavolo.getPosti(),e.getnPersone());
				statistiche.incrementaClientiSoddisfatti(e.getnPersone());//incrementa clienti soddisfatti
				tavolo.setOccupato(true);//setta il tavolo come occupato
				e.setTavolo(tavolo);//asssegnamo il tavolo all'evento
				//dopo un po i clienti si alzeranno. Creiamo un evento di tipo tavolo liberato con gli stessi parametri di questo
				queue.add(new Event(e.getTime().plus(e.getDurata()), EventType.TAVOLO_LIBERATO, e.getnPersone(), e.getDurata(), e.getTolleranza(), tavolo));
				
			}else {
				//c'è solo il bancone. In base alla tolleranza si vede se rimangono e sono soddisfatti oppure vanno via insoddisfatti
				double bancone = Math.random();
				if(bancone <= e.getTolleranza()) {
					//si,ci fermiamo
					System.out.format("%d persone si fermano al bancone",e.getnPersone());
					statistiche.incrementaClientiSoddisfatti(e.getnPersone());
				}else {
					// no, andiamo via
					System.out.format("%d persone vanno via",e.getnPersone());
					statistiche.incrementaClientiInsoddisfatti(e.getnPersone());
				}
			}
			break;
		case TAVOLO_LIBERATO:
			e.getTavolo().setOccupato(false); // liberiamo il tavolo settando il flag a false 
			break;
		}
	}

}
