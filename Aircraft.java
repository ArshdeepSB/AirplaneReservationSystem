/* ARSHDEEP BADHAN
 *  
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of forst class seats 
 * 
 * Add code such that class Aircraft implements the Comparable interface
 * Compare two Aircraft objects by first comparing the number of economy seats. If the number is equal, then compare the
 * number of first class seats 
 */
import java.util.Arrays;
public class Aircraft {
	int numEconomySeats;
	int numFirstClassSeats;
	String model;
	String[][] seatLayout;

	public Aircraft() {
	} // Aircraft constructor for the general general Flight constructor

	public Aircraft(int seats, String model) {
		this.numEconomySeats = seats;
		this.numFirstClassSeats = 0;
		this.model = model;	
	}

	public Aircraft(int economy, int firstClass, String model) {
		this.numEconomySeats = economy;
		this.numFirstClassSeats = firstClass;
		this.model = model;
	}

	public int getNumSeats() {
		return numEconomySeats;
	}

	public int getTotalSeats() {
		return numEconomySeats + numFirstClassSeats;
	}

	public int getNumFirstClassSeats() {
		return numFirstClassSeats;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	/**
	 *  Prints the seat layout of the flight to the console 
	 * 
	 * @return void
	 */
	public String[][] getFirstClassSeatLayout(){
		int rows = 4; // rows are always 4
        int columns = this.getNumSeats()/4; //columns will be seats/4
		int  numberOfFirstClass = numFirstClassSeats;	//initialized with the aircraft object
		String[][] seats = new String[rows][columns]; 	

        for (int i = 0; i < columns; i++) { //i and j are reversed to create the 2d array colunm wise
            for (int j = 0; j < rows; j++) {
                char alphabet = (char)(j + 'A');	//For generating the alphabets
                seats[j][i] = (i+1) + "" + alphabet ;
                if(numberOfFirstClass > 0){ //for making the first class seats at the front of the plane
                    seats[j][i] += "+";
                    numberOfFirstClass--;
                }

            }
            
        }
		return seats;
	}

	public void print() {
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: "
				+ numFirstClassSeats);
	}
}
