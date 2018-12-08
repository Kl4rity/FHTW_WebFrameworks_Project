package at.technikumwien.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@Table(name="t_customers")
@NamedQuery(name="Customer.selectAll", query="SELECT n FROM Customer n")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@XmlAttribute
	private Long id;
	
	@Column(length=100, nullable=false)
	@XmlAttribute
	private String firstName;
	
	@Column(length=100, nullable=false)
	@XmlAttribute
	private String lastName;
	
	@Column(length=100, nullable=false)
	@XmlAttribute
	private Date birthDate;
	
	@Column(length=100, nullable=false)
	@XmlAttribute
	private Boolean active;
	
	public Customer() {}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}	
	
	public Customer(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Customer(Long id, String firstName, String lastName, Date birthDate, Boolean active ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstName;
	}

	public void setFirstname(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public void setLastname(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setActiveState (Boolean state) {
		this.active = state;
	}
	
	public Boolean getActiveState() {
		return this.active;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate= " + birthDate.toString() + ", isActive=" + active + "]";
	}
}