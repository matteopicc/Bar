package it.polito.tdp.bar.model;

import java.time.Duration;

public class Event implements Comparable<Event> {
	//definiamo i tipi di eventi che descrivono la situazione
	public enum EventType{
		ARRIVO_GRUPPO_CLIENTI, //clienti arrivano
		TAVOLO_LIBERATO //clienti se ne vanno
	}
	
	//definiamo le caratteristiche di ogni singolo evento
	private EventType type; //attributo del singolo evento
	private Duration time; //più semplice di usare le date. Parte da 0 e va avanti
	private int nPersone; //persone che entrano
	private Duration durata; //tempo in cui  clienti occupano il tavolo
	private double tolleranza; // probabilità con cui il cliente accetta di consumare al bancone se il tavolo non c'è
	private Tavolo tavolo; // elemento di classe tavolo associato a ogni evento
	
	//costruttore
	public Event( Duration time,EventType type, int nPersone, Duration durata, double tolleranza, Tavolo tavolo) {
		super();
		this.type = type;
		this.time = time;
		this.nPersone = nPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tavolo = tavolo;
	}
	
	//getter e setter 
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public int getnPersone() {
		return nPersone;
	}

	public void setnPersone(int nPersone) {
		this.nPersone = nPersone;
	}

	public Duration getDurata() {
		return durata;
	}

	public void setDurata(Duration durata) {
		this.durata = durata;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	@Override
	public int compareTo(Event o) {
      return this.time.compareTo(o.getTime());//confrontare gli elementi in ordine cronologico
	}
	
	
	
	
	
	
	

}
