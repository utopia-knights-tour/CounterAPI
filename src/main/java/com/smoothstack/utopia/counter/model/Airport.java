package com.smoothstack.utopia.counter.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Airport")
public class Airport implements Serializable {

	private static final long serialVersionUID = -7129282699553616986L;

	@Id
	@Column(name = "airportCode")
	@Length(min = 3, max = 3)
	private String airportCode;
	
	@Column(name = "airportName")
	private String airportName;
	
	@Column(name = "airportLocation")
	private String airportLocation;

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getAirportLocation() {
		return airportLocation;
	}

	public void setAirportLocation(String airportLocation) {
		this.airportLocation = airportLocation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(airportCode, airportLocation, airportName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		return Objects.equals(airportCode, other.airportCode) && Objects.equals(airportLocation, other.airportLocation)
				&& Objects.equals(airportName, other.airportName);
	}
	
}
