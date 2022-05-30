package it.polito.tdp.bar.model;

public class Statistiche { //risultati che vogliamo ottenere svolgendo la simulazione
	private int clientiTOT;
	private int clientiSoddisfatti;
	private int clientiInsoddisfatti;
	
	//Costruttore
	public Statistiche() {
		super();
		this.clientiTOT = 0;
		this.clientiSoddisfatti = 0;
		this.clientiInsoddisfatti = 0;
		//No parametri tra parentesi perche all'inizio non li conosciamo. 
		//Inizializziamo tutto a zero e man mano incrementiamo con metodi di set o metodi di incremento(meglio)
	}
	
	public void incrementaClienti(int n) {
		this.clientiTOT += n;
	}
	
	public void incrementaClientiSoddisfatti(int n) {
		this.clientiSoddisfatti += n;
	}
	
	public void incrementaClientiInsoddisfatti(int n) {
		this.clientiInsoddisfatti += n;
	}
	
	//getter
	public int getClientiTOT() {
		return clientiTOT;
	}

	public int getClientiSoddisfatti() {
		return clientiSoddisfatti;
	}

	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}
	
	
	
	
	
	
	

}
