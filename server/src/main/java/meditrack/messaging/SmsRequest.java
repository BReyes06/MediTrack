package meditrack.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SmsRequest {

    private final String phoneNumber;
    private final String message;

    public SmsRequest(@JsonProperty("phone") String phoneNumber,
                      @JsonProperty("message") String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SendRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
