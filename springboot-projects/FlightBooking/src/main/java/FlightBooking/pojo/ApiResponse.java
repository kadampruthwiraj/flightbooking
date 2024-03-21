package FlightBooking.pojo;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
	public ApiResponse(boolean b, String string) {
		this.success=b;
		this.message=string;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
