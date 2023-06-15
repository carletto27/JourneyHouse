package ui;

import java.util.Scanner;

import systemJourneyHouse.JourneyHouse;
import systemJourneyHouse.LoginRegGestReportHandler;
import systemJourneyHouse.MessaggioRecensioneHandler;

public class ModalitàAdminUI {

	public ModalitàAdminUI() {
		super();
	}

	public void modalitàAdmin (JourneyHouse JH) {

		Scanner s_input = new Scanner(System.in);
		MessaggioRecensioneHandler MRH  = new MessaggioRecensioneHandler (JH);
		LoginRegGestReportHandler LRGH  = new LoginRegGestReportHandler (JH);

		int x=1;
		while(x==1){
			System.out.println("\nIn che sezione vuoi entrare? Digita:");
			System.out.println("1: Visualizza prenotazioni");
			System.out.println("2: Elimina recensione");
			System.out.println("3: Visualizza recensioni");
			System.out.println("4: Modifica e-mail di un utente");
			System.out.println("5: Modifica password di un utente");
			System.out.println("6: Visualizza tutti gli utenti registrati");
			System.out.println("7: Genera report del sistema");
			System.out.println("0: Esci");
			Integer choice=Integer.parseInt(s_input.nextLine());;

			switch(choice){

			case 0: //CASO ESCI
				x=0;
				break;

			case 1: //CASO VISUALIZZA PRENOTAZIONI
				JH.visualizzaPrenotazioniAdmin();
				break;

			case 2: //CASO ELIMINA RECENSIONE
				System.out.println("Inserisci l'id della recensione che vuoi eliminare. Se non ricordi l'id della recensione puoi vederlo su Mostra Recensioni: ");
				int idRecensioneElim = Integer.parseInt(s_input.nextLine());

				boolean responseElim = MRH.eliminaRecensioneAdmin(idRecensioneElim);
				if(responseElim == true) {
					System.out.println("L'eliminazione della recensione e' andata a buon fine. ");
				}else {
					System.out.println("L'eliminazione della recensione non e' andata a buon fine. ");
				}
				break;

			case 3: //VISUALIZZA RECENSIONI
				MRH.visualizzaRecensioni();
				break;

			case 4: //CASO MODIFICA E-MAIL
				System.out.println("Inserisci l'id dell'utente a cui vuoi modificare l'e-mail:");
				int idUtenteMod=Integer.parseInt(s_input.nextLine());
				System.out.println("Inserisci la nuova e-mail dell'utente:");
				String emailMod=s_input.nextLine();
				int check = emailMod.indexOf('@');
				if (check == -1) {
					System.out.println("L'email inserita non e' valida");
					break;
				}
				boolean responseModEmail = LRGH.modificaEmail(idUtenteMod, emailMod);
				
				if(responseModEmail == true) {
					System.out.println("La modifica e' andata a buon fine");
				}else {
					System.out.println("La modifica non e' andata a buon fine");
				}

				break;

			case 5: //CASO MODIFICA PASSWORD
				System.out.println("Inserisci l'id dell'utente a cui vuoi modificare la password:");
				int idUtenteModPs=Integer.parseInt(s_input.nextLine());
				System.out.println("Inserisci la nuova password dell'utente:");
				String passwordMod=s_input.nextLine();
				boolean responseModPassword = LRGH.modificaPassword(idUtenteModPs, passwordMod);
				
				if(responseModPassword == true) {
					System.out.println("La modifica e' andata a buon fine");
				}else {
					System.out.println("La modifica non e' andata a buon fine");
				}

				break;

			case 6: //CASO VISUALIZZA TUTTI GLI UTENTI
				LRGH.visualizzaUtenti();
				break;
			
			case 7: //CASO GENERA REPORT
				boolean responseReport = LRGH.generaReport();
				if(responseReport == true) {
					System.out.println("La generazione del report e' andata a buon fine");
				}else {
					System.out.println("La generazione del report non e' andata a buon fine");
				}
				
				break;

			}
		}
		//s_input.close();
	}
}
