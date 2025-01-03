package com.example.inyencapi.inyencfalatok.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="customers")
public class Customer implements Serializable {

	  	@Id
	    @Column(name = "id", nullable = false, columnDefinition = "char(36)", unique = true)
	    @JdbcTypeCode(SqlTypes.CHAR)
	    private UUID id = UUID.randomUUID();

	    @Column(name = "customer_name", nullable = false)
	    private String customerName;

	    @Column(name = "customer_phone_number", unique = true)
	    private String customerPhoneNumber;

	    @OneToOne
	    @JoinColumn(name = "customer_address_id", nullable = false)
	    private Address address;

	    @Column(name = "customer_email", unique = true)
	    private String customerEmail;
	    
	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Order> orders = new ArrayList<>();

		@Override
		public int hashCode() {
			return Objects.hash(address, customerEmail, customerName, customerPhoneNumber);
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
			return  Objects.equals(customerEmail, other.customerEmail)
					|| Objects.equals(customerPhoneNumber, other.customerPhoneNumber);
		}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", customerName='" + customerName + '\'' +
				", customerPhoneNumber='" + customerPhoneNumber + '\'' +
				", address=" + address.getId() +
				", customerEmail='" + customerEmail + '\'' +
				", orders=" + orders +
				'}';
	}
}
