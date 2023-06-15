package systemJourneyHouse;

import java.io.FileOutputStream;
import java.util.Map;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class LoginRegGestReportHandler {
	private JourneyHouse JH;

	public LoginRegGestReportHandler(JourneyHouse JH) {
		this.JH = JH;
	}


	/*
	 * Accesso di un cliente
	 */
	public boolean accessoCliente (int idCliente, String password) {
		Utente clienteCorrente = JH.utentiRegistrati.get(idCliente);

		if (clienteCorrente != null) {
			boolean response = clienteCorrente.verificaPasswordETipo(password, "cliente");
			if (response == true) {
				JH.utenteCorrente = clienteCorrente;
				return true;
			}else {
				return false;
			}
		}
		return false;
	}


	/*
	 * Accesso di un gestore
	 */
	public boolean accessoGestore (int idGestore, String password) {
		Utente gestoreCorrente = JH.utentiRegistrati.get(idGestore);

		if (gestoreCorrente != null) {
			boolean response = gestoreCorrente.verificaPasswordETipo(password, "gestore");
			if (response == true) {
				JH.utenteCorrente = gestoreCorrente;
				return true;
			}else {
				return false;
			}
		}
		return false;
	}


	/*
	 * Accesso dell'admin
	 */
	public boolean accessoAdmin (int idAdmin, String password) {
		Utente admin = JH.utentiRegistrati.get(idAdmin);

		if (admin != null) {
			boolean response = admin.verificaPasswordETipo(password, "admin");
			if (response == true) {
				JH.utenteCorrente = admin;
				return true;
			}else {
				return false;
			}
		}
		return false;
	}


	/*
	 * Registrazione cliente
	 */
	public void effettuaRegistrazioneCliente (String nome, String cognome, String email, String password) {
		Utente clienteCorrente = new Utente (nome, cognome, email, "cliente", password);
		int idCliente = clienteCorrente.getIdUtente();
		JH.utentiRegistrati.put(idCliente, clienteCorrente);	
		JH.utenteCorrente = clienteCorrente;
		System.out.println("Registrazione effettuata. Il tuo id e': " + idCliente +". Ricordalo per effettuare l'accesso");
	}


	/*
	 * Inserisci struttura all'interno del sistema
	 */
	public void inserisciStruttura (String nome, String città, String indirizzo, String numTelefono) {
		Struttura nuovaStruttura = new Struttura (nome, città, indirizzo, numTelefono, JH.utenteCorrente);
		int idStruttura = nuovaStruttura.getIdStruttura();
		JH.struttureDisponibili.put(idStruttura, nuovaStruttura);
	}


	/*
	 * Registrazione gestore
	 */
	public void effettuaRegistrazioneGestore (String nome, String cognome, String email, String password, String nomeStruttura, String città, String indirizzo, String numTelefono) {
		Utente gestoreCorrente = new Utente (nome, cognome, email, "gestore", password);
		int idGestore = gestoreCorrente.getIdUtente();
		JH.utentiRegistrati.put(idGestore, gestoreCorrente);	
		JH.utenteCorrente = gestoreCorrente;
		inserisciStruttura(nomeStruttura,città,indirizzo,numTelefono);		
		System.out.println("Registrazione effettuata. Il tuo id e': " + idGestore +". Ricordalo per effettuare l'accesso");
	}

	public boolean generaReport () {
		Document doc = new Document(); 
		String save = "path.pdf";

		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(save));    
			doc.open(); 

			for (Map.Entry<Integer,Struttura> entry : JH.struttureDisponibili.entrySet()) {
				doc.add(new Paragraph(entry.getValue().toString()));
			}
			doc.add(new Paragraph("\n"));

			for (Map.Entry<Integer,Camera> entry : JH.camereDisponibili.entrySet()) {
				doc.add(new Paragraph(entry.getValue().toString()));
			}
			doc.add(new Paragraph("\n"));

			for (Map.Entry<Integer,Prenotazione> entry : JH.prenotazioniEffettuate.entrySet()) {
				doc.add(new Paragraph(entry.getValue().toString()));
			}
			doc.add(new Paragraph("\n"));

			for (Map.Entry<Integer,Recensione> entry : JH.recensioniEffettuate.entrySet()) {
				doc.add(new Paragraph(entry.getValue().toString()));
			}
			doc.add(new Paragraph("\n"));

			for (Map.Entry<Integer,Utente> entry : JH.utentiRegistrati.entrySet()) {
				doc.add(new Paragraph(entry.getValue().toString()));
			}
			doc.add(new Paragraph("\n"));

			doc.close();  
			writer.close();  
		}catch (Exception e) {
			return false;
		} 
		return true;
	}

	public boolean modificaEmail (int idUtente, String email) {
		Utente utenteMod = JH.utentiRegistrati.get(idUtente);

		if (utenteMod != null) {
			utenteMod.seteMail(email);
			return true;
		}else {
			return false;
		}
	}

	public boolean modificaPassword (int idUtente, String password) {
		Utente utenteMod = JH.utentiRegistrati.get(idUtente);

		if (utenteMod != null) {
			utenteMod.setPassword(password);
			return true;
		}else {
			return false;
		}
	}


	public void visualizzaUtenti() {
		for (Map.Entry<Integer,Utente> entry : JH.utentiRegistrati.entrySet()) {
			System.out.println(entry.getValue().toString());
		}
	}







}
