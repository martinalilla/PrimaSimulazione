package it.polito.tdp.model;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import it.polito.tdp.model.Evento.tipoEvento;

public class Simulazione {
	private Random rd;
	private int malGestiti;
	private Queue<Evento> queue;
	private List<Crimine> crimini;
	private District centrale;
	private Model model;
	private int agenti;
	private List<Evento> listaEventi;
	private List<Agente> listaAgenti;
	private int anno;
	private HashMap <Crimine, Agente> associazioni;
	
	
	public int getMalGestiti() {
		return malGestiti;
	}

	public void setMalGestiti(int malGestiti) {
		this.malGestiti = malGestiti;
	}

	public Simulazione(int anno, int mese, int giorno, int agenti) {
		model=new Model();
		malGestiti = 0;
		queue = new PriorityQueue<Evento>();
		crimini = model.crimineData(anno, mese, giorno);
		centrale=model.distrettoCentrale(anno);
		this.agenti=agenti;
		listaEventi=new LinkedList<Evento>();
		listaAgenti=new LinkedList<Agente>();
		for(int i=0; i<agenti; i++) {
			Agente a=new Agente(i, centrale);
			listaAgenti.add(a);
		}
		associazioni=new HashMap<Crimine, Agente>();
		this.anno=anno;
		
	}
	
	public void addEvent(Evento e) {
		queue.add(e);
	}
	
	public void simula() {
		for(Crimine c:crimini) {
			Evento e = new Evento(c.getOffense_category_id(), c.getReported_date(), c);
			queue.add(e);
		}
		queue.addAll(listaEventi);
		while (!queue.isEmpty()) {
			run();
		}
	}

	private void run() {
		Evento ev = queue.poll();
		System.out.println(ev);
		Agente scelto=listaAgenti.get(0);
		double distanzaMinima;
		District d=new District(ev.getCrimine().getDistrict_id());
		model.calcolaCentro(d, anno);
		distanzaMinima=model.calcolaDistanza(listaAgenti.get(0).getDoveSiTrova(), d );
		for(Agente a: listaAgenti) {
			if(model.calcolaDistanza(a.getDoveSiTrova(), d)<=distanzaMinima) {
				if(a.isLibero()==true) {
				distanzaMinima=model.calcolaDistanza(a.getDoveSiTrova(), d);
				scelto=a;
				}
			}
		}
		double minuti =distanzaMinima/3600;
		
		switch (ev.getTipoEvento()) {
		case CRIME:
			
			if(minuti<=15) {
				scelto.setDoveSiTrova(d);
				scelto.setLibero(false);
				associazioni.put(ev.getCrimine(), scelto);
				//listaAgenti.remove(scelto);
				Evento e=new Evento("libera", ev.getTime().plusMinutes(120), ev.getCrimine());
				queue.add(e);
			}
			if(minuti>15) {
				malGestiti++;
				scelto.setDoveSiTrova(d);
				scelto.setLibero(false);
				associazioni.put(ev.getCrimine(), scelto);
				//listaAgenti.remove(scelto);
				Evento e=new Evento("libera", ev.getTime().plusMinutes(120), ev.getCrimine());
				queue.add(e);
				
			}
			
			break;
		case OTHERCRIME: 
			Random random = new Random();
			int num = random.nextInt(2)+1 ;
			if(num==2) {
				if(minuti<=15) {
					scelto.setDoveSiTrova(d);
					scelto.setLibero(false);
					associazioni.put(ev.getCrimine(), scelto);
					listaAgenti.remove(scelto);
					Evento e=new Evento("libera", LocalDateTime.now().plusMinutes(120), ev.getCrimine());
					queue.add(e);
				}
				if(minuti>15) {
					malGestiti++;
					scelto.setDoveSiTrova(d);
					scelto.setLibero(false);
					associazioni.put(ev.getCrimine(), scelto);
					listaAgenti.remove(scelto);
					Evento e=new Evento("libera", LocalDateTime.now().plusMinutes(120), ev.getCrimine());
					queue.add(e);
					
				}
				if(num==1) {
					if(minuti<=15) {
						scelto.setDoveSiTrova(d);
						scelto.setLibero(false);
						associazioni.put(ev.getCrimine(), scelto);
						listaAgenti.remove(scelto);
						Evento e=new Evento("libera", LocalDateTime.now().plusMinutes(60), ev.getCrimine());
						queue.add(e);
					}
					if(minuti>15) {
						malGestiti++;
						scelto.setDoveSiTrova(d);
						scelto.setLibero(false);
						associazioni.put(ev.getCrimine(), scelto);
						listaAgenti.remove(scelto);
						Evento e=new Evento("libera", LocalDateTime.now().plusMinutes(60), ev.getCrimine());
						queue.add(e);
						
					}
					
				}
				
			}
			
			break;
			
		case LIBERA_AGENTE:
			Agente a=associazioni.get(ev.getCrimine());
			a.setLibero(true);
			//listaAgenti.add(a);
			break;
		
	}


	

	

}
}
