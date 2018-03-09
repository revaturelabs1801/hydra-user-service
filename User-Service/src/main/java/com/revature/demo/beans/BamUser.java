package com.revature.demo.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;


@Entity
@Table(name = "USERS")
@Component
public class BamUser {

	@Id
	@Column(name = "User_Id")
	@SequenceGenerator(name = "USERID_SEQ", sequenceName = "USERID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERID_SEQ")
	private int userId;

	@Column(name = "First_Name")
	@NotNull(message = "First name cannot be empty")
	private String fName;

	@Column(name = "Middle_Name")
	private String mName;

	@Column(name = "Last_Name")
	@NotNull(message = "Last name cannot be empty")
	private String lName;

	@Column(name = "eMail")
	@NotNull(message = "e-mail address cannot be empty")
	private String email;

	@Column(name = "Password")
	@NotNull(message="Password cannot be empty")
	private String pwd;
	
	@Column(name = "Role") // Role 1 is for associates // Role 2 is for trainers & QC
	private int role; // Role 3 is for admins

	@Column(name = "Main_Phone")
	@NotNull(message = "Primary phone cannot be empty")
	private String phone;

	@Column(name = "Second_Phone")
	private String phone2;

	@Column(name = "Skype_ID")
	private String skype;

	@Column(name="AssignForce_ID")
	private Integer assignForceID;
	
	
	public BamUser() {
		super();
	}

	public BamUser(String fName, String mName, String lName, String email, String pwd, int role,
			String phone, String phone2, String skype) {//NOSONAR
		super();
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
		this.phone = phone;
		this.phone2 = phone2;
		this.skype = skype;
	}

	public BamUser(int userId, String fName, String mName, String lName, String email, String pwd, int role,
			String phone, String phone2, String skype) {//NOSONAR
		super();
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.email = email;
		this.userId = userId;
		this.pwd = pwd;
		this.role = role;
		this.phone = phone;
		this.phone2 = phone2;
		this.skype = skype;
	}

	public BamUser(int userId, String fName, String mName, String lName, String email, String pwd, int role,
			String phone, String phone2, String skype, Integer AssignForceID) {//NOSONAR
		super();
		this.userId = userId;
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
		this.phone = phone;
		this.phone2 = phone2;
		this.skype = skype;
		assignForceID = AssignForceID;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public Integer getAssignForceID() {
		return assignForceID;
	}

	public void setAssignForceID(Integer assignForceID) {
		this.assignForceID = assignForceID;
	}

	@Override
	public String toString() {
		return "BamUser [userId=" + userId + ", fName=" + fName + ", mName=" + mName + ", lName=" + lName + ", email="
				+ email + ", pwd=" + pwd + ", role=" + role + ", phone=" + phone + ", phone2="
				+ phone2 + ", skype=" + skype + "]";
	}

}