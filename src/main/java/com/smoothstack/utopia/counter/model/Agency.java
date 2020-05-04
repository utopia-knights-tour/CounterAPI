package com.smoothstack.utopia.counter.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Agency")
public class Agency implements Serializable {
	
	private static final long serialVersionUID = -6684730485115837355L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agencyId")
	private Integer agencyId;
	
	@Column(name = "agencyName")
	private String agencyName;
	
	@Column(name = "agencyAddress")
	private String agencyAddress;
	
	@Column(name = "agencyPhone")
	private String agencyPhone;

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyAddress() {
		return agencyAddress;
	}

	public void setAgencyAddress(String agencyAddress) {
		this.agencyAddress = agencyAddress;
	}

	public String getAgencyPhone() {
		return agencyPhone;
	}

	public void setAgencyPhone(String agencyPhone) {
		this.agencyPhone = agencyPhone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agencyAddress, agencyId, agencyName, agencyPhone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agency other = (Agency) obj;
		return Objects.equals(agencyAddress, other.agencyAddress) && Objects.equals(agencyId, other.agencyId)
				&& Objects.equals(agencyName, other.agencyName) && Objects.equals(agencyPhone, other.agencyPhone);
	}


}
