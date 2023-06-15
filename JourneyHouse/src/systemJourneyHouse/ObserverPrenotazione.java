package systemJourneyHouse;

import systemJourneyHouse.Prenotazione.prenotazioneState;

public class ObserverPrenotazione implements Observer{

	private prenotazioneState stato;


	@Override
	public Messaggio update(Utente admin, Object stato, Utente destinatario) {
		this.stato = (prenotazioneState) stato;
		prenotazioneState st = prenotazioneState.DURANTESOGGIORNO;

		if (this.stato.equals(st)) {
			String testo = "Buon Soggiorno";
			Messaggio msg = new Messaggio (admin, destinatario, testo);
			return msg;
		}else {
			String testo = "La ringraziamo per aver soggiornato da noi";
			Messaggio msg = new Messaggio (admin, destinatario, testo);
			return msg;
		}
	}
}
