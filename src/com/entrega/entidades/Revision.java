package com.entrega.entidades;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Revision {
	@Id
	@GeneratedValue
	int id;
	@ManyToOne(cascade= CascadeType.ALL)
	Usuario evaluador;
	@ManyToOne(cascade= CascadeType.ALL)
	Trabajo trabajo;
	@Column(nullable = false)
	Calendar fechaRevision;
	public Revision() {};
	public Usuario getEvaluador() {
		return evaluador;
	}
	public void setEvaluador(Usuario evaluador) {
		this.evaluador = evaluador;
	}
	public Trabajo getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}
	public Calendar getFechaRevision() {
		return fechaRevision;
	}
	public void setFechaRevision(Calendar fechaRevision) {
		this.fechaRevision = fechaRevision;
	}
	@Override
	public String toString() {
		return "Revision [id=" + id + ", evaluador=" + evaluador + ", trabajo=" + trabajo + ", fechaRevision="
				+ fechaRevision + "]";
	}
	public int getId() {
		return id;
	}
	
}
