package calcDataModule;

public class Tester {

	public static void main(String[] args){
		
		String expression = "16 / 5 + 9";
		double value = StringCalculator.calculate(expression);
		
		System.out.println("\n\n" + value);
	}
}
