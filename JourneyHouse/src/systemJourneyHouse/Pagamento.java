package systemJourneyHouse;
import libraries.CreditCardLib;

public class Pagamento {
	private String metodo;
	private long numeroCarta;

	public Pagamento(String metodo, long numeroCarta) {
		this.metodo = metodo;
		this.numeroCarta = numeroCarta;
	}

	public Pagamento(String metodo) {
		this.metodo = metodo;
	}

	public boolean verificaCartaCredito() {
		CreditCardLib lib = new CreditCardLib();
		long numeroCarta = this.numeroCarta;
		
		  return (lib.getSize(numeroCarta) >= 13 &&
	               lib.getSize(numeroCarta) <= 16) &&
	               (lib.prefixMatched(numeroCarta, 4) ||
	                lib.prefixMatched(numeroCarta, 5) ||
	                lib.prefixMatched(numeroCarta, 37) ||
	                lib.prefixMatched(numeroCarta, 6)) &&
	              ((lib.sumOfDoubleEvenPlace(numeroCarta) +
	                lib.sumOfOddPlace(numeroCarta)) % 10 == 0);

	}
}
