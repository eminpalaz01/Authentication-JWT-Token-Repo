package com.user.springbootcase.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "Logintracking")
public class LoginTracking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@CreationTimestamp
	@Column(name = "Attemptdate")
	private Date attemptDate;

	@Column(name = "Attempttype", length = 6)
	private String attemptType;

	@Column(name = "Userid", length = 15)
	private String userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Userid", referencedColumnName = "Userid", insertable = false, updatable = false)
	private MaxUser maxUser;

	public LoginTracking() {
	}

	public LoginTracking(int id, Date attemptDate, String attemptType, MaxUser maxUser) {
		this.attemptDate = attemptDate;
		this.attemptType = attemptType;
	}
}
