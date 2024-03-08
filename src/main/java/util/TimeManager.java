package util;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jdbi.v3.core.Jdbi;

import db.dao.DaoManager;
import objects.Data;

public abstract class TimeManager {
	
	
	public static void insertWetterAPIAtFullHour(Jdbi jdbi, Data wetterdaten) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
             
        //long delayUntilNextHour = Timelord.calculateDelayUntilNextHour();
        long delayUntilNextHour = 20;

        scheduler.scheduleAtFixedRate(() -> DaoManager.getWetterAPIDao().insertIntoWetterAPI(wetterdaten),
                delayUntilNextHour, TimeUnit.HOURS.toSeconds(1), TimeUnit.SECONDS);
        System.out.println("Weather data inserted.");
    }


	
	}



