package ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import systemJourneyHouse.JourneyHouse;
import systemJourneyHouse.MessaggioRecensioneHandler;

public class ModalitàClienteUI {

	public ModalitàClienteUI() {
		super();
	}

	public void modalitàCliente (JourneyHouse JH) {

		try {
			Scanner s_input = new Scanner(System.in);
			MessaggioRecensioneHandler MRH  = new MessaggioRecensioneHandler (JH);
			int z=1;
			while(z==1){
				System.out.println("\nIn che sezione vuoi entrare? Digita:");
				System.out.println("1: Ricerca struttura e concludi prenotazione");
				System.out.println("2: Lascia una recensione");
				System.out.println("3: Modifica una recensione");
				System.out.println("4: Elimina una recensione");
				System.out.println("5: Mostra recensioni");
				System.out.println("6: Annulla una tua prenotazione");
				System.out.println("7: Mostra le tue prenotazioni");
				System.out.println("8: Bacheca notifiche");
				System.out.println("0: Esci");
				Integer choice1=Integer.parseInt(s_input.nextLine());

				switch(choice1){

				case 0: //CASO ESCI
					z=0;
					break;

				case 1:    //CASI RICERCA E PRENOTAZIONE

					System.out.println("In quale citta' vorresti soggiornare: ");  
					String cittaReq=s_input.nextLine();
					Date checkIn;
					Date checkOut;
					System.out.println("Inserisci data di inizio soggiorno (formato gg/mm/aaaa): ");
					String checkInStr = s_input.nextLine();
					checkIn=new SimpleDateFormat("dd/MM/yyyy").parse(checkInStr);
					System.out.println("Inserisci data di fine soggiorno (formato gg/mm/aaaa): ");
					String checkOutStr = s_input.nextLine();
					checkOut=new SimpleDateFormat("dd/MM/yyyy").parse(checkOutStr);

					Calendar oggi = Calendar.getInstance();
					oggi.set(Calendar.HOUR_OF_DAY, 0);

					if (checkIn.before(oggi.getTime()) || checkOut.before(oggi.getTime()) ) {
						System.out.println("Il giorno di checkIn o checkOut inserito e' antecedente a oggi!");
						break;
					}else if(checkOut.before(checkIn)) {
						System.out.println("Il giorno di checkOut inserito e' antecedente a quello di checkIn!");
						break;
					}

					System.out.println("Inserisci il tipo di camera che vuoi inserire (Singola, Doppia, Matrimoniale, Tripla o Quadrupla... NB. Iniziale maiuscola.)");
					String tipoCameraReq = s_input.nextLine();

					if(tipoCameraReq.equals("Singola")== false && tipoCameraReq.equals("Doppia")== false && tipoCameraReq.equals("Matrimoniale")== false && tipoCameraReq.equals("Tripla")== false && tipoCameraReq.equals("Quadrupla")== false ) {
						System.out.println("Il tipo di camera inserita non e' valido");
						break;
					}

					ArrayList<Integer> idsCamere=new ArrayList<Integer>();	

					System.out.println("\nLe camere disponibili per periodo,tipo e destinazione scelti sono le seguenti:");
					idsCamere = JH.ricercaCamera(cittaReq, checkIn, checkOut, tipoCameraReq);

					if(idsCamere.size()==0) {
						System.out.println("Nessuna camera disponibile.");
						break;
					}
					System.out.println("Inserisci l'id della camera che vuoi selezionare.");									
					System.out.println("Il prezzo visualizzato e' comprensivo delle tasse e degli oneri del sistema.");
					System.out.println("Inserisci un numero negativo per tornare al menu' precedente.\n");

					Integer idScelto=Integer.parseInt(s_input.nextLine());

					if (!JH.verificaCamera(idsCamere, idScelto)) {
						System.out.println("L'id selezionato non e' presente fra gli id delle camere disponibili.");
						break;
					} 

					if (idScelto >= 0) {
						JH.sceltaCamera(checkIn, checkOut, idScelto);
						System.out.println("Quale metodo di pagamento desideri utilizzare: ");
						System.out.println("1: Pagamento in struttura (supplemento di 10 euro sul totale)");
						System.out.println("2: Pagamento con carta");
						Integer paymentChoice=Integer.parseInt(s_input.nextLine());

						if (paymentChoice == 1) {
							JH.pagamentoInStruttura();
							System.out.println("Prenotazione completata!");		
						} else if (paymentChoice == 2) {
							System.out.println("Inserisci il numero della tua carta di credito: ");		
							String numeroCarta = s_input.nextLine();
							Long numeroCartaLong = Long.parseLong(numeroCarta, 10);

							//JH.sceltaCamera(checkIn, checkOut, idScelto);
							if(JH.pagamentoCarta(numeroCartaLong)){
								System.out.println("Il pagamento e' andato a buon fine.");
								System.out.println("Prenotazione completata!");
							}else {
								System.out.println("Il pagamento non e' andato a buon fine.");
							}

						}else {
							System.out.println("Il pagamento non e' andato a buon fine.");
							return;
						}
					}else {
						break;
					}

					break; 

				case 2: //CASO SCRIVI RECENSIONE
					System.out.println("Inserisci l'id della struttura che vuoi recensire. Se non ricordi l'id della struttura in cui hai prenotato puoi vederlo su Mostra prenotazioni: ");
					int idStruttura = Integer.parseInt(s_input.nextLine());
					System.out.println("Inserisci il voto alla struttura (da 1 a 5): ");
					int voto = Integer.parseInt(s_input.nextLine());
					if(voto < 1 || voto > 5) {
						System.out.println("Il voto inserito non e' corretto");
						break;
					}
					System.out.println("Inserisci la tua recensione: ");
					String testo = s_input.nextLine();

					System.out.println("RIEPILOGO: ");
					System.out.println("Id Struttura: " + idStruttura + ", voto: " + voto + ", testo: "+ testo + ".");

					System.out.println("Scrivi true per confermare o false per rifiutare");
					String res= s_input.nextLine();
					//s_input.close();
					if(res.equals("true")){
						boolean responseMsg = MRH.inserisciRecensione(testo, voto, idStruttura);
						if(responseMsg == true) {
							System.out.println("L'inserimento della recensione e' andato a buon fine.");
						}else {
							System.out.println("L'inserimento della recensione non e' andato a buon fine. L'id della struttura inserito non e' corretto.");
						}					
					} else{ 	
						System.out.println("L'inserimento della recensione non e' andato a buon fine.");
					}
					break;

				case 3: //CASO MODIFICA RECENSIONE

					System.out.println("Inserisci l'id della recensione che vuoi modificare. Se non ricordi l'id della tua recensione puoi vederlo su Mostra recensioni: ");
					int idRecensioneMod = Integer.parseInt(s_input.nextLine());
					System.out.println("Inserisci il nuovo voto: ");
					int votoMod = Integer.parseInt(s_input.nextLine());
					if(votoMod < 1 || votoMod > 5) {
						System.out.println("Il voto inserito non e' corretto");
						break;
					}
					System.out.println("Inserisci il nuovo testo: ");				
					String testoMod = s_input.nextLine();
					boolean responseModMsg = MRH.modificaRecensione(idRecensioneMod, votoMod, testoMod);

					if(responseModMsg == true) {
						System.out.println("La modifica della recensione e' andata a buon fine. ");
					}else {
						System.out.println("La modifica della recensione non e' andata a buon fine. ");
					}
					break;

				case 4: //CASO ELIMINA RECENSIONE
					System.out.println("Inserisci l'id della recensione che vuoi eliminare. Se non ricordi l'id della tua recensione puoi vederlo su Mostra recensioni: ");
					int idRecensioneElim = Integer.parseInt(s_input.nextLine());
					boolean responseElim = MRH.eliminaRecensioneCliente(idRecensioneElim);

					if(responseElim == true) {
						System.out.println("L'eliminazione della recensione e' andata a buon fine. ");
					}else {
						System.out.println("L'eliminazione della recensione non e' andata a buon fine. ");
					}
					break;

				case 5: //CASO MOSTRA RECENSIONI
					MRH.visualizzaRecensioni();
					break;

				case 6://CASO ELIMINAZIONE PRENOTAZIONI
					System.out.println("Inserisci l'id della prenotazione che vuoi eliminare. Se non ricordi l'id della sua prenotazione puoi vederlo su Mostra Prenotazioni: ");
					int idPrenotazione = Integer.parseInt(s_input.nextLine());

					boolean response = JH.eliminaPrenotazione(idPrenotazione);

					if (response == true) {
						System.out.println("L'eliminazione della prenotazione e' andata a buon fine! ");
					}else {
						System.out.println("L'eliminazione della prenotazione non e' andata a buon fine! L'id della prenotazione inserito non è corretto! ");
					}
					break;

				case 7: //CASO MOSTRARE PRENOTAZIONI
					JH.visualizzaPrenotazioniCliente();
					break;

				case 8://CASO BACHECA MESSAGGI
					MRH.visualizzaMessaggi();
					break;
				}
			}
			//s_input.close();

			//FINE LOGIN CLIENTE
		}catch(Exception ex) {
			System.out.println("Selezione non consentita!");
			System.out.println("Chiudo il sistema");
			ex.printStackTrace();
			System.exit(0);
		}
	}

}