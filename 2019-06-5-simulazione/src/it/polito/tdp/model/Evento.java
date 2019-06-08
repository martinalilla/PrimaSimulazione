package it.polito.tdp.model;

import java.time.LocalDateTime;

public class Evento implements Comparable <Evento>{
	public enum tipoEvento{
		CRIME,
		OTHERCRIME,
		LIBERA_AGENTE, 
	};

	public tipoEvento tipoEvento;
	
	private LocalDateTime time;
	private Crimine crimine;

	public Evento(String s, LocalDateTime time, Crimine crimine) {
		if(s=="all_other_crimes") {
			tipoEvento=tipoEvento.OTHERCRIME;
		} if(s=="libera"){
			tipoEvento=tipoEvento.LIBERA_AGENTE;
		}else {
			
		
			tipoEvento=tipoEvento.CRIME;
		}
		
		this.time = time;
		this.crimine = crimine;
	}

	public tipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String s) {
		if(s=="all_other_crimes") {
			tipoEvento=tipoEvento.OTHERCRIME;
		} else tipoEvento=tipoEvento.CRIME;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTimeStamp(LocalDateTime time) {
		this.time = time;
	}

	public Crimine getCrimine() {
		return crimine;
	}

	public void setCrimine(Crimine crimine) {
		this.crimine = crimine;
	}

	@Override
	public int compareTo(Evento e) {
		
		return this.getTime().compareTo(e.getTime());
	}

	@Override
	public String toString() {
		return "Evento [tipoEvento=" + tipoEvento + ", time=" + time + ", crimine=" + crimine + "]";
	}

}
