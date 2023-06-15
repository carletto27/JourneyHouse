package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import systemJourneyHouse.Camera;
import systemJourneyHouse.Struttura;
import systemJourneyHouse.Utente;

public class StrutturaTest {

	@Test
	@Order(0)
	@DisplayName("Test verificaStrutturaByCittà")
	public void testverificaStrutturaByCittà() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		boolean response = s1.verificaStrutturaByCittà("Roma");
		boolean response1 = s1.verificaStrutturaByCittà("Catania");
		assertTrue(response);
		assertFalse(response1);
	}


	@Test
	@Order(1)
	@DisplayName("Test verificaStrutturaByGestore")
	public void verificaIdStrutturaByIdGestore() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		boolean response = s1.verificaIdStrutturaByIdGestore(u3.getIdUtente());
		boolean response1 = s1.verificaIdStrutturaByIdGestore(456); 
		assertTrue(response);
		assertFalse(response1);
	}


	@Test
	@Order(2)
	@DisplayName("Test verificaEsistenzaCamera")
	public void verificaEsistenzaCamera() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);	
		Camera c1 = new Camera(s1, 502, 5,"Matrimoniale", 20);
		s1.aggiungiCamera(c1);
		boolean response = s1.verificaEsistenzaCamera(502);
		boolean response1 = s1.verificaEsistenzaCamera(503);
		assertTrue(response);
		assertFalse(response1);
	}
}
