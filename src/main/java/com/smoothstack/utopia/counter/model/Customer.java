package com.smoothstack.utopia.counter.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = -806608271727480741L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId")
	private Integer customerId;
	
	@NotNull
	@Length(min=3, max=45)
	@Column(name = "customerName")
	private String customerName;
	
	@NotNull
	@Length(min=15, max=50)
	@Column(name = "customerAddress")
	private String customerAddress;
	
	@NotNull
	@Column(name = "customerPhone")
	private String customerPhone;
	
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerAddress, customerId, customerName, customerPhone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerAddress, other.customerAddress) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(customerName, other.customerName)
				&& Objects.equals(customerPhone, other.customerPhone);
	}

}
