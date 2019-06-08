package it.polito.tdp.model;

public class Vicini {
	District d1;
	District d2;
	Double distanza;
	Model m;
	public Vicini(District d1, District d2) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		m=new Model();
		distanza=m.calcolaDistanza(d1, d2);
	}
	@Override
	public String toString() {
		return "Vicini [d1=" + d1 + ", d2=" + d2 + ", distanza=" + distanza + "]\n";
	}
	public District getD1() {
		return d1;
	}
	public void setD1(District d1) {
		this.d1 = d1;
	}
	public District getD2() {
		return d2;
	}
	public void setD2(District d2) {
		this.d2 = d2;
	}
	public Double getDistanza() {
		return distanza;
	}
	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}
	

}
