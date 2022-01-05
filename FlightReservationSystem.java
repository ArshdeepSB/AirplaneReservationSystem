import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!

public class FlightReservationSystem {
	public static void main(String[] args) {
		System.out.println("Flight System for one single day at YYZ");
		// Create a FlightManager object and catch any exceptions
		FlightManager manager = null;
		try {
			manager = new FlightManager();
		} 
		catch (IOException e1){	//for wrong input and output for a file
			System.out.println(e1.getMessage());
			System.exit(0);	//made it so that it does not run the program when this happens
		}
		catch(IndexOutOfBoundsException e2){ //This is for flightTimes[] incase the index is never found it defaults to -1
			System.out.println(e2.getMessage());
			System.exit(0);	//exits if this is thrown because the flight times will be wrong
		}

		


		// List of reservations that have been made
		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine()) {
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals(""))
				continue;

			// The command line is a scanner that scans the inputLine string
			// For example: list AC201
			Scanner commandLine = new Scanner(inputLine);

			// The action string is the command to be performed (e.g. list, cancel etc)
			String action = commandLine.next();

			if (action == null || action.equals(""))
				continue;

			if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			// List all flights
			else if (action.equalsIgnoreCase("LIST")) {
				manager.printAllFlights();
			}
			// Reserve a flight
			// Uses the format RES flightNum name passport seat
			else if (action.equalsIgnoreCase("RES")) {
				String flightNum = null;
				String name = null;
				String passport = null;
				String seat = null;
					try {
					//Gathers the necessary information from commandLine 
					flightNum = commandLine.next();
					name = commandLine.next();
					passport = commandLine.next();
					seat = commandLine.next();
					
					Reservation ReservedSeat;
						//can be a first class reservation or economy
						if(seat.charAt(seat.length()-1) == '+'){
							ReservedSeat = manager.reserveSeatOnFlight(flightNum, LongHaulFlight.firstClass, name,
							passport, seat);
						}
						else{
						ReservedSeat = manager.reserveSeatOnFlight(flightNum, LongHaulFlight.economy, name,
								passport, seat);
							
						}

						//incase the reserve seat method returned null
						if (ReservedSeat == null) {
							throw new NullPointerException("Something went wrong with reserving " + flightNum);
						}
						else{
							myReservations.add(ReservedSeat);	
						}

						manager.setPassenger(myReservations.get((myReservations.size() - 1)), name, passport, seat);
						ReservedSeat.print();
					} 
					// when exception occurs it makes sure to delete the reservaion and remove it from the arraylist
					catch (DublicatePassengerException e) {
						System.out.println( e.getMessage());
						manager.cancelReservation(flightNum);
						myReservations.remove(myReservations.size() - 1);
					}
					catch (SeatOccupiedException m){
						System.out.println(m.getMessage());
						manager.cancelReservation(flightNum);
						myReservations.remove(myReservations.size() - 1);
					}
					catch(NullPointerException f){
						System.out.println(f.getMessage());
					}
					catch(NoSuchElementException e1){
						System.out.println("Please follow format: RES flightNum name passport seat");
					}
			}

			//requires format as SEATS flightnum
			else if (action.equalsIgnoreCase("SEATS")) {
				String flightNum = null;

				try{
					flightNum = commandLine.next();
					manager.seatsAvailable(flightNum);
				}
				catch(NoSuchElementException e){
					System.out.println("Please follow format: SEATS flightnum");
				}
			}

			// Works as CANCEL flightNum passport
			else if (action.equalsIgnoreCase("CANCEL")) {

					String flightNum = null;
					try {
						flightNum = commandLine.next();
						String passport = commandLine.next();
						boolean removed = false; //used to makes sure the if statement was reached in the for loop
						for (int i = 0; i < myReservations.size(); i++) {
							if (myReservations.get(i).getFlightNum().equals(flightNum) && myReservations.get(i).getPassengerPassport().equals(passport)){
								myReservations.remove(i);
								removed = true;
							}
						}
						if(removed == false){ //incase no reservations are removed
							throw new PassengerNotInManifestException("Reservation for " + flightNum + " flight does not exist");
						}

						manager.cancelReservation(flightNum);
						manager.cancelPassengerReservation(flightNum, passport);
					} 
					catch (PassengerNotInManifestException e) {
						System.out.println(e.getMessage());

					}
					catch (SeatOccupiedException m){
						System.out.println(m.getMessage());
					}
					catch(NoSuchElementException e1){
						System.out.println("Please follow format: CANCEL flightNum passport");
					}
			}
			// Print all the reservations in array list myReservations
			// Works as MYRES
			else if (action.equalsIgnoreCase("MYRES")) {
				for (int i = 0; i < myReservations.size(); i++) { // loops through myReservations and prints them out
					myReservations.get(i).print();	
				}

			//Printes all Passengers in manifest
			//Uses format PASMAN flightnum
			} else if (action.equalsIgnoreCase("PASMAN")) {
			 	String flightNum = null;
					try {
						flightNum = commandLine.next();
						manager.printPassengerManifest(flightNum);
					} catch (PassengerNotInManifestException e) {
						System.out.println(e.getMessage());
					}
					catch(NoSuchElementException e1){
						System.out.println("Please follow format: PASMAN flightnum");
					}
			}

			System.out.print("\n>");
		}
	}

}
