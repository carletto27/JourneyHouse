package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import systemJourneyHouse.Camera;
import systemJourneyHouse.Observer;
import systemJourneyHouse.ObserverPrenotazione;
import systemJourneyHouse.Prenotazione;
import systemJourneyHouse.Struttura;
import systemJourneyHouse.Utente;

public class prenotazioneTests {
	
	@Test
	@Order(0)
	@DisplayName("Test verificaDisponibilità")
	public void testVerificaDisponibilita() throws Exception {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u4 = new Utente ("Carlo" , "Lentini", "carlo@gmail.com", "cliente", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		Camera c1 = new Camera(s1, 502, 5,"Matrimoniale", 20);
		s1.aggiungiCamera(c1);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date arrivo = date.parse("2023-07-12");
		Date partenza = date.parse("2023-07-14");
		Observer ob = new ObserverPrenotazione ();
		Prenotazione p1 = new Prenotazione(arrivo, partenza, c1,100.0, u4,true,ob);
		Date arrivoReq = date.parse("2023-07-11");
		Date partenzaReq = date.parse("2023-07-15");
		boolean response = p1.verificaDisponibilità(arrivoReq, partenzaReq);
		Date arrivoReq1 = date.parse("2023-07-18");
		Date partenzaReq1 = date.parse("2023-07-20");
		boolean response1 = p1.verificaDisponibilità(arrivoReq1, partenzaReq1);
		assertTrue(response);
		assertFalse(response1);
	}
	
	@Test
	@Order(1)
	@DisplayName("Test verificaDataEliminazione")
	public void testVerificaDataEliminazione() throws Exception {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u4 = new Utente ("Carlo" , "Lentini", "carlo@gmail.com", "cliente", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		Camera c1 = new Camera(s1, 502, 5,"Matrimoniale", 20);
		s1.aggiungiCamera(c1);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date arrivo = date.parse("2023-06-28");
		Date partenza = date.parse("2023-06-30");
		Observer ob = new ObserverPrenotazione ();
		Prenotazione p1 = new Prenotazione(arrivo, partenza, c1,100.0, u4,true,ob);
		boolean response = p1.verificaDataEliminazione();
		assertTrue(response);
	}

}
