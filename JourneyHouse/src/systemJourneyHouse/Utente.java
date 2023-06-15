package systemJourneyHouse;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	private static int counter=0;
	private int idUtente;
	String nome;
	String cognome;
	String email;
	String tipologiaUtente;
	String password;
	private List<Messaggio> messaggiRicevuti;

	public Utente(String nome, String cognome, String email, String tipologiaUtente, String password) {
		this.idUtente = counter;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.tipologiaUtente = tipologiaUtente;
		this.password = password;
		this.messaggiRicevuti = new ArrayList <Messaggio>();
		counter++;
	}

	public void aggiungiMessaggioRicevuto(Messaggio messaggio) {
		messaggiRicevuti.add(messaggio);
	}

	@Override
	public String toString() {
		return "Utente [idUtente=" + idUtente + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email
				+ ", tipologiaUtente=" + tipologiaUtente + ", password=" + password + "]";
	}

	public boolean verificaPasswordETipo (String password, String tipo) {
		if(this.password.equals(password) && this.tipologiaUtente.equals(tipo)){
			return true;
		} else{ 	
			return false;
		}
	}

	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public String getTipologiaUtente() {
		return tipologiaUtente;
	}
	public void setTipologiaUtente(String tipologiaUtente) {
		this.tipologiaUtente = tipologiaUtente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}
	public void seteMail(String email) {
		this.email = email;
	}

	public List<Messaggio> getMessaggiRicevuti() {
		return messaggiRicevuti;
	}

	public void setMessaggiRicevuti(List<Messaggio> messaggiRicevuti) {
		this.messaggiRicevuti = messaggiRicevuti;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
