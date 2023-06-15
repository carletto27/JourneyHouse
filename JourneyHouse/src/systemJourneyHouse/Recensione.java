package systemJourneyHouse;

public class Recensione {

	private static int counter=0;
	private int idRecensione;
	private String testo;
	private int voto;
	private Utente utenteAutore;
	private Struttura struttura;


	public Recensione( String testo, int voto, Utente utenteAutore, Struttura struttura) {
		this.idRecensione = counter;
		this.testo = testo;
		this.voto = voto;
		this.utenteAutore = utenteAutore;
		this.struttura = struttura;
		counter++;
	}

	@Override
	public String toString() {
		return "Recensione [idRecensione=" + idRecensione + ", testo=" + testo + ", voto=" + voto + ", id utenteAutore="
				+ utenteAutore.getIdUtente() + ",id struttura=" + struttura.getIdStruttura() + "]";
	}

	public int getIdRecensione() {
		return idRecensione;
	}

	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getVoto() {
		return voto;
	}

	public Utente getUtenteAutore() {
		return utenteAutore;
	}

	public void setUtenteAutore(Utente utenteAutore) {
		this.utenteAutore = utenteAutore;
	}

	public Struttura getStruttura() {
		return struttura;
	}

	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}

	public int getIdUtenteAutore () {
		return utenteAutore.getIdUtente();
	}

	public int getIdStruttura() {
		return struttura.getIdStruttura();
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}	
}
