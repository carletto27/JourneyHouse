package systemJourneyHouse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Startup {

	private Utente u1,u2,u3,u4, u5, u6;
	private Struttura s1, s2, s3;
	private Camera c1, c2, c3, c4, c5;
	private Prenotazione p1, p2, p3, p4;
	private Recensione r1, r2;
	private JourneyHouse JH;
	

	public Startup  (JourneyHouse JH) throws Exception {
		this.JH = JH;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		//Alcuni utenti di esempio
		
		u1 = new Utente ("Journey", "House", "admin@journeyhouse.com", "admin", "password");
		JH.utentiRegistrati.put(u1.getIdUtente(), u1);
		u2 = new Utente ("Carlo" , "Lentini", "carlo@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u2.getIdUtente(), u2);
		u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		u4 = new Utente ("Gianni" , "Morandi", "gianni@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u4.getIdUtente(), u4);
		u5 = new Utente ("Guido" , "Bianchi",  "guido@gmail.com", "gestore", "password");
		JH.utentiRegistrati.put(u5.getIdUtente(), u5);
		u6 = new Utente ("Guido" , "La Vespa", "guido1@gmail.com", "gestore", "password");
		JH.utentiRegistrati.put(u6.getIdUtente(), u6);

		//Alcune strutture di esempio

		s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		s2 = new Struttura("Casa Stella", "Roma", "x1", "3296358794",u5);
		JH.struttureDisponibili.put(s2.getIdStruttura(), s2);
		s3 = new Struttura("Il purtuso", "Assoro", "x2", "3875214569", u6);
		JH.struttureDisponibili.put(s3.getIdStruttura(), s3);


		//Alcune camere di esempio

		c1 = new Camera(s2,101,1,"Matrimoniale", 20.50); 
		JH.camereDisponibili.put(c1.getIdCamera(), c1);
		s2.aggiungiCamera(c1);
		
		c2 = new Camera(s1,202,2,"Doppia", 40.50);
		JH.camereDisponibili.put(c2.getIdCamera(), c2);
		s1.aggiungiCamera(c2);
		
		c3 = new Camera(s1,302,3,"Matrimoniale", 10.50);  
		JH.camereDisponibili.put(c3.getIdCamera(), c3);
		s1.aggiungiCamera(c3);
		
		c4 = new Camera(s3,402,1,"Singola", 20.50);   
		JH.camereDisponibili.put(c4.getIdCamera(), c4);
		s3.aggiungiCamera(c4);
		
		c5 = new Camera (s1, 502, 5, "Matrimoniale", 40.50);  
		JH.camereDisponibili.put(c5.getIdCamera(), c5);
		s1.aggiungiCamera(c5);


		//Alcune prenotazioni di esempio
	
		Date arrivo1 = date.parse("2023-07-11");
		Date partenza1 = date.parse("2023-07-14");

		Date arrivo2 = date.parse("2023-07-12");
		Date partenza2 = date.parse("2023-07-14");

		Date arrivo3 = date.parse("2023-07-12");
		Date partenza3 = date.parse("2023-07-14");

		Date arrivo4 = date.parse("2023-08-16");
		Date partenza4 = date.parse("2023-08-18");
		
		Observer ob = new ObserverPrenotazione ();

		p1 = new Prenotazione(arrivo1, partenza1, c1, 100, u2, true, ob ); 
		JH.prenotazioniEffettuate.put(p1.getIdPrenotazione(), p1);
		c1.aggiungiPrenotazione(p1);
		
		p2 = new Prenotazione(arrivo2, partenza2, c2, 200, u4, false, ob );
		JH.prenotazioniEffettuate.put(p2.getIdPrenotazione(), p2);
		c2.aggiungiPrenotazione(p2);
		
		p3 = new Prenotazione(arrivo3, partenza3, c4, 300, u2, false, ob);
		JH.prenotazioniEffettuate.put(p3.getIdPrenotazione(), p3);
		c4.aggiungiPrenotazione(p3);
		
		p4 = new Prenotazione(arrivo4, partenza4, c3, 400, u4, true, ob); 
		JH.prenotazioniEffettuate.put(p4.getIdPrenotazione(), p4);
		c3.aggiungiPrenotazione(p4);
		
		
		//Alcune recensioni di esempio
		r1 = new Recensione ("Prova" ,3,u4,s1);
		JH.recensioniEffettuate.put(r1.getIdRecensione(), r1);
		s1.aggiungiRecensione(r1);
		r2 = new Recensione ("Prova2" ,4, u4, s2);
		JH.recensioniEffettuate.put(r2.getIdRecensione(), r2);
		s2.aggiungiRecensione(r2);

	}
}
