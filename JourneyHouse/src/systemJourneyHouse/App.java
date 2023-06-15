package systemJourneyHouse;

import java.util.Scanner;

import ui.ModalitàClienteUI;
import ui.ModalitàGestoreUI;
import ui.ModalitàAdminUI;

public class App {

	public static void main(String[] args) throws Exception {
		JourneyHouse JH= JourneyHouse.getIstance();
		Startup startup = new Startup(JH);
		ModalitàClienteUI MC = new ModalitàClienteUI();
		ModalitàGestoreUI MG = new ModalitàGestoreUI();
		ModalitàAdminUI MA = new ModalitàAdminUI();
		LoginRegGestReportHandler LRGH  = new LoginRegGestReportHandler (JH);

		try {
			Scanner s_input = new Scanner(System.in);

			while(true){
				System.out.println("\nBENVENUTO SU JourneyHouse:");
				System.out.println("\nIn che sezione vuoi entrare? Digita:");
				System.out.println("a: Sezione Login");
				System.out.println("b: Sezione Registrazione");
				System.out.println("c: Chiudi il sistema");
				String section=s_input.nextLine();

				switch(section){
				case "a": 
					int x=1;
					while(x==1){
						System.out.println("\nCosa vuoi fare? Digita:");
						System.out.println("1: Login cliente");
						System.out.println("2: Login gestore struttura");
						System.out.println("3: Login admin");
						System.out.println("0: Esci");
						Integer choice=Integer.parseInt(s_input.nextLine());

						switch(choice){
						case 0:
							x=0;
							break;
						case 1: //LOGIN CLIENTE:   
							System.out.println("Inserisci il tuo id:");
							int idCliente=Integer.parseInt(s_input.nextLine());   
							System.out.println("Inserisci la tua password:");
							String passwordCliente = s_input.nextLine();    

							boolean loginClienteResponse = LRGH.accessoCliente(idCliente, passwordCliente);			
							if (loginClienteResponse == true) {
								System.out.println("Accesso consentito");
								MC.modalitàCliente(JH);             
							}else {
								System.out.println("Accesso non consentito");
							}

							break;
							//FINE LOGIN GESTORE

						case 2: //LOGIN GESTORE:					
							System.out.println("Inserisci il tuo id:");
							int idGestore=Integer.parseInt(s_input.nextLine());   
							System.out.println("Inserisci la tua password:");
							String passwordGestore = s_input.nextLine();    

							boolean loginGestoreResponse = LRGH.accessoGestore(idGestore, passwordGestore);			
							if (loginGestoreResponse == true) {
								System.out.println("Accesso consentito");
								MG.modalitàGestore(JH);             
							}else {
								System.out.println("Accesso non consentito");
							}

							break;  
							//FINE LOGIN GESTORE

						case 3: //LOGIN ADMIN: 	
							System.out.println("Inserisci il tuo id:");
							int idAdmin=Integer.parseInt(s_input.nextLine());   
							System.out.println("Inserisci la tua password:");
							String passwordAdmin = s_input.nextLine();    

							boolean loginAdminResponse = LRGH.accessoAdmin(idAdmin, passwordAdmin);			
							if (loginAdminResponse == true) {
								System.out.println("Accesso consentito");
								MA.modalitàAdmin(JH);            
							}else {
								System.out.println("Accesso non consentito");
							}

							break;
							//FINE LOGIN ADMIN
						}
					}
					break;

				case "b":
					int y=1;
					while(y==1){
						System.out.println("\nCosa vuoi fare? Digita:");
						System.out.println("1: Registrazione cliente");
						System.out.println("2: Registrazione gestore struttura");
						System.out.println("0: Esci");	
						Integer choice=Integer.parseInt(s_input.nextLine());        
						switch(choice){
						case 0:
							y=0;
							break;
						case 1: //REGISTRAZIONE CLIENTE: 						
							System.out.println("Inserisci nome:");
							String nomeCliente=s_input.nextLine();
							System.out.println("Inserisci cognome:");
							String cognomeCliente=s_input.nextLine();                     
							System.out.println("Inserisci e-mail:");
							String emailCliente=s_input.nextLine();
							int check = emailCliente.indexOf('@');
							if (check == -1) {
								System.out.println("L'email inserita non e' valida");
								break;
							}
							System.out.println("Inserisci password:");
							String passwordCliente=s_input.nextLine();	

							LRGH.effettuaRegistrazioneCliente(nomeCliente, cognomeCliente, emailCliente, passwordCliente);
							MC.modalitàCliente(JH);

							break;
							//FINE REGISTRAZIONE CLIENTE

						case 2: //REGISTRAZIONE GESTORE:                     
							System.out.println("Inserisci nome:");
							String nomeGestore=s_input.nextLine();
							System.out.println("Inserisci cognome:");
							String cognomeGestore=s_input.nextLine();                     
							System.out.println("Inserisci e-mail:");
							String emailGestore=s_input.nextLine();
							int checkGestore = emailGestore.indexOf('@');
							if (checkGestore == -1) {
								System.out.println("L'email inserita non e' valida");
								break;
							}
							System.out.println("Inserisci password:");
							String passwordGestore=s_input.nextLine();	
							System.out.println("Inserisci il nome della tua struttura:");
							String nomeStruttura=s_input.nextLine();
							System.out.println("Inserisci la citta' dove risiede la tua struttura:");
							String cittaStruttura=s_input.nextLine();
							System.out.println("Inserisci l'indirizzo della tua struttura:");
							String indirizzoStruttura=s_input.nextLine();
							System.out.println("Inserisci il numero di telefono della tua struttura:");
							String telefonoStruttura=s_input.nextLine();

							LRGH.effettuaRegistrazioneGestore(nomeGestore, cognomeGestore, emailGestore, passwordGestore,nomeStruttura,cittaStruttura,indirizzoStruttura,telefonoStruttura );
							MG.modalitàGestore(JH);

							break;   
							//FINE REGISTRAZIONE GESTORE          
						}				
					}
					break;

				case "c":
					System.exit(0);
					break;
				}

			}
		}catch(Exception ex) {
			System.out.println("Selezione non consentita!");
			System.out.println("Chiudo il sistema");
			ex.printStackTrace();
			System.exit(0);
		}
	}		
}

