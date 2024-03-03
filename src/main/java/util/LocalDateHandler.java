package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateHandler {
	protected LocalDate currentDate = LocalDate.now();
	protected LocalTime currentTime = LocalTime.now();
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    protected String formattedDate = currentDate.format(formatter);
    protected String currentHour = String.valueOf(currentTime.getHour());
 

    public String getCurrentHour() {
		return currentHour;
	}

	public void setCurrentHour(String currentHour) {
		this.currentHour = currentHour;
	}

	public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }


    

}
