
// ARSHDEEP BADHAN 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class FlightManager {
  // Contains list of Flights departing from Toronto in a single day
  TreeMap<String, Flight> flightMap = new TreeMap<String, Flight>();

  String[] cities = { "Dallas", "New York", "London", "Paris", "Tokyo" };
  final int DALLAS = 0;
  final int NEWYORK = 1;
  final int LONDON = 2;
  final int PARIS = 3;
  final int TOKYO = 4;

  // flight times in hours
  int[] flightTimes = { 3, // Dallas
      1, // New York
      7, // London
      8, // Paris
      16// Tokyo
  };

  // Contains list of available airplane types and their seat capacity
  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();

  // String errorMsg = null; // if a method finds an error (e.g. flight number not
  // found) set this string.
  // See video!

  Random random = new Random(); // random number generator - google "Java class Random". Use this in
                                // generateFlightNumber

  public FlightManager() throws FileNotFoundException {
    // DO NOT ALTER THIS CODE - THE TA'S WILL USE IT TO TEST YOUR PROGRAM
    // IN ASSIGNMENT 2 YOU WILL BE LOADING THIS INFORMATION FROM A FILE

    // Create some aircraft types with max seat capacities
    airplanes.add(new Aircraft(85, "Boeing 737"));
    airplanes.add(new Aircraft(180, "Airbus 320"));
    airplanes.add(new Aircraft(37, "Dash-8 100"));
    airplanes.add(new Aircraft(12, "Bombardier 5000"));
    airplanes.add(new Aircraft(592, 14, "Boeing 747"));

    ArrayList<Integer> aircraftSeats = new ArrayList<Integer>();
    Aircraft flightAircraft = new Aircraft();
    File file = new File("flights.txt");
    Scanner in = new Scanner(file);

    while (in.hasNextLine()) {

      String name = in.next();
      String flightNum = generateFlightNumber(name);
      String destination = in.next();
      String departureTime = in.next();
      int capacity = Integer.parseInt(in.next());

      // gets the closest aircraft thats just above the capacity
      for (Aircraft aircraft : airplanes) {
        aircraftSeats.add(aircraft.getNumSeats()); // add each aircraft seat capacity to arraylist
      }
      Collections.sort(aircraftSeats); // sort arraylist

      int i = 0;
      while (aircraftSeats.get(i) < capacity) { // loop through seats till its bigger than capacity
        i++;
      }
      for (Aircraft aircraft : airplanes) { // find the aircraft object with the same number of seats as above
        if (aircraftSeats.get(i) == aircraft.getNumSeats()) {
          flightAircraft = aircraft;
        }
      }

      // for flightTimes[]
      if (destination.contains("_")) { // takes out the "_" string if inside destination
        destination = destination.substring(0, destination.indexOf("_")) + " "
            + destination.substring(destination.indexOf("_") + 1, destination.length());
      }
      int timnum = -1; // throw exception if this stays -1
      for (int j = 0; j < cities.length; j++) { // loops through cities to find which one destination is
        if (destination.equals(cities[j])) {
          timnum = j; // the index will go into flightTimes to get time
        }
      }
      if (timnum == -1) {
        throw new IndexOutOfBoundsException("The destination is not updated in cities array");
      }
      Flight flight = new Flight(flightNum, name, destination, departureTime, flightTimes[timnum], flightAircraft);
      flightMap.put(flightNum, flight);

    }

    String flightNum = generateFlightNumber("Air Canada");
    Flight flight = new LongHaulFlight(flightNum, "Air Canada", "Tokyo", "2200", flightTimes[TOKYO], airplanes.get(4));
    flightMap.put(flightNum, flight);
  }

  /*
   * This private helper method generates and returns a flight number string from
   * the airline name parameter For example, if parameter string airline is
   * "Air Canada" the flight number should be "ACxxx" where xxx is a random 3
   * digit number between 101 and 300 (Hint: use class Random - see variable
   * random at top of class) you can assume every airline name is always 2 words.
   */
  /**
   * @param airline the name of airline(ex. Air Canada)
   * @return the airlineName the method makes
   */
  private String generateFlightNumber(String airline) {
    String airlineName = "";
    for (int i = 0; i < airline.length() - 1; i++) {
      if (Character.isUpperCase(airline.charAt(i))) { // Finds the uppercase letters in string
        airlineName += String.valueOf(airline.charAt(i)); // Stores it in airlineName
      }
    }
    airlineName += String.valueOf(random.nextInt(301 - 101) + 101); // gets a random value between 101 - 300
    return airlineName;
  }

  // Prints all flights in flights array list (see class Flight toString() method)
  // This one is done for you!
  public void printAllFlights() {
    for (String number : flightMap.keySet()) {
      System.out.println(flightMap.get(number));
    }
  }

  // Given a flight number (e.g. "UA220"), check to see if there are economy seats
  // available
  // if so return true, if not return false

  /**
   * Checks if flight has available seats
   * 
   * @param flightNum number of flight
   * @return boolean return true if there are available seats
   */
  public void seatsAvailable(String flightNum) {
    if (flightMap.keySet().contains(flightNum)) {
      flightMap.get(flightNum).printSeats();
    }
  }

  /**
   * makes reservation object and updates passenger count for a flight(economy or
   * first class)
   * 
   * @param flightNum number for the flight
   * @param seatType  type of seat(economy or first class)
   * @return Reservation the reservation object for a specific flight
   */
  public Reservation reserveSeatOnFlight(String flightNum, String seatType, String name, String passport, String seat) {
    // Check for valid flight number by searching through flights array list
    // If matching flight is not found, set instance variable errorMsg (see at top)
    // and return null
    if (flightMap.keySet().contains(flightNum)) {

      // If flight found
      if (seatType.equals(LongHaulFlight.firstClass)) {
        if (flightMap.get(flightNum) instanceof LongHaulFlight) { // checks if this is long haul flight
          LongHaulFlight newLongHaulFlight = (LongHaulFlight) flightMap.get(flightNum);// casts flight to longhaulflight
          newLongHaulFlight.reserveSeat(LongHaulFlight.firstClass); // method returns boolean

          Reservation reservation = new Reservation(flightNum, flightMap.get(flightNum).toString() + " FCL", name,
              passport, seat); // made new
          // reservation
          // object with
          // FCL since its
          // a first class
          // flight
          reservation.setFirstClass(); // sets variable of first class in reservation object
          return reservation;
        }

      } else { // for economy flight reservation
        flightMap.get(flightNum).reserveSeat();
        Reservation reservation = new Reservation(flightNum, flightMap.get(flightNum).toString(), name, passport, seat); // creats
        // reservation
        // object
        return reservation;
      }
    }
    return null;
  }

  /*
   * Given a Reservation object, cancel the seat on the flight
   */

  /**
   * Cancels reservation by removing seat from flight
   * 
   * @param res this is a Reservation object
   * @return boolean true if the reservation is cancelled
   */
  public void cancelReservation(String flightNum) {
    if (flightMap.keySet().contains(flightNum)) {
      flightMap.get(flightNum).cancelSeat(); // decreases passengers
    }
  }

  // Prints all aircraft in airplanes array list.
  // See class Aircraft for a print() method
  public void printAllAircraft() {
    for (int i = 0; i < airplanes.size(); i++) {
      airplanes.get(i).print();
    }
  }
  // PASSENGER RELATED CODE STARTS HERE !!!

  /**
   * finds the correct flight and executes a method that adds the passenger to a
   * specific flight
   * 
   * @param res      this is a Reservation
   * @param name     name of the passenger
   * @param passport passport of the passenger
   * @param seat     seat the passenger requests
   * @return void
   */
  public void setPassenger(Reservation res, String name, String passport, String seat) {
    String flightNum = res.getFlightNum();
    if (flightMap.keySet().contains(flightNum)) {
      flightMap.get(flightNum).addPassengerToFlight(name, passport, seat); // method returns boolean
    }
  }

  /**
   * grabs the list of passengers for a specific flight
   * 
   * @param res this is a Reservation
   * @return void
   */
  public void getFlightPassengers(Reservation res) {
    String flightNum = res.getFlightNum(); // gets the flight number for the reservation
    if (flightMap.keySet().contains(flightNum)) {
      System.out.println(flightMap.get(flightNum).getPassengerList()); // prints the arraylist of passengers
      return; // so it does not print again
    }
  }

  /**
   * cancels a passenger for a flight
   * 
   * @param res      this is a Reservation object
   * @param passport this is a passport string associated with passenger
   * @return void
   */
  public void cancelPassengerReservation(String flightNum, String passport) {
    if (flightMap.keySet().contains(flightNum)) {
      ArrayList<Passenger> passList = flightMap.get(flightNum).getPassengerManifest(); // grabs the
                                                                                       // passengerlist(arraylist) for
                                                                                       // the
      // flight
      for (int j = 0; j < passList.size(); j++) {
        if (passList.get(j).getPassportNum().equals(passport)) { // finds a passenger with the matching passport
                                                                 // string
          flightMap.get(flightNum).cancelPassengerFromFlight(passList.get(j)); // once found it cancels passenger from
                                                                               // flight
        }
      }
    }
  }

  /**
   * prints manifest arraylist from flight class
   * 
   * @param flightNum this is the flight number of flight
   * @return void
   */
  public void printPassengerManifest(String flightNum) {
    if (flightMap.keySet().contains(flightNum)) {
      flightMap.get(flightNum).printPassengerManifest();
    }
  }
}
