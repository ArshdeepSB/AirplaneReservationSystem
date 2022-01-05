// ARSHDEEP BADHAN     
public class Passenger {
    String name;
    String passportNum;
    String seatNum;
    String seatType;

    public Passenger(String name, String passportNum, String seatNum) {
        this.name = name;
        this.passportNum = passportNum;
        this.seatNum = seatNum;
    }

    public Passenger() {
        name = "";
        passportNum = "";
        seatNum = "";
    }

    public String getName() {
        return name;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public String getseatNum() {
        return seatNum;
    }

    public boolean equals(Passenger other) {
        //compares two passengers according to their name and passport number
        if (this.getPassportNum().equals(other.getPassportNum()) && this.getName().equals(other.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public void cancelSeat(Passenger p){
        p.seatType = "";
        p.seatNum = "";
    }

    public void reserveSeat(Passenger p, String seat){
        if(seat.charAt(seat.length() - 1) == '+'){
            p.seatType = LongHaulFlight.firstClass;
        }
        else{
            p.seatType = LongHaulFlight.economy;
        }
        p.seatNum = seat;
    }

    public String toString() {
        return name + " " + passportNum + " " +  seatNum;
    }

}
