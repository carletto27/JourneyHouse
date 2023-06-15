package systemJourneyHouse;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;



public class Prenotazione {

	public enum prenotazioneState {
		PRECHECKIN,
		DURANTESOGGIORNO,
		POSTCHECKIN
	}

	private static int counter=0;
	private int idPrenotazione;
	private Date dataArrivo;
	private Date dataPartenza;
	private Camera camera;
	private double costoTotale;
	private Utente utente;
	private boolean pagato; //True indica pagato online (già pagato), false indica da pagare in struttura (non pagato)
	private prenotazioneState stato;
	private Observer observer;

	public Prenotazione() {
		super();
	}

	public Prenotazione(Date dataArrivo, Date dataPartenza, Camera camera, double costoTotale,
			Utente utente, boolean pagato,Observer observer) {
		this.idPrenotazione = counter;;
		this.dataArrivo = dataArrivo;
		this.dataPartenza = dataPartenza;
		this.camera = camera;
		this.costoTotale = costoTotale;
		this.utente = utente;
		this.pagato = pagato;
		this.stato = prenotazioneState.PRECHECKIN;;
		this.observer = observer;
		counter++;
	}

	public boolean verificaDisponibilità (Date dataArrivo, Date dataPartenza) {
		if((dataPartenza.after(this.dataArrivo) & this.dataPartenza.after(dataArrivo))) {
			return true; //Torna true se la camera e' indisponibile per le date passate
		}else {
			return false;
		}
	}

	public void createPdf () {
		Document doc = new Document(); 
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.dataArrivo);
			cal.set(Calendar.HOUR_OF_DAY, 15);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dataArrivoForm =  cal.getTime();
			cal.setTime(this.dataPartenza);
			cal.set(Calendar.HOUR_OF_DAY, 10);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dataPartenzaForm =  cal.getTime();

			String save = "path" + (this.idPrenotazione) + ".pdf";
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(save));    
			doc.open();   
			doc.add(new Paragraph(" Id Prenotazione: " + this.idPrenotazione + ",\n Id Camera: "+this.camera.getIdCamera() + ",\n Data checkIn: " + dataArrivoForm + ",\n Data checkOut: " +dataPartenzaForm + ",\n Costo della prenotazione: " + costoTotale + "€." ));   
			doc.close();  
			writer.close();  
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public boolean verificaDataEliminazione () {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		Date dataArrivo = this.dataArrivo;
		long diffLong = dataArrivo.getTime() - today.getTime().getTime();
		int numDays = (int) (diffLong / (1000*60*60*24));
		if (numDays >= 3) {
			return true;
		}else {
			return false;
		}

	}

	public Messaggio setStato(Utente admin,prenotazioneState newState) {
		this.stato = newState;
		return observer.update(admin, this.stato, this.utente);
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}

	public int getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(int idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public double getCostoTotale() {
		return costoTotale;
	}


	public void setCostoTotale(double costoTotale) {
		this.costoTotale = costoTotale;
	}

	public boolean isPagato() {
		return pagato;
	}

	public void setPagato(boolean pagato) {
		this.pagato = pagato;
	}

	@Override
	public String toString() {
		return "Prenotazione [idPrenotazione=" + idPrenotazione + ", dataArrivo=" + dataArrivo + ", dataPartenza="
				+ dataPartenza + ",id camera=" + camera.getIdCamera() + ", costoTotale=" + costoTotale + ", id utente=" + utente.getIdUtente()
				+ ", pagato=" + pagato + ", stato=" + stato + "]";
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public int getIdUtente() {
		return this.utente.getIdUtente();
	}

	public int getIdCamera() {
		return this.camera.getIdCamera();
	}

	public prenotazioneState getStato() {
		return stato;
	}
}


