package com.entrega.entidades;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Revision {
	@Id
	@GeneratedValue
	int id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="evaludor_dni")
	Usuario evaluador;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="trabajo_id")
	Trabajo trabajo;
	
//	@Column(nullable = false)
//	Calendar fechaRevision;
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
//	public Calendar getFechaRevision() {
//		return fechaRevision;
//	}
//	public void setFechaRevision(Calendar fechaRevision) {
//		this.fechaRevision = fechaRevision;
//	}

	public int getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evaluador == null) ? 0 : evaluador.hashCode());
		result = prime * result + id;
		result = prime * result + ((trabajo == null) ? 0 : trabajo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Revision other = (Revision) obj;
		if (evaluador == null) {
			if (other.evaluador != null)
				return false;
		} else if (!evaluador.equals(other.evaluador))
			return false;
		if (id != other.id)
			return false;
		if (trabajo == null) {
			if (other.trabajo != null)
				return false;
		} else if (!trabajo.equals(other.trabajo))
			return false;
		return true;
	}
	
}
