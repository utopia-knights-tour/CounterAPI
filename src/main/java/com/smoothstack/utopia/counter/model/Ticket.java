package com.smoothstack.utopia.counter.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Ticket", uniqueConstraints=
@UniqueConstraint(columnNames={"customerId", "flightId"}))
@SQLDelete(sql = "UPDATE Ticket SET canceled = b'1' WHERE ticketId = ?")
@Where(clause = "canceled = false")
public class Ticket implements Serializable {
	
	private static final long serialVersionUID = -6100768964665114216L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticketId")
	private Integer ticketId;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "customerId" )
	private Customer customer;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "flightId")
	private Flight flight;
	
	@ManyToOne
	@JoinColumn(name = "agencyId")
	private Agency agency;
	
	@Column(name = "canceled")
	private Boolean canceled;

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Boolean getCanceled() {
		return canceled;
	}

	public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agency, canceled, customer, flight, ticketId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(canceled, other.canceled)
				&& Objects.equals(customer, other.customer) && Objects.equals(flight, other.flight)
				&& Objects.equals(ticketId, other.ticketId);
	}

}
