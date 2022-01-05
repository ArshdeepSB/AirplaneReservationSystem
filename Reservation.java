/* ARSHDEEP BADHAN 
 * A simple class to model an electronic airline flight reservation
 */
public class Reservation {
	String flightNum;
	String flightInfo;
	boolean firstClass;	
	String passengerName; 
	String passengerPassport; 
	String seatNum; 

	//Consider Deleting Later 
	public Reservation(String flightNum, String info) {
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.firstClass = false;
	}

	public Reservation(String flightNum, String info, String passengerName, String passengerPassport, String seat) {
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.firstClass = false;
		this.passengerName = passengerName; 
		this.passengerPassport = passengerPassport;
		this.seatNum = seat;
	}

	public boolean isFirstClass() {
		return firstClass;
	}

	public void setFirstClass() {
		this.firstClass = true;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public String getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	public String getPassengerName(){
		return passengerName;
	}

	//might not be needed
	public void setPassengerName(String name){
		passengerName = name;
	}

	public String getPassengerPassport(){
		return passengerPassport;
	}

	//might not be needed
	public void setPassengerPassport(String passport){
		passengerPassport = passport;
	}

	public String getSeat(){
		return seatNum;
	}

	//might not be needed
	public void setSeat(String seat){
		seatNum = seat;
	}

	public void print() {
		System.out.println(flightInfo +" "+ passengerName +" "+ passengerPassport + " "+ seatNum);
	}

	public boolean equals(Reservation other){
		if(this.flightNum.equals(other.getFlightNum()) && this.passengerName.equals(other.getPassengerName()) && this.passengerPassport.equals(other.getPassengerPassport())){
			return true;
		}
		else{
			return false;
		}
	}
}
