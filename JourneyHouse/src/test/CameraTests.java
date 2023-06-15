package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import systemJourneyHouse.Camera;
import systemJourneyHouse.Struttura;
import systemJourneyHouse.Utente;

public class CameraTests {

	@Test
	@Order(0)
	@DisplayName("Test calcola prezzo totale")
	public void testCalcolaPrezzo() throws Exception {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		Camera c1 = new Camera(s1, 502, 5,"Matrimoniale", 20);

		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date arrivo = date.parse("2023-07-12");
		Date partenza = date.parse("2023-07-14");
		double prezzoTotale = c1.calcolaPrezzoTotale(arrivo, partenza);
		boolean response = false;

		if(prezzoTotale == 42) {
			response= true;
		}
		assertTrue(response);
	}

	@Test
	@Order(1)
	@DisplayName("Test verificaTipo")
	public void testVerificaTipoTrue() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Struttura s1 = new Struttura("Hotel Europa", "Roma", "x", "3211233211", u3);
		Camera c1 = new Camera(s1, 502, 5,"Matrimoniale", 20);
		boolean response = c1.verificaTipo("Matrimoniale");
		boolean response1 = c1.verificaTipo("Singola");
		assertTrue(response);
		assertFalse(response1);
	}	
}
