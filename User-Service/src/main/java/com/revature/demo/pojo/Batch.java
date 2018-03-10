package com.revature.demo.pojo;

import java.sql.Timestamp;

import com.revature.demo.beans.BamUser;

public class Batch {
	
	private int id;
	private String name;
	private Timestamp startDate;
	private Timestamp endDate;
	private BamUser trainer;
	private BatchType type;

	public Batch() {
		// TODO Auto-generated constructor stub
	}

	public Batch(int id, String name, Timestamp startDate, Timestamp endDate, BamUser trainer, BatchType type) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainer = trainer;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public BamUser getTrainer() {
		return trainer;
	}

	public void setTrainer(BamUser trainer) {
		this.trainer = trainer;
	}

	public BatchType getType() {
		return type;
	}

	public void setType(BatchType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Batch [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
	
}
