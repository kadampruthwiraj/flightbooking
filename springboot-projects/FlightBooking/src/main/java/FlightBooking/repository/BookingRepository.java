package FlightBooking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import FlightBooking.pojo.Booking;

public interface BookingRepository extends MongoRepository<Booking,Long> {
	
	  @Query("{'departue_date' : :#{#departure_date}}") 
	  List<Booking> findByDate(@Param("departure_date") Date departure_date);
	  
	  @Query("{'passenger.passenger_name':{'$regex' : ?0, '$options' : 'i'}}") 
	  List<Booking> findByPassenger_name(String passenger_name);
	 
}
