// ARSHDEEP BADHAN 
import java.util.ArrayList;
import java.util.TreeMap;

/* 
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *  
 *  This class models a simple flight that has only economy seats
 */
public class Flight {
	public enum Status {
		DELAYED, ONTIME, ARRIVED, INFLIGHT
	};

	public static enum FlightType {
		SHORTHAUL, MEDIUMHAUL, LONGHAUL
	};

	String flightNum;
	String airline;
	String origin, dest;
	String departureTime;
	Status status; // see enum Status above. google this to see how to use it
	int flightDuration;
	Aircraft aircraft;
	FlightType flightType;
	// Check if this was always protected
	protected int passengers; // count of (economy) passengers on this flight - initially 0
	ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap;

	public Flight() {
		// write code to initialize instance variables to default values***
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.departureTime = "";
		this.flightDuration = 0;
		this.origin = "Toronto";
		this.aircraft = new Aircraft(); // default is just initialized to empty constructor
		this.passengers = 0;
		this.status = Status.ONTIME;
		this.flightType = FlightType.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}

	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration,
			Aircraft aircraft) {
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		passengers = 0;
		status = Status.ONTIME;
		this.flightType = FlightType.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getFlightDuration() {
		return flightDuration;
	}

	public void setFlightDuration(int dur) {
		this.flightDuration = dur;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public FlightType getFlightType() {
		return flightType;
	}

	// To get the 2d array of seats for aircraft and print them
	public String[][] getAircraftSeats() {
		String[][] seats = aircraft.getFirstClassSeatLayout();
		return seats;
	}

	/**
   * prints a 2D array of flight seats to console
   * @return void since it is printing to console
   */
	public void printSeats() {
		String[][] seats = this.getAircraftSeats();
		
		// REPLACE ALL RESERVED SEATS WITH "XX"	
			for (String key : seatMap.keySet()) {
				String resSeat = key;
				for (int i = 0; i < seats.length; i++) {
					for (int j = 0; j < seats[0].length; j++) {
						if (seats[i][j].equals(resSeat)) {
							seats[i][j] = "XX";
						}
					}
				}

			}

		// FOR PRINTING
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[0].length; j++) {
				System.out.print(seats[i][j] + " ");
			}
			if (i == 1) {
				System.out.println();
			}
			System.out.println();
		}

		System.out.println("XX = Occupied + = First Class");
	}

	/*
	 * Cancel a seat - essentially reduce the passenger count by 1. Make sure the
	 * count does not fall below 0 (see instance variable passenger)
	 */
	public void cancelSeat() {
		if (passengers > 0) {
			passengers--;
		}
		else{
			throw new SeatOccupiedException("There are no passengers on this flight");
		}
	}

	/*
	 * reserve a seat on this flight - essentially increases the passenger count by
	 * 1 only if there is room for more economy passengers on the aircraft used for
	 * this flight (see instance variables above)
	 */
	public void reserveSeat() {
		if (passengers < aircraft.getNumSeats()) {
			passengers++;
			// return true;
		} else {
			throw new SeatOccupiedException("The Flight is full");
		}
	}

	public void printPassengerManifest() {
		//double check
		if(manifest.isEmpty()){
			throw new PassengerNotInManifestException("There are currently no passengers in this flight");
		}
		for (int i = 0; i < manifest.size(); i++) {
			System.out.println(manifest.get(i));
		}

	}

	public ArrayList<Passenger> getPassengerManifest(){
		return manifest;
	}

	public String toString() {
		return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime
				+ "\t Duration: " + flightDuration + "\t Status: " + status;
	}

	public ArrayList<Passenger> getPassengerList() {
		return passengerList;
	}

	// PASSENGER METHODS START HERE !!!!

	// Adds passenger objects to the list of passengers in the flight object
	/**
	 * Adds passenger to a arraylist of passengers(passengerList)
	 * 
	 * @param name     name of the passenger
	 * @param passport passport of the passenger
	 * @param seatNum  the passenger's seat number
	 * @return void
	 */
	public void addPassengerToFlight(String name, String passport, String seatNum) {
		boolean exists = false;
		Passenger a = new Passenger(name, passport, seatNum);
		for (int i = 0; i < manifest.size(); i++) {// finds if the same passenger exists by matching passport
														// number to exisiting passengers
			if (manifest.get(i).getPassportNum().equals(passport)) {

				throw new DublicatePassengerException("Duplicate Passenger " + name + " " + passport);

			} else if (manifest.get(i).getseatNum().equals(seatNum)) {

				throw new SeatOccupiedException("Seat " + seatNum + " already occupied");

			}
		}
	
		String[][] tempSeat = this.getAircraftSeats();

			// for determining first class or economy
		for (int i = 0; i < tempSeat.length; i++) {
			for (int j = 0; j < tempSeat[0].length; j++) {
				if(tempSeat[i][j].equals(seatNum)){
					manifest.add(a);
					seatMap.put(seatNum, a);
					exists = true;
				}
			}
		}
		if(exists == false){
			throw new SeatOccupiedException("This seat does not exist on this plane");
		}
	}

	/**
	 * removes input passenger from arraylist of passengers(passengerList)
	 * 
	 * @param a this is a passenger object
	 * @return void
	 */
	public void cancelPassengerFromFlight(Passenger a) {
		if (manifest.contains(a)) {
			manifest.remove(a);

			if(seatMap.isEmpty()){
				throw new SeatOccupiedException("There are no reserved seats on this flight");
			}
			if(seatMap.keySet() == null){
					throw new PassengerNotInManifestException("Cannot cancel passenger " + a.getName() + " , seat not found");
			}
			for (String key : seatMap.keySet()) {
				if(seatMap.get(key).equals(a)){
					seatMap.remove(key);
					break;
				}
			}
		} 
		else {
			throw new PassengerNotInManifestException("Cannot cancel passenger " + a.getName() + " not found");
		}
	}

}


// ******* The custom exception classes are made beyond this point ************

class DublicatePassengerException extends RuntimeException {
    public DublicatePassengerException(){}
    public DublicatePassengerException(String message){
        super(message);
    }
}

class SeatOccupiedException extends RuntimeException{
    public SeatOccupiedException(){}
    public SeatOccupiedException(String message){
        super(message);
    }
}

class PassengerNotInManifestException extends RuntimeException{
    public PassengerNotInManifestException(){}
    public PassengerNotInManifestException(String message){
        super(message);
    }
}



