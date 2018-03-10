package com.revature.demo.pojo;

/*
 * Author: Devin Dellamano
 * Purpose: Provides the type for each batch
 */
public class BatchType {

	private int id;
	private String name;
	//length = 10 for now, this defaults to 10 and can't be changed.
	private int length = 10; // in the future, this field can be editable.
	
	public BatchType() {
		// TODO Auto-generated constructor stub
	}

	public BatchType(int id, String name, int length) {
		super();
		this.id = id;
		this.name = name;
		this.length = length;
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "BatchType [id=" + id + ", name=" + name + ", length=" + length + "]";
	}
	
	
}
