package systemJourneyHouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import systemJourneyHouse.Prenotazione.prenotazioneState;

public class JourneyHouse {

	public Map<Integer,Struttura> struttureDisponibili;
	public Map<Integer,Camera> camereDisponibili;
	public Map<Integer,Prenotazione> prenotazioniEffettuate;
	public Map<Integer,Utente> utentiRegistrati;
	public Map<Integer,Recensione> recensioniEffettuate;
	public Map<Integer,Messaggio> messaggiInviati;
	private Prenotazione pInCorso;
	private Camera cInCorso;
	public Utente utenteCorrente;      
	MessaggioRecensioneHandler MRH;


	private static JourneyHouse istance; 


	//Restituisce il riferimento all'istanza singleton	 
	public static JourneyHouse getIstance(){
		if(istance==null) 
			istance = new JourneyHouse();
		return istance;
	}


	public JourneyHouse() {
		this.struttureDisponibili = new HashMap<>();
		this.camereDisponibili = new HashMap<>();
		this.prenotazioniEffettuate = new HashMap<>();
		this.utentiRegistrati = new HashMap<>();
		this.recensioniEffettuate = new HashMap<>();
		this.messaggiInviati = new HashMap <>();
		MRH = new MessaggioRecensioneHandler (this);
	}

	/*
	 * inserisci una prenotazione nel sistema
	 */
	public void inserisciPrenotazione(Prenotazione prenotazione) {
		prenotazioniEffettuate.put(prenotazione.getIdPrenotazione(), prenotazione);
	}


	/*
	 * Ricerca di una camera disponibile
	 */
	public ArrayList<Integer> ricercaCamera(String città, Date dataArrivoReq, Date dataPartenzaReq, String tipoCamera) {

		ArrayList<Integer> idsStrutture=new ArrayList<Integer>();
		ArrayList<Integer> idsCamere=new ArrayList<Integer>();
		ArrayList<Integer> idsCamereNonDisponibili=new ArrayList<Integer>();

		//FASE 1	
		for (Map.Entry<Integer,Struttura> entry : struttureDisponibili.entrySet()) {
			boolean res0 = entry.getValue().verificaStrutturaByCittà(città);
			if(res0 == true) {
				idsStrutture.add(entry.getKey());
			}
		}

		//FASE 2
		for (Map.Entry<Integer,Camera> entry : camereDisponibili.entrySet()) {
			for (int i = 0; i < idsStrutture.size(); i++) {
				int idStruttura = entry.getValue().getIdStruttura();
				int idStruttura1 = idsStrutture.get(i);
				if(idStruttura == idStruttura1){
					boolean res = entry.getValue().verificaTipo(tipoCamera);
					if(res == true) {
						idsCamere.add(entry.getKey());
					}
				}
			}		
		}

		//FASE 3 		
		for (Map.Entry<Integer,Prenotazione> entry : prenotazioniEffettuate.entrySet()) {
			for (int i = 0; i < idsCamere.size(); i++) {
				int idCamera = entry.getValue().getIdCamera();
				int idCamera1 = idsCamere.get(i);				
				if(idCamera == idCamera1){

					boolean res = entry.getValue().verificaDisponibilità(dataArrivoReq, dataPartenzaReq);
					if(res == true && !idsCamereNonDisponibili.contains(idCamera)) {
						idsCamereNonDisponibili.add(idCamera1);
					}			
				}
			}	
		}

		idsCamere.removeAll(idsCamereNonDisponibili);

		for (int i = 0; i< idsCamere.size(); i++) {
			int idCam = idsCamere.get(i);
			Camera cam = camereDisponibili.get(idCam);
			Struttura str = cam.getStruttura();
			System.out.println("\n"+str.toString());
			System.out.println(cam.toString());
			System.out.println("Prezzo totale: " + cam.calcolaPrezzoTotale(dataArrivoReq, dataPartenzaReq)  + "\n" );			
		}
		return idsCamere;
	}


