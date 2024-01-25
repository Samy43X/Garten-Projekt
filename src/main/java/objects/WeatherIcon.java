package objects;

public class WeatherIcon {
    private String weatherId;
    private String description;
    private String weatherCode;
    private byte[] icon;
    
    
    
	public String getWeatherId() {
		return weatherId;
	}
	public void setWeatherId(String weatherId) {
		this.weatherId = weatherId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode(String weatherCode) {
		this.weatherCode = weatherCode;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	} 
    
    
}

