package com.easybusiness.usermanagement.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@Embeddable
public class User implements Serializable {
    
   private static final long serialVersionUID = 1L;

    @Id()
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="USER_DETAILS_SEQ")
    @SequenceGenerator(name="USER_DETAILS_SEQ", sequenceName="USER_DETAILS_SEQ")
    private Long id;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LOGINID")
    private String loginid;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CONTACTNO")
    private String contactno;

    @Column(name = "PASSWORD")
    private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", loginid=" + loginid + ", address="
				+ address + ", contactno=" + contactno + ", password=" + password + "]";
	}

	

    
}
