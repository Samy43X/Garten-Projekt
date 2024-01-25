package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import objects.WeatherIcon;

public class CSVReaderClass {

	String folderPath = "C:\\programs\\Icons";
	String csvFilePath = "C:\\programs\\Icons\\WeathericonTable.csv";
	public List<WeatherIcon> readCSVAndCreateObjects() throws IOException {
	   // Reader reader = null;
		BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
	    
	        //reader = new CSVReader(new FileReader(csvFilePath));
	        List<String[]> csvDataList = new ArrayList<>();

	        
	            String line;

	            while ((line = reader.readLine()) != null) {

	                String[] entry = line.split(";");
	                csvDataList.add(entry);
	            }

	        return createObjectsFromCSV(csvDataList);
	}

    private List<WeatherIcon> createObjectsFromCSV(List<String[]> csvData) {

        return csvData.stream()
                .map(row -> {
                	WeatherIcon icon = new WeatherIcon();
                	
                    icon.setWeatherId((row[0]));
                    icon.setDescription(row[1]);
                    icon.setWeatherCode(row[2]);
                    try {
						icon.setIcon(loadImageAsByteArray(icon.getWeatherCode()));
					} catch (IOException e) {
						e.printStackTrace();
					}
                    return icon;
                })
                .toList();
    }
	
    public byte[] loadImageAsByteArray(String imageName) throws IOException {
    	String imageNamePlusEnding = imageName + ".png";
        Path imagePath = Paths.get(folderPath, imageNamePlusEnding);
        
        return Files.readAllBytes(imagePath);
            
    
    
}
}
