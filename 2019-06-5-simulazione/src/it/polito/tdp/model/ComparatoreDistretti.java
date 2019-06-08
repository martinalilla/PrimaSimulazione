package it.polito.tdp.model;

import java.util.Comparator;

public class ComparatoreDistretti implements Comparator<Vicini> {


	@Override
	public int compare(Vicini v1, Vicini v2) {
		double differenzaDistanze = v1.distanza-v2.distanza;
		/*if(differenzaDistanze>0.0) {
			return (int)(differenzaDistanze);
		}
		else return (int) (-(differenzaDistanze));*/
		return (int)(differenzaDistanze);
	}
	

}
