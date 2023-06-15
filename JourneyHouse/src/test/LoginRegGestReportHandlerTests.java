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
import systemJourneyHouse.Utente;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TEST LOGINREGGESTREPORTHANDLERTESTS")
public class LoginRegGestReportHandlerTests {
	JourneyHouse JH = new JourneyHouse();
	LoginRegGestReportHandler LRGH  = new LoginRegGestReportHandler (JH);

	@Test
	@Order(0)
	@DisplayName("Test Accesso Cliente")
	public void testAccessoCliente() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "cliente", "password");
		Utente u4 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "admin", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u4.getIdUtente(), u4);
		boolean response = LRGH.accessoCliente(u3.getIdUtente(), "password");
		boolean response1 = LRGH.accessoCliente(u3.getIdUtente(), "password1");  //password errata
		boolean response2 = LRGH.accessoCliente(456, "password");//id errato
		boolean response3 = LRGH.accessoCliente(u4.getIdUtente(), "password"); //tipologia errata
		assertTrue(response);
		assertFalse(response1);
		assertFalse(response2);
		assertFalse(response3);
	}

	@Test
	@Order(1)
	@DisplayName("Test Accesso Gestore")
	public void testAccessoGestore() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "gestore", "password");
		Utente u4 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u4.getIdUtente(), u4);
		boolean response = LRGH.accessoGestore(u3.getIdUtente(), "password");
		boolean response1 = LRGH.accessoGestore(u3.getIdUtente(), "password1");  //password errata
		boolean response2 = LRGH.accessoGestore(456, "password");//id errato
		boolean response3 = LRGH.accessoGestore(u4.getIdUtente(), "password"); //tipologia errata
		assertTrue(response);
		assertFalse(response1);
		assertFalse(response2);
		assertFalse(response3);
	}

	@Test
	@Order(2)
	@DisplayName("Test Accesso Admin")
	public void testAccessoAdmin() {
		Utente u3 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "admin", "password");
		Utente u4 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		JH.utentiRegistrati.put(u4.getIdUtente(), u4);
		boolean response = LRGH.accessoAdmin(u3.getIdUtente(), "password");
		boolean response1 = LRGH.accessoAdmin(u3.getIdUtente(), "password1");  //password errata
		boolean response2 = LRGH.accessoAdmin(456, "password"); //id errato
		boolean response3 = LRGH.accessoAdmin(u4.getIdUtente(), "password"); //tipologia errata
		assertTrue(response);
		assertFalse(response1);
		assertFalse(response2);
		assertFalse(response3);
	}

	@Test
	@Order(3)
	@DisplayName("Test modifica password")
	public void testModificaPassword() {
		Utente u3 = new Utente ("Carlo" , "Lentini", "carlo@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		Utente u0 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "admin", "password");
		JH.utentiRegistrati.put(u0.getIdUtente(), u0);
		LRGH.accessoAdmin(u3.getIdUtente(), "password"); 
		boolean response = LRGH.modificaPassword(u3.getIdUtente(), "password1");
		boolean response1 = LRGH.modificaPassword(456, "password1"); //Id non esistente
		assertTrue(response);
		assertFalse(response1);
	}

	@Test
	@Order(4)
	@DisplayName("Test modifica Email")
	public void testModificaEmail() {
		Utente u3 = new Utente ("Carlo" , "Lentini", "carlo@gmail.com", "cliente", "password");
		JH.utentiRegistrati.put(u3.getIdUtente(), u3);
		Utente u0 = new Utente ("Mario" , "Rossi", "mario@gmail.com", "admin", "password");
		JH.utentiRegistrati.put(u0.getIdUtente(), u0);
		LRGH.accessoAdmin(u3.getIdUtente(), "password"); 
		boolean response = LRGH.modificaEmail(u3.getIdUtente(), "ciao@gmail.com");
		boolean response1 = LRGH.modificaEmail(456, "ciao@gmail.com"); //Id non esistente
		assertTrue(response);
		assertFalse(response1);
	}
}
