package hw1;

public class PrinterTest {

	public static void main(String[] args) {
		
		Printer p = new Printer(10);
		p.addPaper(8);
		System.out.println(p.getSheetsAvailable());
		p.removeTray();
		System.out.println(p.getSheetsAvailable());
		p.addPaper(2);
		System.out.println(p.getSheetsAvailable());
		p.removePaper(3);
		System.out.println(p.getSheetsAvailable());
		
	}

}
