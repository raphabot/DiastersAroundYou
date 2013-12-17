package ca.raphabot.disastersaroundyou;

import java.sql.Timestamp;

import android.os.Parcel;
import android.os.Parcelable;

public class Disaster implements Parcelable{
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

	// The following methods that are required for using Parcelable
	private Disaster(Parcel in) {
        // This order must match the order in writeToParcel()
        id = Integer.parseInt(in.readString());
		description = in.readString();
		type = Integer.parseInt(in.readString());
		started = (Timestamp.valueOf(in.readString()));
		ended = (Timestamp.valueOf(in.readString()));;
		lat = Double.parseDouble(in.readString());
		lng = Double.parseDouble(in.readString());
		radio = Double.parseDouble(in.readString());
        
    }
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(description);
		out.writeInt(type);
		out.writeValue(null);
		out.writeString(null);
		out.writeDouble(lat);
		out.writeDouble(lng);
		out.writeDouble(radio);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

    // Just cut and paste this for now
    public static final Parcelable.Creator<Disaster> CREATOR = new Parcelable.Creator<Disaster>() {
        public Disaster createFromParcel(Parcel in) {
            return new Disaster(in);
        }

        public Disaster[] newArray(int size) {
            return new Disaster[size];
        }
    };

}
