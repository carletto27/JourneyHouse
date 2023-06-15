package ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import systemJourneyHouse.JourneyHouse;
import systemJourneyHouse.MessaggioRecensioneHandler;

public class ModalitàGestoreUI {


	public void modalitàGestore(JourneyHouse JH) {
		Scanner s_input = new Scanner(System.in);
		MessaggioRecensioneHandler MRH  = new MessaggioRecensioneHandler (JH);
		int x=1;
		while(x==1){
			System.out.println("\nIn che sezione vuoi entrare? Digita:");
			System.out.println("1: Inserisci camera");
			System.out.println("2: Visualizza le camere della tua struttura");
			System.out.println("3: Modifica camera, usa questa sessione per applicare uno sconto e abbassare il prezzo");
			System.out.println("4: Invia messaggio a cliente");
			System.out.println("5: Mostra le prenotazioni per la tua struttura");
			System.out.println("6: Bacheca notifiche");
			System.out.println("7: Mostra recensioni");
			System.out.println("8: Cambia stato Prenotazione da Pre-CheckIn a Soggiorno in corso");
			System.out.println("9: Cambia stato Prenotazione da Soggiorno in corso a Post Soggiorno");
			System.out.println("0: Esci");
			Integer choice=Integer.parseInt(s_input.nextLine());

			switch(choice){

			case 0: //CASO ESCI
				x=0;
				break;

			case 1: //CASO INSERIMENTO CAMERA   				
				System.out.println("Inserisci il numero della camera: ");
				int numeroCamera=Integer.parseInt(s_input.nextLine());  
				System.out.println("Inserisci il piano della camera (numero intero, es. 3)");
				int numeroPiano = Integer.parseInt(s_input.nextLine());   
				System.out.println("Inserisci il tipo di camera che vuoi inserire (Singola, Doppia, Matrimoniale, Tripla o Quadrupla... NB. Iniziale maiuscola.)");
				String tipoCamera = s_input.nextLine();
				if(tipoCamera.equals("Singola")== false && tipoCamera.equals("Doppia")== false && tipoCamera.equals("Matrimoniale")== false && tipoCamera.equals("Tripla")== false && tipoCamera.equals("Quadrupla")== false ) {
					System.out.println("Il tipo di camera inserita non e' valido");
					break;
				}
				System.out.println("Inserisci il prezzo per notte della tua camera: ");
				double prezzoPerNotte = Double.parseDouble(s_input.nextLine());

				System.out.println("RIEPILOGO: ");
				System.out.println("Numero camera: " + numeroCamera + ", piano Camera: " + numeroPiano + ", tipo camera: "+ tipoCamera + ", prezzo per notte: " + prezzoPerNotte + "€.");

				System.out.println("Scrivi true per confermare o false per rifiutare");
				String res= s_input.nextLine();
				if(res.equals("true")){
					boolean response = JH.inserisciCamera(numeroCamera, numeroPiano, tipoCamera, prezzoPerNotte);

					if(response == true) {
						System.out.println("L'inserimento e' andato a buon fine");
					}else {
						System.out.println("L'inserimento non e' andato a buon fine. Potrebbe essera gia' presente una camera con questo numero. Riprova");
					}					
				} else{ 	
					System.out.println("L'inserimento della camera non e' andato a buon fine.");
				}
				break; 				

			case 2: //CASO VISUALIZZA CAMERE
				JH.visualizzaCamere();
				break;  

			case 3: //CASO MODIFICA PREZZO CAMERA
				try {
				System.out.println("Inserisci l'id della camera di cui vuoi modificare il prezzo (puoi vederlo in visualizza camere):");
				int idCameraDaModificare=Integer.parseInt(s_input.nextLine());		
				System.out.println("Inserisci il prezzo aggiornato per notte della tua camera: ");
				float prezzoAggiornato = Float.parseFloat(s_input.nextLine());
				
				Date inizio;
				Date fine;
				System.out.println("Inserisci data di inizio dei prezzi aggiornati(formato gg/mm/aaaa): ");
				String inizioStr = s_input.nextLine();
				inizio=new SimpleDateFormat("dd/MM/yyyy").parse(inizioStr);
				System.out.println("Inserisci data di fine dei prezzi aggiornati (formato gg/mm/aaaa): ");
				String fineStr = s_input.nextLine();
				fine=new SimpleDateFormat("dd/MM/yyyy").parse(fineStr);

				Calendar oggi = Calendar.getInstance();
				oggi.set(Calendar.HOUR_OF_DAY, 0);

				if (inizio.before(oggi.getTime()) || fine.before(oggi.getTime()) ) {
					System.out.println("Il giorno di checkIn o checkOut inserito e' antecedente a oggi!");
					break;
				}else if(fine.before(inizio)) {
					System.out.println("Il giorno di checkOut inserito e' antecedente a quello di checkIn!");
					break;
				}
				boolean responseModifica = JH.modificaPrezzoCamera(idCameraDaModificare, prezzoAggiornato, inizio, fine);

				if(responseModifica == true) {
					System.out.println("La modifica e' andata a buon fine! ");
				}else {
					System.out.println("La modifica non e' andata a buon fine! L'id inserito non e' corretto!");	
				}
				break;
				}catch(Exception ex) {
					break;
				}

			case 4: //CASO INSERISCI MESSAGGIO 
				System.out.println("Inserisca l'id dell'utente al quale vuoi inviare un messaggio (Se non lo ricordi puoi visualizzarlo nella tua bacheca prenotazioni): ");
				int idDestinatario=Integer.parseInt(s_input.nextLine());
				System.out.println("Inserisci il messaggio che vuoi inviare: ");
				String testoMsg=s_input.nextLine();

				boolean responseInvioMsg = MRH.inserisciMessaggio(testoMsg, idDestinatario);
				if(responseInvioMsg == true) {
					System.out.println("L'invio del messaggio e' andato a buon fine. ");
				}else {
					System.out.println("L'invio del messaggio non e' andato a buon fine. ");
				}
				break;

			case 5: //CASO MOSTRA PRENOTAZIONI
				JH.visualizzaPrenotazioniGestore();
				break;

			case 6: //CASO MOSTRA NOTIFICHE
				MRH.visualizzaMessaggi();
				break;

			case 7: //CASO MOSTRA RECENSIONI
				MRH.visualizzaRecensioni();
				break;
				
			case 8:
				System.out.println("Inserisca l'id della prenotazione di cui vuoi cambiare lo stato: ");
				int idPrenotazioneCambioStato=Integer.parseInt(s_input.nextLine());
				
				boolean resCambio = JH.cambioStatoDur(idPrenotazioneCambioStato);
				
				if(resCambio) {
					System.out.println("Il cambio dello stato e' andato a buon fine. ");
				}else {
					System.out.println("Il cambio dello stato non e' andato a buon fine. Rivedi l'id della prenotazione di cui vuoi cambiare lo stato o lo stato attuale della prenotazione. ");
				}
				break;
				
			case 9:
				System.out.println("Inserisca l'id della prenotazione di cui vuoi cambiare lo stato: ");
				int idPrenotazioneCambioStato1=Integer.parseInt(s_input.nextLine());
				boolean resCambio1 = JH.cambioStatoFin(idPrenotazioneCambioStato1);
				if(resCambio1) {
					System.out.println("Il cambio dello stato e' andato a buon fine. ");
				}else {
					System.out.println("Il cambio dello stato non e' andato a buon fine. Rivedi l'id della prenotazione di cui vuoi cambiare lo stato o lo stato attuale della prenotazione. ");
				}
				break;

			}
		}
		//s_input.close();
	}

}
