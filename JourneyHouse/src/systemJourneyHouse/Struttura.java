package systemJourneyHouse;

import java.util.ArrayList;
import java.util.List;

public class Struttura {

	private static int counter=0;
	private int idStruttura; 
	private String nome;
	private String città;
	private String indirizzo;
	private String numeroTelefono;
	private List<Camera> camere;
	private List<Recensione> recensioni;
	private Utente gestore;

	public Struttura(String nome, String città, String indirizzo, String numeroTelefono, Utente gestore) {
		this.idStruttura = counter;
		this.nome = nome;
		this.città = città;
		this.indirizzo = indirizzo;
		this.numeroTelefono = numeroTelefono;
		this.camere = new ArrayList <Camera>();
		this.recensioni = new ArrayList <Recensione>();
		this.gestore = gestore;
		counter++;
	}

	public Struttura() {
		super();
	}

	@Override
	public String toString() {
		return "Struttura [idStruttura=" + idStruttura + ", nome=" + nome + ", citta'=" + città + ", indirizzo="
				+ indirizzo + ", numeroTelefono=" + numeroTelefono + ", id gestore=" + gestore.getIdUtente() + "]";
	}

	public boolean verificaStrutturaByCittà(String città) {
		if(this.città.equals(città)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean verificaIdStrutturaByIdGestore (int idGestore) {
		if(this.gestore.getIdUtente() == idGestore) {
			return true;
		}else {
			return false;
		}
	}

	public boolean verificaEsistenzaCamera (int numeroCamera) {
		for(int i = 0; i<this.camere.size(); i++) {
			int numeroCameraAtt = this.camere.get(i).getNumeroCamera();
			if(numeroCameraAtt == numeroCamera) {
				return true;
			}
		}
		return false;
	}

	public void eliminaRecensione (int idRecensione) {
		int indiceDaEliminare = 0;
		for (int i=0; i<recensioni.size(); i++) {
			int idRecensioneCorrente = recensioni.get(i).getIdRecensione();
			if(idRecensioneCorrente == idRecensione) {
				indiceDaEliminare = i;
			}
		}
		recensioni.remove(indiceDaEliminare);
	}


	public void aggiungiCamera (Camera camera) {
		camere.add(camera);
	}

	public void aggiungiRecensione (Recensione recensione) {
		recensioni.add(recensione);
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCittà() {
		return città;
	}

	public void setCittà(String città) {
		this.città = città;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}


	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Struttura.counter = counter;
	}

	public int getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}


	public List<Camera> getCamere() {
		return camere;
	}


	public void setCamere(List<Camera> camere) {
		this.camere = camere;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public int getIdGestore() {
		return gestore.getIdUtente();
	}


	public Utente getGestore() {
		return gestore;
	}


	public void setGestore(Utente gestore) {
		this.gestore = gestore;
	}


	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}
}
