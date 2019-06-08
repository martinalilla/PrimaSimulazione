package it.polito.tdp.model;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.db.EventsDao;

public class Model {
	private EventsDao dao;
	private SimpleWeightedGraph<District, DefaultWeightedEdge> grafo;
	
	
	
	
	public Model() {
		
		this.dao = new EventsDao();
	}




	public List<Integer> getAnni(){
		
		return dao.anniEventi();
	}
	
	public void creaGrafo(int anno) {
		 grafo = new SimpleWeightedGraph<District, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		 for(int i=1; i<8; i++) {
			 District d = new District(i);
			 dao.calcolaCentro(d, anno);
			 grafo.addVertex(d);
			 
			 
		 }
		 
		 for( District d: grafo.vertexSet()) {
			 for(District d2: grafo.vertexSet()) {
				if(!d.equals(d2) && !grafo.containsEdge(d, d2)) {
				 Graphs.addEdgeWithVertices(grafo, d, d2, calcolaDistanza(d, d2));
				}
			 }
		 }
		 System.out.println(grafo);
	}
	
	public List<District> trovaVicini(District d){
		return Graphs.neighborListOf(grafo, d);
	}
	
	public String listaVicini(District d) {
			List<District> vicini = trovaVicini(d);
			List<Vicini> viciniDistanze = new LinkedList<Vicini>();
			for(District vicino: vicini) {
				Vicini v = new Vicini(d, vicino);
				viciniDistanze.add(v);
			}
			Collections.sort(viciniDistanze, new ComparatoreDistretti());
			
			
		return viciniDistanze.toString();
	}
	public String tuttiVicini() {
		String s="";
		for(District d: grafo.vertexSet()) {
			s+="Lista dei vicini per il distretto "+d.toString()+" (ordinata in base alla distanza crescente)\n"+listaVicini(d).toString();
		}
		return s;
	}
	
	public double calcolaDistanza(District d1, District d2) {
		double distanza = LatLngTool.distance(d1.getCentro(), d2.getCentro(), LengthUnit.KILOMETER);
		return distanza;
	}
	
	public List<Integer> getMesi(){
		List<Integer> mesi=new LinkedList<Integer>();
		for(int i=1; i<=12; i++) {
			mesi.add(i);
		}
		return mesi;
	}
	public List<Integer> getGiorni(){
		List<Integer> giorni=new LinkedList<Integer>();
		for(int i=1; i<=31; i++) {
			giorni.add(i);
		}
		return giorni;
	}
	
	public List<Crimine> caricaCrimini(){
		return dao.listAllEvents();
	}
	
	public District distrettoCentrale(int anno) {
		District d= new District(dao.trovaCentrale(anno));
		dao.calcolaCentro(d, anno);
		return d;
	}
	public List<Crimine> crimineData(int anno, int mese, int giorno){
		return dao.criminiGiorno(giorno, mese, anno);
	}
	public void calcolaCentro(District d, int anno) {
		
		dao.calcolaCentro(d, anno);
	}
	
	
}
