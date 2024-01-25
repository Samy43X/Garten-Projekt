package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateHandler {
	protected LocalDate currentDate = LocalDate.now();
	protected LocalTime currentTime = LocalTime.now();
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    protected String formattedDate = currentDate.format(formatter);
    protected int currentHour = currentTime.getHour();
 

    public int getCurrentHour() {
		return currentHour;
	}

	public void setCurrentHour(int currentHour) {
		this.currentHour = currentHour;
	}

	public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }


    

}
