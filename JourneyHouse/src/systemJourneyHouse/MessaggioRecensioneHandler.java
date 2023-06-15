package systemJourneyHouse;

import java.util.List;
import java.util.Map;

public class MessaggioRecensioneHandler {
	private JourneyHouse JH;

	public MessaggioRecensioneHandler(JourneyHouse JH) {
		this.JH = JH;
	}


	/*
	 * Inserisci recensione
	 */
	public boolean inserisciRecensione (String testo, int voto, int idStruttura) {
		Struttura str = JH.struttureDisponibili.get(idStruttura);
		if(str != null) {
			Recensione nuovaRecensione = new Recensione (testo,voto, JH.utenteCorrente, str);  //MODIFICATO QUI E PRIMA
			int idRecensione = nuovaRecensione.getIdRecensione();
			JH.recensioniEffettuate.put(idRecensione, nuovaRecensione);
			str.aggiungiRecensione(nuovaRecensione);
			return true;
		}else {
			return false;
		}			
	}


	/*
	 * Modifica recensione
	 */
	public boolean modificaRecensione(int idRecensione, int voto, String testo) {
		Recensione recensioneDaModificare = JH.recensioniEffettuate.get(idRecensione);
		if (recensioneDaModificare != null) {
			int idAutoreRecensione = recensioneDaModificare.getIdUtenteAutore();
			int idUtenteCorrente = JH.utenteCorrente.getIdUtente();

			if(idUtenteCorrente == idAutoreRecensione) {
				recensioneDaModificare.setTesto(testo);
				recensioneDaModificare.setVoto(voto);
				return true;
			}
		}
		return false;
	}


	/*
	 * Visualizza recensioni
	 */
	public void visualizzaRecensioni() {
		for (Map.Entry<Integer,Recensione> entry : JH.recensioniEffettuate.entrySet()) {
			System.out.println(entry.getValue().toString());		
		}
	}


	/*
	 * Elimina recensione cliente
	 */
	public boolean eliminaRecensioneCliente (int idRecensione) {
		Recensione recensioneDaEliminare = JH.recensioniEffettuate.get(idRecensione);
		if(recensioneDaEliminare != null) {
			int idUtenteCorrente = JH.utenteCorrente.getIdUtente();
			int idUtenteAutore = recensioneDaEliminare.getIdUtenteAutore();
			if(idUtenteCorrente == idUtenteAutore) {
				JH.recensioniEffettuate.remove(idRecensione);
				Struttura str = recensioneDaEliminare.getStruttura();
				str.eliminaRecensione(idRecensione);
				return true;
			}	
		}
		return false;
	}


	/*
	 * Elimina recensione admin
	 */
	public boolean eliminaRecensioneAdmin (int idRecensione) {
		Recensione recensioneDaEliminare = JH.recensioniEffettuate.get(idRecensione);
		if(recensioneDaEliminare != null) {
			JH.recensioniEffettuate.remove(idRecensione);
			Struttura str = recensioneDaEliminare.getStruttura();
			//Struttura str = JH.struttureDisponibili.get(idStruttura);
			str.eliminaRecensione(idRecensione);
			return true;		
		}
		return false;
	}


	/*
	 * Invio di un messaggio
	 */
	public boolean inserisciMessaggio(String testo, int idDestinatario) {
		Utente utenteDestinatario = JH.utentiRegistrati.get(idDestinatario);
		if (utenteDestinatario != null) {
			Messaggio nuovoMessaggio = new Messaggio (JH.utenteCorrente, utenteDestinatario, testo);
			int idMessaggio = nuovoMessaggio.getIdMessaggio();
			JH.messaggiInviati.put(idMessaggio, nuovoMessaggio);
			utenteDestinatario.aggiungiMessaggioRicevuto(nuovoMessaggio);
			return true;
		}
		return false;		
	}


	/*
	 * Visualizzazione messaggi
	 */
	public void visualizzaMessaggi() {
		List <Messaggio> messaggiRicevuti = JH.utenteCorrente.getMessaggiRicevuti();

		for (int i = 0; i<messaggiRicevuti.size(); i++) {
			System.out.println(messaggiRicevuti.get(i).toString());
		}
	}
}
