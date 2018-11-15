package com.entrega.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Tema {
	@Id
	@GeneratedValue
	int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private boolean temaGeneral;

	
	public Tema() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isTemaGeneral() {
		return temaGeneral;
	}
	public void setEsGeneral(boolean temaGeneral) {
		this.temaGeneral = temaGeneral;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id=id;
	}
}
