package util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Timelord {
	
    public static long calculateDelayUntilNextHour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextHour = now.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        return now.until(nextHour, ChronoUnit.SECONDS);
    }


}
