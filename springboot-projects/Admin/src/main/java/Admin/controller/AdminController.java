package Admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import Admin.pojo.Flight;
import Admin.pojo.FlightData;

@RestController
@RequestMapping("/Admin")
public class AdminController {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	Environment env;

//For admin to get all the flights	
	@GetMapping("/AllFlights")
	@HystrixCommand(fallbackMethod = "GetAllFlightsFallback")
	public Flight[] getAllFlights() throws InterruptedException {
		Flight[] flight = restTemplate.getForObject(env.getProperty("baseurl_flightsearch") + "/Search/allFlights",
				Flight[].class);

		return flight;

	}

	private Flight[] GetAllFlightsFallback() {
		return null;

	}
//For admin to get all the flights from location to destination

	@GetMapping("/find/{departure_location}/{arrival_location}")
	@HystrixCommand(fallbackMethod = "GetFlightFallback")
	public Flight[] getFlightByData(@PathVariable("departure_location") String dep_loc,
			@PathVariable("arrival_location") String arr_loc) throws InterruptedException {
		Flight[] flight = restTemplate.getForObject(env.getProperty("baseurl_flightsearch")+"/Search/find/" + dep_loc + "/" + arr_loc,
				Flight[].class);
		
		return flight;

	}

	private Flight[] GetFlightFallback(String dep_loc, String arr_loc) {
		return null;

	}

//For admin to add new flight to the list	
//addition of particular  flight 
	@PostMapping(value = "/addFlights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "AddFlightFallback")
	public Flight addFlight(@RequestBody Flight flight) throws InterruptedException {
		restTemplate.postForObject(env.getProperty("baseurl_flightsearch")+"/Search/addFlights", flight, Flight.class);
		
		return flight;
	}

	private Flight AddFlightFallback(Flight flight) {
		return flight;

	}

//For admin to update specific flights	
//updation of particular flight(messaging)
	@PutMapping("/updateFlightsData/{id}")
	@HystrixCommand(fallbackMethod = "UpdateFlightFallback")
	public String updateFlight(@RequestBody FlightData flight, @PathVariable("id") int flights_id)
			throws InterruptedException {
		restTemplate.put(env.getProperty("baseurl_flightsearch")+"/Search/updateFlightsData/" + flights_id, flight);
		
		return "Flight: " + flight.getFlight_id() + "updated successfully ";

	}

	private String UpdateFlightFallback(FlightData flight, int flights_id) {
		return "Request fails. It takes long time to response";

	}

//cancellation of particular flight(messaging)
}
