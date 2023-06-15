package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import systemJourneyHouse.Camera;
import systemJourneyHouse.JourneyHouse;
import systemJourneyHouse.LoginRegGestReportHandler;
import systemJourneyHouse.Observer;
import systemJourneyHouse.ObserverPrenotazione;
import systemJourneyHouse.Prenotazione;
import systemJourneyHouse.Struttura;
import systemJourneyHouse.Utente;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TEST JOURNEYHOUSE")

public class JourneyHouseTests {
	JourneyHouse JH = new JourneyHouse();
	LoginRegGestReportHandler LRGH  = new LoginRegGestReportHandler (JH);

	@Test
	@Order(0)
	@DisplayName("Test Costruttore")
	public void testJourneyHouse() throws Exception{
		assertNotNull(JH); 
	}


	@Test
	@Order(1)
	@DisplayName("Test ricerca camera")
	public void testRicercaCamera() throws Exception {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u4 = new Utente ("Guido" , "Bianchi", "mario@gmail.com", "gestore", "password");
		Utente u5 = new Utente ("Gianni" , "L'attore", "gianni@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u4.getIdUtente(), u4);
		JH.utentiRegistrati.put(u5.getIdUtente(), u5);
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		Struttura s3 = new Struttura("Il purtuso", "Assoro", "x2", "3875214569", u4);
		JH.struttureDisponibili.put(s3.getIdStruttura(), s3);

		Camera c2 = new Camera(s1,202,2,"Doppia", 40.50);
		JH.camereDisponibili.put(c2.getIdCamera(), c2);
		s1.aggiungiCamera(c2);
		Camera c3 = new Camera(s1,302,3,"Matrimoniale", 10.50);  
		JH.camereDisponibili.put(c3.getIdCamera(), c3);
		s1.aggiungiCamera(c3);
		Camera c4 = new Camera(s3,402,1,"Singola", 20.50);   
		JH.camereDisponibili.put(c4.getIdCamera(), c4);
		s3.aggiungiCamera(c4);
		Camera c5 = new Camera (s1, 502, 5, "Matrimoniale", 40.50);  
		JH.camereDisponibili.put(c5.getIdCamera(), c5);
		s1.aggiungiCamera(c5);

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date arrivo2 = date.parse("2023-07-12");
		Date partenza2 = date.parse("2023-07-14");

		Date arrivo3 = date.parse("2023-07-12");
		Date partenza3 = date.parse("2023-07-14");

		Date arrivo4 = date.parse("2023-08-16");
		Date partenza4 = date.parse("2023-08-18");

		Observer ob = new ObserverPrenotazione ();
		Prenotazione p2 = new Prenotazione(arrivo2, partenza2, c2, 200, u5, false, ob);
		JH.prenotazioniEffettuate.put(p2.getIdPrenotazione(), p2);
		c2.aggiungiPrenotazione(p2);
		Prenotazione p3 = new Prenotazione(arrivo3, partenza3, c4, 300,u5, false, ob);
		JH.prenotazioniEffettuate.put(p3.getIdPrenotazione(), p3);
		c4.aggiungiPrenotazione(p3);
		Prenotazione p4 = new Prenotazione(arrivo4, partenza4, c3, 400,u5, true, ob); 
		JH.prenotazioniEffettuate.put(p4.getIdPrenotazione(), p4);
		c3.aggiungiPrenotazione(p4);

		LRGH.effettuaRegistrazioneCliente("Carlo" , "Lentini", "carlo@gmail.com", "password");
		Date dataArrivo = date.parse("2023-07-12");
		Date dataPartenza = date.parse("2023-07-14");
		int size = JH.ricercaCamera("Roma", dataArrivo, dataPartenza,"Matrimoniale").size();
		//Le camere disponibili per il periodo selezionato sono 2 e sono le camere 502 e 302
		assertEquals(2,size);
	}


	@Test
	@Order(2)
	@DisplayName("Test scelta camera e pagamento online")
	public void testFinePrenotazione() throws Exception{
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		Camera c2 = new Camera(s1,202,2,"Doppia", 40.50);
		JH.camereDisponibili.put(c2.getIdCamera(), c2);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date dataArrivo = date.parse("2023-07-12");
		Date dataPartenza = date.parse("2023-07-14");

		LRGH.effettuaRegistrazioneCliente("Carlo" , "Lentini", "carlo@gmail.com", "password");
		JH.sceltaCamera(dataArrivo, dataPartenza, c2.getIdCamera());
		long numeroCartaCredito = 379354508162306L;
		boolean response = JH.pagamentoCarta(numeroCartaCredito);
		assertTrue(response);
	}


	@Test
	@Order(3)
	@DisplayName("Test inserisci camera")
	public void testInserisciCamera() {
		LRGH.effettuaRegistrazioneGestore("Mario", "Rossi", "mario@gmail.com", "password", "Hotel Europa", "Parigi", "Via Roma", "3211234567");
		boolean response = JH.inserisciCamera(503, 5, "Matrimoniale", 20.5);
		assertTrue(response);
	}


	@Test
	@Order(4)
	@DisplayName("Test modifica camera")
	public void testModificaCamera() throws Exception {
		LRGH.effettuaRegistrazioneGestore("Mario", "Rossi", "mario@gmail.com", "password", "Hotel Europa", "Parigi", "Via Roma", "3211234567");

		int idStruttura = -1;

		for (Map.Entry<Integer,Struttura> entry : JH.struttureDisponibili.entrySet()) {
			boolean response = entry.getValue().verificaIdStrutturaByIdGestore(JH.utenteCorrente.getIdUtente());
			if(response == true) {
				idStruttura = entry.getKey();
			}
		}
		Struttura str = JH.struttureDisponibili.get(idStruttura);
		Camera c1 = new Camera (str, 503,5,"Matrimoniale",20.5);
		JH.camereDisponibili.put(c1.getIdCamera(), c1);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date inizio = date.parse("2023-08-16");
		Date fine = date.parse("2023-08-18");
		boolean response1 = JH.modificaPrezzoCamera(c1.getIdCamera(), 20.5, inizio, fine); 
		assertTrue(response1);
	}
	
	@Test
	@Order(5)
	@DisplayName("Test elimina prenotazione")
	public void testEffettuaEliminaPrenotazione() throws Exception {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u5 = new Utente ("Gianni" , "L'attore", "gianni@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u5.getIdUtente(), u5);
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		Camera c2 = new Camera(s1,202,2,"Doppia", 40.50);
		JH.camereDisponibili.put(c2.getIdCamera(), c2);
		s1.aggiungiCamera(c2);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date arrivo2= date.parse("2023-08-16");
		Date partenza2 = date.parse("2023-08-18");
		Observer ob = new ObserverPrenotazione ();
		LRGH.effettuaRegistrazioneCliente("Carlo" , "Lentini", "carlo@gmail.com", "password");
		Prenotazione p2 = new Prenotazione(arrivo2, partenza2, c2, 200, JH.utenteCorrente, false, ob);
		c2.aggiungiPrenotazione(p2);
		Prenotazione p3 = new Prenotazione(arrivo2, partenza2, c2, 200, u5, false, ob);
		JH.prenotazioniEffettuate.put(p3.getIdPrenotazione(), p3);
		JH.prenotazioniEffettuate.put(p2.getIdPrenotazione(), p2);
		boolean response = JH.eliminaPrenotazione(p2.getIdPrenotazione());
		boolean response1 = JH.eliminaPrenotazione(4560); //INSERISCO UN ID CASUALE NON PRESENTE
		boolean response2 = JH.eliminaPrenotazione(p3.getIdPrenotazione()); //INSERISCO L'ID CORRETTO DELLA PRENOTAZIONE MA NON QUELLO DELL'UTENTE ATTUALE
		assertTrue(response);
		assertFalse(response1);
		assertFalse(response2);
	}


}
