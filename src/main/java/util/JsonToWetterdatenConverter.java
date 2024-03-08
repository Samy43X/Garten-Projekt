package util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import objects.Data;

public class JsonToWetterdatenConverter {
	private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	JsonNode data;

  /*  public Wetterdaten convertJsonToWetterdaten(String jsonString) {
        try {
        	JsonNode root = objectMapper.readTree(jsonString);
        	if(root.has("data")) {data = root.get("data");}
        	objectMapper.readValue(data, Wetterdaten.class);
            return objectMapper.readValue(jsonString, Wetterdaten.class);
        } catch (Exception e) {
            e.printStackTrace(); 
            return null;
        }
    }*/

    
    public Data convertJsonToWetterAPI(String jsonString) {
    	JsonNode data = null;

    		
    		 Gson gson = new Gson();
    	        Type type = new TypeToken<Map<String, Object>>(){}.getType();
    	        Map<String, Object> response = gson.fromJson(jsonString, type);
    	        Data weatherdata = new Data();

    	        // Jetzt enthält "response.get("data")" die Liste der Datenobjekte.
    	        List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.get("data");
	        	//System.out.println(weatherdata.getApp_temp()); 
    	        for (Map<String, Object> eintrag : dataList) {
    	            // Iteriere durch die Map für jeden Eintrag
    	            for (Map.Entry<String, Object> entry : eintrag.entrySet()) {
    	                String key = entry.getKey();
    	                Object value = entry.getValue();
    	                //System.out.println("Key: " + key + ", Value: " + value);
    	            }
    	        }

    	        Map<String, Object> datas = dataList.getFirst();
    	        
    	        	weatherdata.setApp_temp(datas.get("app_temp").toString());   
    	        	weatherdata.setAqi(datas.get("aqi").toString());
    	        	weatherdata.setAqi(datas.get("aqi").toString());
    				weatherdata.setCity_name(datas.get("city_name").toString());
    				weatherdata.setClouds(datas.get("clouds").toString());
    				weatherdata.setCountry_code(datas.get("country_code").toString());
    				weatherdata.setDatetime(datas.get("datetime").toString());
    				weatherdata.setDewpt(datas.get("dewpt").toString());
    				weatherdata.setDhi(datas.get("dhi").toString());
    				weatherdata.setDni(datas.get("dni").toString());
    				weatherdata.setElev_angle(datas.get("elev_angle").toString());
    				weatherdata.setGhi(datas.get("ghi").toString());
    				weatherdata.setGust(datas.get("gust").toString());
    				weatherdata.setH_angle(datas.get("h_angle").toString());
    				weatherdata.setLat(datas.get("lat").toString());
    				weatherdata.setLon(datas.get("lon").toString());
    				weatherdata.setOb_time(datas.get("ob_time").toString());
    				weatherdata.setPod(datas.get("pod").toString());
    				weatherdata.setPrecip(datas.get("precip").toString());
    				weatherdata.setPres(datas.get("pres").toString());
    				weatherdata.setRh(datas.get("rh").toString());
    				weatherdata.setSlp(datas.get("slp").toString());
    				weatherdata.setSnow(datas.get("snow").toString());
    				weatherdata.setSolar_rad(datas.get("solar_rad").toString());
    				String weather = datas.get("weather").toString();
    				Map<String, String> weatherMap = getMap(weather);
    				weatherdata.setDescription(weatherMap.get("description"));
    				weatherdata.setCode(weatherMap.get("code"));
    				weatherdata.setIcon(weatherMap.get("icon"));	
    				System.out.println(weatherdata.getDescription());
    				System.out.println(weatherdata.getCode());
    				System.out.println(weatherdata.getIcon());
    		        
    				weatherdata.setState_code(datas.get("state_code").toString());
    				weatherdata.setStation(datas.get("station").toString());
    				weatherdata.setSunrise(datas.get("sunrise").toString());
    				weatherdata.setSunset(datas.get("sunset").toString());
    				weatherdata.setTemp(datas.get("temp").toString());
    				weatherdata.setTimezone(datas.get("timezone").toString());
    				weatherdata.setTs(datas.get("ts").toString());
    				weatherdata.setUv(datas.get("uv").toString());
    				weatherdata.setVis(datas.get("vis").toString());
    				weatherdata.setWind_cdir(datas.get("wind_cdir").toString());
    				weatherdata.setWind_cdir_full(datas.get("wind_cdir_full").toString());
    				weatherdata.setWind_dir(datas.get("wind_dir").toString());
    				weatherdata.setWind_spd(datas.get("wind_spd").toString());						    	        	
    				
    	        return weatherdata;
    	       
    	    
        	
        }
    
    public Map<String, String> getMap(String string) {
    	String[] keyValuePairStrings = string.substring(1, string.length() - 1).split(", ");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairStrings) {
            String[] keyValue = pair.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }
    }

    
