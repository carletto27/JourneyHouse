package systemJourneyHouse;

public class Messaggio {
	private static int counter=0;
	int idMessaggio;
	Utente mittente;
	Utente destinatario;
	String testo;


	public Messaggio(Utente mittente, Utente destinatario, String testo) {
		this.idMessaggio = counter;
		this.mittente = mittente;
		this.destinatario = destinatario;
		this.testo = testo;
		counter++;
	}


	@Override
	public String toString() {
		return "Messaggio [idMessaggio=" + idMessaggio + ", id mittente=" + mittente.getIdUtente() + ", id destinatario=" + destinatario.getIdUtente()
		+ ", testo=" + testo + "]";
	}


	public int getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(int idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Utente getMittente() {
		return mittente;
	}

	public void setMittente(Utente mittente) {
		this.mittente = mittente;
	}

	public int getIdDestinatario() {
		return destinatario.getIdUtente();
	}

	public int getIdMittente() {
		return mittente.getIdUtente();
	}

	public Utente getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Utente destinatario) {
		this.destinatario = destinatario;
	}
}
