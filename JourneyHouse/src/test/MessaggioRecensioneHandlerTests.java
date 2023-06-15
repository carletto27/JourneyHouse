package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import systemJourneyHouse.JourneyHouse;
import systemJourneyHouse.LoginRegGestReportHandler;
import systemJourneyHouse.MessaggioRecensioneHandler;
import systemJourneyHouse.Recensione;
import systemJourneyHouse.Struttura;
import systemJourneyHouse.Utente;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TEST MESSAGGIORECENSIONEHANDLER")
public class MessaggioRecensioneHandlerTests {
	JourneyHouse JH = new JourneyHouse();
	LoginRegGestReportHandler LRGH  = new LoginRegGestReportHandler (JH);
	MessaggioRecensioneHandler MRH = new MessaggioRecensioneHandler (JH);

	@Test
	@Order(0)
	@DisplayName("Test Inserimento Recensione")
	public void testInserimentoRecensione(){
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		LRGH.effettuaRegistrazioneCliente("Carlo" , "Lentini", "carlo@gmail.com", "password");
		boolean response = MRH.inserisciRecensione("Prova", 5, s1.getIdStruttura());
		boolean response1 = MRH.inserisciRecensione("Prova", 5, 256); //L'ID INSERITO E' CASUALE
		assertTrue(response);
		assertFalse(response1);
	}

	@Test
	@Order(1)
	@DisplayName("Test Modifica Recensione")
	public void testModificaRecensione(){
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u5 = new Utente ("Gianni" , "L'attore", "gianni@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u5.getIdUtente(), u5);
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		LRGH.effettuaRegistrazioneCliente("Carlo" , "Lentini", "carlo@gmail.com", "password");
		Recensione r0 = new Recensione("prova", 5, u5, s1);
		Recensione r1 = new Recensione("prova", 5, JH.utenteCorrente, s1);
		JH.recensioniEffettuate.put(r1.getIdRecensione(), r1);
		s1.aggiungiRecensione(r1);
		boolean response = MRH.modificaRecensione(r1.getIdRecensione(), 5, "Prova"); 
		boolean response1 = MRH.modificaRecensione(256, 5, "Prova"); //L'ID INSERITO E' CASUALE
		boolean response2 = MRH.modificaRecensione(r0.getIdRecensione(), 5, "Prova"); //L'ID INSERITO NON APPARTIENTE ALL'UTENTE ATTUALE
		assertTrue(response);
		assertFalse(response1);
		assertFalse(response2);
	}

	@Test
	@Order(2)
	@DisplayName("Test Elimina Recensione")
	public void testEliminaRecensione(){
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u5 = new Utente ("Gianni" , "L'attore", "gianni@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u5.getIdUtente(), u5);
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		JH.struttureDisponibili.put(s1.getIdStruttura(), s1);
		LRGH.effettuaRegistrazioneCliente("Carlo" , "Lentini", "carlo@gmail.com", "password");
		Recensione r1 = new Recensione("prova", 5, JH.utenteCorrente, s1);
		Recensione r2 = new Recensione("prova", 5, u5, s1);
		JH.recensioniEffettuate.put(r1.getIdRecensione(), r1);
		JH.recensioniEffettuate.put(r2.getIdRecensione(), r2);
		s1.aggiungiRecensione(r1);
		boolean response = MRH.eliminaRecensioneCliente(r1.getIdRecensione());
		boolean response1 = MRH.eliminaRecensioneCliente(r2.getIdRecensione()); //L'ID INSERITO NON APPARTIENTE ALL'UTENTE ATTUALE
		boolean response2 = MRH.eliminaRecensioneCliente(456); //L'ID INSERITO E' CASUALE
		assertTrue(response);
		assertFalse(response1);
		assertFalse(response2);
	}

	@Test
	@Order(3)
	@DisplayName("Test Inserisci Messaggio")
	public void testInserisciMessaggio(){
		Utente u5 = new Utente ("Gianni" , "L'attore", "gianni@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u5.getIdUtente(), u5);
		LRGH.effettuaRegistrazioneGestore("Mario", "Rossi", "mario@gmail.com", "password", "Hotel Europa", "Parigi", "Via Roma", "3211234567");
		boolean response = MRH.inserisciMessaggio("prova", u5.getIdUtente());
		boolean response1 = MRH.inserisciMessaggio("prova", 256); //L'ID INSERITO NON APPARTIENE AD ALCUN UTENTE
		assertTrue(response);
		assertFalse(response1);
	}
}