	/*
	 * Scelta di una camera
	 */
	public void sceltaCamera (Date dataArrivo, Date dataPartenza, int idCamera) {
		cInCorso = camereDisponibili.get(idCamera);
		double prezzoTotale = cInCorso.calcolaPrezzoTotale(dataArrivo, dataPartenza);
		pInCorso = new Prenotazione ();
		pInCorso.setDataArrivo(dataArrivo);
		pInCorso.setDataPartenza(dataPartenza);
		pInCorso.setCamera(cInCorso); 
		pInCorso.setCostoTotale(prezzoTotale);
		pInCorso.setUtente(utenteCorrente); 
	}


	/*
	 * Metodo di pagamento con carta
	 */
	public boolean pagamentoCarta  (long numeroCarta) {
		Pagamento pagInCorso = new Pagamento ("Carta di credito",numeroCarta);

		if (pagInCorso.verificaCartaCredito()) {
			Date dataArrivo = pInCorso.getDataArrivo();
			Date dataPartenza = pInCorso.getDataPartenza();
			Camera camera = pInCorso.getCamera();       
			double costoTotale = pInCorso.getCostoTotale();

			Observer ob = new ObserverPrenotazione ();
			Prenotazione nuovaPrenotazione = new Prenotazione (dataArrivo, dataPartenza, camera, costoTotale, utenteCorrente, true, ob);
			inserisciPrenotazione (nuovaPrenotazione);
			cInCorso.aggiungiPrenotazione(nuovaPrenotazione);
			nuovaPrenotazione.createPdf();	
			Struttura str = cInCorso.getStruttura(); 
			int idDestinatario = str.getIdGestore();	
			int idPrenotazione = nuovaPrenotazione.getIdPrenotazione();
			String msgPred = "E' stata effettuata la prenotazione con id:" + idPrenotazione;

			MRH.inserisciMessaggio(msgPred, idDestinatario);
			return true;		
		}else {
			return false;
		}		
	}


	/*
	 * Metodo di pagamento in struttura
	 */
	public void pagamentoInStruttura  () {
		Pagamento pagInCorso = new Pagamento ("Contanti");

		Date dataArrivo = pInCorso.getDataArrivo();
		Date dataPartenza = pInCorso.getDataPartenza();
		Camera camera = pInCorso.getCamera();      
		double costoTotale = pInCorso.getCostoTotale();

		Observer ob = new ObserverPrenotazione ();
		Prenotazione nuovaPrenotazione = new Prenotazione (dataArrivo, dataPartenza, camera, costoTotale, utenteCorrente, false, ob);

		inserisciPrenotazione (nuovaPrenotazione);
		cInCorso.aggiungiPrenotazione(nuovaPrenotazione);
		nuovaPrenotazione.createPdf();
		Struttura str = cInCorso.getStruttura();   
		int idDestinatario = str.getIdGestore();
		int idPrenotazione = nuovaPrenotazione.getIdPrenotazione();
		String msgPred = "E' stata effettuata la prenotazione con id:" + idPrenotazione;

		MRH.inserisciMessaggio(msgPred, idDestinatario);
	}


	/*
	 * Verifica della camera scelta
	 */
	public boolean verificaCamera (ArrayList<Integer> idsCamere, Integer idScelto) {
		if(idsCamere.contains(idScelto)) {
			return true;
		}else {
			return false;
		}
	}


	/*
	 * Inserimento di una camera
	 */
	public boolean inserisciCamera (int numeroCamera, int numeroPiano, String tipoCamera, double prezzoPerNotte) {
		int idStruttura = 0;
		int idGestore = utenteCorrente.getIdUtente();

		for (Map.Entry<Integer,Struttura> entry : struttureDisponibili.entrySet()) {
			boolean res = entry.getValue().verificaIdStrutturaByIdGestore(idGestore);
			if(res == true) {
				idStruttura = entry.getKey();
			}
		}
		Struttura str = struttureDisponibili.get(idStruttura);
		boolean response = str.verificaEsistenzaCamera(numeroCamera);

		if(response == true) {
			return false;
		}else {
			Camera cameraCorrente = new Camera (str, numeroCamera, numeroPiano, tipoCamera, prezzoPerNotte); //MODIFICATO QUi	
			str.aggiungiCamera(cameraCorrente);
			int idCamera = cameraCorrente.getIdCamera();
			camereDisponibili.put(idCamera, cameraCorrente);
			return true;
		}	
	}


