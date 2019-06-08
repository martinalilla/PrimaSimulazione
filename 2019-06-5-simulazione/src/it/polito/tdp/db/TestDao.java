package it.polito.tdp.db;

import it.polito.tdp.model.Crimine;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		for(Crimine e : dao.listAllEvents())
			System.out.println(e);
	}

}
