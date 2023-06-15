package systemJourneyHouse;

public interface Observer {
	public Messaggio update(Utente admin, Object o , Utente destinatario);
}