	/*
	 * Modifica prezzo di una camera
	 */
	public boolean modificaPrezzoCamera (int idCamera, double prezzoPerNotte, Date dataInizioPrezzo, Date dataFinePrezzo) {
		Camera cameraDaModificare = camereDisponibili.get(idCamera);

		if (cameraDaModificare != null) {
			Struttura str = cameraDaModificare.getStruttura();
			int idGestore = str.getIdGestore();
			int idUtenteCorrente = utenteCorrente.getIdUtente();
			if(idGestore == idUtenteCorrente) {
				//cameraDaModificare.setPrezzoPerNotte(prezzoPerNotte);
				Calendar calendar = Calendar.getInstance();
				Calendar endCalendar = Calendar.getInstance();
				calendar.setTime(dataInizioPrezzo);
				endCalendar.setTime(dataFinePrezzo);

				while (calendar.before(endCalendar) ||  calendar.compareTo(endCalendar) == 0) {
					Date result = calendar.getTime();
					
					Map <Date, Double> prezzoNonDefault = cameraDaModificare.getPrezzoNonDefault();
					prezzoNonDefault.put(result, prezzoPerNotte);
					calendar.add(Calendar.DATE, 1);
				}

				return true;
			}else {
				return false;
			}	
		}else {
			return false;
		}

	}


	/*
	 * visualizza camere di una struttura
	 */
	public void visualizzaCamere () {
		Struttura str  = new Struttura();
		int idUtenteCorrente = utenteCorrente.getIdUtente();

		for (Map.Entry<Integer,Struttura> entry : struttureDisponibili.entrySet()) {
			boolean res = entry.getValue().verificaIdStrutturaByIdGestore(idUtenteCorrente);
			if(res == true) {
				str = entry.getValue();
			}
		}
		List <Camera> camere = new ArrayList <Camera>();
		camere = str.getCamere();
		for (int i = 0 ; i< camere.size(); i++) {
			System.out.println(camere.get(i).toString());
		}
	}


	/*
	 * visualizza prenotazioni di un cliente
	 */
	public void visualizzaPrenotazioniCliente () {
		int idUtenteCorrente = utenteCorrente.getIdUtente();

		for (Map.Entry<Integer,Prenotazione> entry : prenotazioniEffettuate.entrySet()) {
			Camera cam = entry.getValue().getCamera();
			int idStruttura = cam.getIdStruttura();
			int idUtentePrenotazione = entry.getValue().getIdUtente();
			if(idUtentePrenotazione == idUtenteCorrente) {
				System.out.println(entry.getValue().toString());
				System.out.println("Id struttura: " + idStruttura + ".\n");
			}				
		}
	}


	/*
	 * visualizza prenotazioni di un gestore
	 */
	public void visualizzaPrenotazioniGestore () {
		Struttura str  = new Struttura();
		int idUtenteCorrente = utenteCorrente.getIdUtente();

		for (Map.Entry<Integer,Struttura> entry : struttureDisponibili.entrySet()) {			
			boolean res = entry.getValue().verificaIdStrutturaByIdGestore(idUtenteCorrente);
			if(res == true) {
				str = entry.getValue();
			}
		}
		List <Camera> camereStruttura = str.getCamere();

		for (Map.Entry<Integer,Prenotazione> entry : prenotazioniEffettuate.entrySet()) {
			int idCameraAttuale = entry.getValue().getIdCamera();

			for (int i = 0; i < camereStruttura.size(); i++) {
				int idCameraStruttura = camereStruttura.get(i).getIdCamera();
				if(idCameraAttuale == idCameraStruttura) {
					System.out.println(entry.getValue().toString());
				}
			}
		}	
	}


