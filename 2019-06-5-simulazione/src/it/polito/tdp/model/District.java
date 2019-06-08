package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

public class District {
	@Override
	public String toString() {
		return "District [id=" + id + ", " + ", centro=" + centro
				+ "]\n";
	}

	private int id ;
	private LatLng centro ;
	
	public LatLng getCentro() {
		return centro;
	}

	public void setCentro(LatLng centro) {
		this.centro = centro;
	}

	public District(int id) {
		super();
		this.id = id;
		
		//this.centro = New LatLng(0.0,0.0) ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		District other = (District) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
