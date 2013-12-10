package ca.raphabot.disastersaroundyou;

import java.sql.Timestamp;

public class Disaster {
	private int id;
	private String description;
	private int type;
	private Timestamp started;
	private Timestamp ended;
	private double lat;
	private double lng;
	private double radio;
	
	public Disaster(int id, String description, int type, Timestamp started,
			Timestamp ended, int lat, int lng, int radio) {
		super();
		this.id = id;
		this.description = description;
		this.type = type;
		this.started = started;
		this.ended = ended;
		this.lat = lat;
		this.lng = lng;
		this.radio = radio;
	}
	
	
	public Disaster(String id, String description, String type,	String started, String ended, String lat, String lng, String radio) {
		this.id = Integer.parseInt(id);
		this.description = description;
		this.type = Integer.parseInt(type);
		//this.started = (Timestamp)(started);
		//this.ended = ended;
		this.lat = Double.parseDouble(lat);
		this.lng = Integer.parseInt(lng);
		this.radio =Integer.parseInt(radio);
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Timestamp getStarted() {
		return started;
	}
	public void setStarted(Timestamp started) {
		this.started = started;
	}
	public Timestamp getEnded() {
		return ended;
	}
	public void setEnded(Timestamp ended) {
		this.ended = ended;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(int lng) {
		this.lng = lng;
	}
	public double getRadio() {
		return radio;
	}
	public void setRadio(int radio) {
		this.radio = radio;
	}
	
	@Override
	public String toString(){
		int id = this.id;
		return String.valueOf(id);
	}

}
