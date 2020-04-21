package com.smoothstack.utopia.counter.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Flight")
public class Flight implements Serializable {
	
	private static final long serialVersionUID = 6724683215392686183L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "flightId")
	private Integer flightId;
	
	@ManyToOne
    @JoinColumn(name="sourceAirport")
	private Airport sourceAirport;
	
	@ManyToOne
	@JoinColumn(name = "destinationAirport")
	private Airport destinationAirport;
	
	@Column(name = "departureTime")
	private LocalTime departureTime;
	
	@Column(name = "arrivalTime")
	private LocalTime arrivalTime;
	
	@Column(name = "departureDate")
	private LocalDate departureDate;
	
	@Column(name = "arrivalDate")
	private LocalDate arrivalDate;
	
	@Column(name = "cost")
	private BigDecimal cost;
	
	@Column(name = "seatsAvailable")
	private Integer seatsAvailable;

	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	public Airport getSourceAirport() {
		return sourceAirport;
	}

	public void setSourceAirport(Airport sourceAirport) {
		this.sourceAirport = sourceAirport;
	}

	public Airport getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Integer getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(Integer seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrivalDate, arrivalTime, cost, departureDate, departureTime, destinationAirport, flightId,
				seatsAvailable, sourceAirport);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(arrivalDate, other.arrivalDate) && Objects.equals(arrivalTime, other.arrivalTime)
				&& Objects.equals(cost, other.cost) && Objects.equals(departureDate, other.departureDate)
				&& Objects.equals(departureTime, other.departureTime)
				&& Objects.equals(destinationAirport, other.destinationAirport)
				&& Objects.equals(flightId, other.flightId) && Objects.equals(seatsAvailable, other.seatsAvailable)
				&& Objects.equals(sourceAirport, other.sourceAirport);
	}

}