	/*
	 * visualizza prenotazioni dell'intero sistema
	 */
	public void visualizzaPrenotazioniAdmin() {
		for (Map.Entry<Integer,Prenotazione> entry : prenotazioniEffettuate.entrySet()) {
			System.out.println(entry.getValue().toString());
		}		
	}


	/*
	 * Elimina prenotazione
	 */
	public boolean eliminaPrenotazione (int idPrenotazione) {
		Prenotazione prenotazioneDaEliminare = prenotazioniEffettuate.get(idPrenotazione);

		if(prenotazioneDaEliminare != null ) {
			int idUtenteCorrente = utenteCorrente.getIdUtente();
			int idUtentePrenotazioneDaEliminare = prenotazioneDaEliminare.getIdUtente();
			if(prenotazioneDaEliminare.verificaDataEliminazione() == true) {
				if (idUtenteCorrente == idUtentePrenotazioneDaEliminare) {
					Camera cam = prenotazioneDaEliminare.getCamera();
					cam.eliminaPrenotazione(idPrenotazione);
					prenotazioniEffettuate.remove(idPrenotazione);
					Struttura str = cam.getStruttura();
					int idDestinatario = str.getIdGestore();
					String testoPredefinito = "La prenotazione con id: " + idPrenotazione + " e' stata eliminata.";
					MRH.inserisciMessaggio(testoPredefinito, idDestinatario);
					return true;
				}else {
					return false;
				}
			} else{
				return false;
			}
		}else {
			return false;
		}
	}


	public boolean cambioStatoDur (int idPrenotazione) {
		Prenotazione prenot=  prenotazioniEffettuate.get(idPrenotazione);

		if (prenot != null) {
			Camera cam = prenot.getCamera();
			Struttura str = cam.getStruttura();
			int idGestore = str.getIdGestore();
			int idUtenteCorrente = utenteCorrente.getIdUtente();
			if(idGestore == idUtenteCorrente) {
				prenotazioneState stato = prenot.getStato();
				if(stato.equals(prenotazioneState.PRECHECKIN)) {
					Utente admin = utentiRegistrati.get(0);
					Messaggio msg = prenot.setStato(admin,prenotazioneState.DURANTESOGGIORNO);
					int idMessaggio = msg.getIdMessaggio();
					messaggiInviati.put(idMessaggio, msg);
					int idUtenteDestinatario = msg.getIdDestinatario();
					Utente utenteDestinatario = utentiRegistrati.get(idUtenteDestinatario);
					utenteDestinatario.aggiungiMessaggioRicevuto(msg);					
					return true;
				}else {
					return false;
				}
			}else {
				return false;			
			}
		}else {
			return false;
		}
	}

	public boolean cambioStatoFin (int idPrenotazione) {
		Prenotazione prenot=  prenotazioniEffettuate.get(idPrenotazione);

		if (prenot != null) {
			Camera cam = prenot.getCamera();
			Struttura str = cam.getStruttura();
			int idGestore = str.getIdGestore();
			int idUtenteCorrente = utenteCorrente.getIdUtente();
			if(idGestore == idUtenteCorrente) {
				if(prenot.getStato().equals(prenotazioneState.DURANTESOGGIORNO)) {
					Utente admin = utentiRegistrati.get(0);
					Messaggio msg = prenot.setStato(admin, prenotazioneState.POSTCHECKIN);
					int idMessaggio = msg.getIdMessaggio();
					messaggiInviati.put(idMessaggio, msg);
					int idUtenteDestinatario = msg.getIdDestinatario();
					Utente utenteDestinatario = utentiRegistrati.get(idUtenteDestinatario);
					utenteDestinatario.aggiungiMessaggioRicevuto(msg);
					return true;
				}else {
					return false;				
				}
			}else {
				return false;			
			}
		}else {
			return false;
		}
	}
}










