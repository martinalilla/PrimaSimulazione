package it.polito.tdp.model;

public class Agente {
	private int id;
	private District doveSiTrova;
	private boolean libero;
	
	public Agente(int id, District doveSiTrova) {
		super();
		this.id = id;
		this.doveSiTrova = doveSiTrova;
		libero=true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public District getDoveSiTrova() {
		return doveSiTrova;
	}

	public void setDoveSiTrova(District doveSiTrova) {
		this.doveSiTrova = doveSiTrova;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}
	

}
