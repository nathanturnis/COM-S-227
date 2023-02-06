package hw1;

/**
 * Models a functioning printer that can print pages, add or remove pages, and replace/remove the paper tray.
 * 
 * @author Nathan Turnis
 *
 */
public class Printer {
	
	/**
	 * The number of sheets available for the printer to print.
	 */
	private int numSheetsAvailable = 0;
	/**
	 * The current number of sheets in the printer tray.
	 */
	private int numSheetsInTray = 0;
	/**
	 * The max sheets the printer tray can hold.
	 */
	private int maxSheets;
	/**
	 * The total amount of pages printed by the printer since it was made.
	 */
	private int totalPrintedPages = 0;
	/**
	 * How many pages that are in a document.
	 */
	private int pagesToPrint = 0;
	/**
	 * The next page number of the document that will be printed. 0 indicates the beginning of the document.
	 */
	private int nextPageNum;

	/**
	 * Creates a new printer, given the maximum number of sheets a tray can hold. Initially the tray is empty and has not printed any pages.
	 * 
	 * @param trayCapacity
	 * 		how many sheets of paper a tray can hold
	 */
	public Printer(int trayCapacity) {
		maxSheets = trayCapacity;
	}
	
	/**
	 * Starts a new print job to make copies of a document that is a specified page length (doucmentPages). Updates the next page to print as page 0.
	 * 
	 * @param documentPages
	 * 		how many pages are in a document
	 */
	public void startPrintJob(int documentPages) {
		pagesToPrint = documentPages;
		nextPageNum = 0;
	}
	
	/**
	 * Returns the number of sheets available to the printer for printing.
	 * 
	 * @return
	 * 		number of sheets available to the printer to print.
	 */
	public int getSheetsAvailable() {
		return numSheetsAvailable;
	}
	
	/**
	 * Returns the next page number of the document to be printed.
	 * 
	 * @return
	 * 		the next page number of the document
	 */
	public int getNextPage() {
		return nextPageNum;
	}
	
	/**
	 * Returns the count of all pages printed by the printer since it was created.
	 * 
	 * @return
	 * 		the total amount of pages printed by the printer since it was created
	 */
	public int getTotalPages() {
		return totalPrintedPages;
	}
	
	/**
	 * Simulates the printer printing a page. The number pages printed is either one or zero depending on whether there is at least one sheet of paper available to the printer.
	 *  Increments the total page count of the printer by the number of pages printed. 
	 *  Advances the next page to print by the number of pages printed (possibly wrapping around to page zero). 
	 *  The number of pages available to the printer and in the tray are also updated accordingly. 
	 * 
	 */
	public void printPage() {
		
		numSheetsInTray -= Math.min(numSheetsAvailable, 1);
		totalPrintedPages += Math.min(numSheetsAvailable, 1);
		nextPageNum += Math.min(numSheetsAvailable, 1);
		nextPageNum %= pagesToPrint;
		numSheetsAvailable -= Math.min(numSheetsAvailable, 1);
		
	}
	
	/**
	 * Removes the paper tray from the printer, making the number of sheets available to the printer as 0.
	 * 
	 */
	public void removeTray() {
		numSheetsInTray = numSheetsAvailable;
		numSheetsAvailable = 0;
	}
	
	/**
	 * Replaces the paper tray in the printer. Makes the number of sheets available to the printer as the number of sheets in the tray.
	 * 
	 */
	public void replaceTray() {
		numSheetsAvailable = numSheetsInTray;
	}
	
	/**
	 * Simulates removing the tray, adding the given number of sheets (up to the maximum capacity of the tray), and replacing the tray in the printer. 
	 * 
	 * @param sheets
	 * 		the number of sheets to add
	 */
	public void addPaper(int sheets) {
		
		//removeTray();
		numSheetsAvailable = numSheetsInTray;
		numSheetsAvailable += sheets;
		numSheetsAvailable = Math.min(maxSheets, numSheetsAvailable);
		numSheetsInTray += sheets;
		numSheetsInTray = Math.min(maxSheets, numSheetsInTray);
		//replaceTray();
	}
	
	/**
	 * Simulates removing the tray, removing the given number of sheets (but not allowing the sheets to go below zero), and replacing the tray in the printer. 
	 * 
	 * @param sheets
	 * 		the number of sheets to remove
	 */
	public void removePaper(int sheets) {
		numSheetsInTray -= sheets;
		numSheetsInTray = Math.max(0, numSheetsInTray);
		replaceTray();

	}

}
