package systemJourneyHouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Camera {

	private static int counter=0;
	private int idCamera;
	private Struttura struttura;
	private int numeroCamera;
	private int numeroPiano;
	private String tipoCamera;
	private double prezzoPerNotteDefault;
	private List<Prenotazione> prenotazioni;
	private Map<Date,Double> prezzoNonDefault;

	public Camera(Struttura struttura, int numeroCamera, int numeroPiano, String tipoCamera,double prezzoPerNotteDefault) {
		super();
		this.idCamera = counter;
		this.struttura = struttura;
		this.numeroCamera = numeroCamera;
		this.numeroPiano = numeroPiano;
		this.tipoCamera = tipoCamera;
		this.prezzoPerNotteDefault = prezzoPerNotteDefault;
		this.prenotazioni = new ArrayList <Prenotazione>();
		this.prezzoNonDefault = new HashMap <>();
		counter++;
	}


	@Override
	public String toString() {
		return "Camera [idCamera=" + idCamera + ", id struttura=" + struttura.getIdStruttura() + ", numeroCamera=" + numeroCamera
				+ ", numeroPiano=" + numeroPiano + ", tipoCamera=" + tipoCamera + ", prezzoPerNotte=" + prezzoPerNotteDefault
				+ "]";
	}

	public void aggiungiPrenotazione (Prenotazione prenotazione) {
		prenotazioni.add(prenotazione);
	}

	public double calcolaPrezzoTotale(Date dataArrivo, Date dataPartenza) {
		double prezzoTotale = 0;

		Calendar calendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		calendar.setTime(dataArrivo);
		endCalendar.setTime(dataPartenza);

		List <Date> date = new ArrayList <Date>();
		for (Map.Entry<Date,Double> entry : prezzoNonDefault.entrySet()) {
			date.add(entry.getKey());
		}

		while (calendar.before(endCalendar)) {
			Date result = calendar.getTime();

			if (date.contains(result)) {
				double prezzoPerNotte = prezzoNonDefault.get(result);
				double tassa = ((2*prezzoPerNotte)/100);
				double tassaSistema = ((3*prezzoPerNotte)/100);		
				prezzoTotale += prezzoPerNotte+tassa+tassaSistema;	
			}else {
				double prezzoPerNotte = this.prezzoPerNotteDefault;
				double tassa = ((2*prezzoPerNotte)/100);
				double tassaSistema = ((3*prezzoPerNotte)/100);		
				prezzoTotale += prezzoPerNotte+tassa+tassaSistema;
			}
			calendar.add(Calendar.DATE, 1);
		}
		long factor = (long) Math.pow(10, 2);
		prezzoTotale = prezzoTotale * factor;
		long tmp = Math.round(prezzoTotale);
		prezzoTotale = (double) tmp / factor;
		return prezzoTotale;
	}

	public boolean verificaTipo(String tipo) {
		if (this.tipoCamera.equals(tipo)) {
			return true;
		}else {
			return false;
		}
	}

	public void eliminaPrenotazione (int idPrenotazione) {
		int indiceDaEliminare = 0;
		for (int i=0; i<prenotazioni.size(); i++) {
			int idPrenotazioneCorrente = prenotazioni.get(i).getIdPrenotazione();
			if(idPrenotazioneCorrente == idPrenotazione) {
				indiceDaEliminare = i;
			}
		}
		prenotazioni.remove(indiceDaEliminare);
	}

	public int getIdStruttura() {
		return this.struttura.getIdStruttura();
	}

	public int getNumeroCamera() {
		return numeroCamera;
	}

	public void setNumeroCamera(int numeroCamera) {
		this.numeroCamera = numeroCamera;
	}

	public int getNumeroPiano() {
		return numeroPiano;
	}

	public void setNumeroPiano(int numeroPiano) {
		this.numeroPiano = numeroPiano;
	}

	public String getTipoCamera() {
		return tipoCamera;
	}

	public void setTipoCamera(String tipoCamera) {
		this.tipoCamera = tipoCamera;
	}

	public double getPrezzoPerNotteDefault() {
		return prezzoPerNotteDefault;
	}

	public void setPrezzoPerNotteDefault(float prezzoPerNotte) {
		this.prezzoPerNotteDefault = prezzoPerNotte;
	}

	public int getIdCamera() {
		return idCamera;
	}

	public void setIdCamera(int idCamera) {
		this.idCamera = idCamera;
	}

	public void setPrezzoPerNotte(double prezzoPerNotte) {
		this.prezzoPerNotteDefault = prezzoPerNotte;
	}

	public Struttura getStruttura() {
		return struttura;
	}

	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}


	public Map<Date, Double> getPrezzoNonDefault() {
		return prezzoNonDefault;
	}


	public void setPrezzoNonDefault(Map<Date, Double> prezzoNonDefault) {
		this.prezzoNonDefault = prezzoNonDefault;
	}
}
