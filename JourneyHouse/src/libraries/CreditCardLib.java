package libraries;

public class CreditCardLib {

	public CreditCardLib() {

	}
	
	public int getDigit(int number)
	{
		if (number < 9)
			return number;
		return number / 10 + number % 10;
	}

	public long getPrefix(long number, int k)
	{
		if (getSize(number) > k) {
			String num = number + "";
			return Long.parseLong(num.substring(0, k));
		}
		return number;
	}

	public boolean prefixMatched(long number, int d)
	{
		return getPrefix(number, getSize(d)) == d;
	}

	public int getSize(long d)
	{
		String num = d + "";
		return num.length();
	}

	public int sumOfOddPlace(long number)
	{
		int sum = 0;
		String num = number + "";
		for (int i = getSize(number) - 1; i >= 0; i -= 2)
			sum += Integer.parseInt(num.charAt(i) + "");       
		return sum;
	}


	public int sumOfDoubleEvenPlace(long number)
	{
		int sum = 0;
		String num = number + "";
		for (int i = getSize(number) - 2; i >= 0; i -= 2)
			sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

		return sum;
	}
}
