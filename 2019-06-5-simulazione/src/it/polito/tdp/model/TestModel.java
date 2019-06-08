package it.polito.tdp.model;

import java.time.LocalDateTime;

public class TestModel {

	public static void main(String[] args) {
		Simulazione sim = new Simulazione(2015, 6, 8, 6);
		sim.simula();
		System.out.println(sim.getMalGestiti());
		
	}

}
