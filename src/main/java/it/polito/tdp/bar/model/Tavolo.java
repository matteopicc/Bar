package it.polito.tdp.bar.model;

public class Tavolo { // importante per tenere traccia dei tavoli occupati o da assegnare
	
	private int posti; //posti che il tavolo contiene
	private boolean occupato; // flag che ci indica se il tavolo è occupato o no
	
	//costruttore
	public Tavolo(int posti, boolean occupato) {
		super();
		this.posti = posti;
		this.occupato = occupato;
	}
	
	//getter e setter
	public int getPosti() {
		return posti;
	}
	
	public void setPosti(int posti) {
		this.posti = posti;
	}
	public boolean isOccupato() {
		return occupato;
	}
	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
	
	

}
