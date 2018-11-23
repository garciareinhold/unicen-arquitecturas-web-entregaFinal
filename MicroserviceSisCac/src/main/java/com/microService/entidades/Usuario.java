package com.microService.entidades;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity
public class Usuario {
	@Id
	int id
	;
	@Column(nullable = false)
	String nombre;
	
	@Column(nullable = false)
	String apellido;
	
	@Column(nullable = false)
	String lugarDeTrabajo;
	
	@Column(nullable = true)
	@OneToMany(mappedBy = "evaluador")
	@JsonIgnoreProperties(value= "evaluador", allowSetters=true)
	List<Revision> review;
	
	@Column(nullable = true)
	@ManyToMany(mappedBy = "autores")
	List<Trabajo> trabajos;
	
	@Column(nullable = false)
	boolean expert;
	
	@Column(nullable = true)
	@ManyToMany(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name="usuario_id",referencedColumnName="id"), 
	@PrimaryKeyJoinColumn(name="temasConocimiento_idTema",referencedColumnName="idTema") 	})
	List<Tema> temasConocimiento;

	public Usuario() {
		this.review = new ArrayList<Revision>();
		this.trabajos = new ArrayList<Trabajo>();
		this.temasConocimiento = new ArrayList<Tema>();
		this.expert = false;
	}

	public Usuario(String nombre, String apellido, int dni, boolean esEvaluador,
			String lugarDeTrabajo) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = dni;
		this.lugarDeTrabajo = lugarDeTrabajo;
		this.expert = false;
	}
	
	public List<Revision> getReview() {
		return review;
	}

	public void setReview(List<Revision> review) {
		this.review = review;
	}

	public boolean isExpert() {
		return expert;
	}

	public void setExpert(boolean expert) {
		this.expert = expert;
	}

	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	public void setTemasConocimiento(List<Tema> temasConocimiento) {
		this.temasConocimiento = temasConocimiento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + id;
		result = prime * result + (expert ? 1231 : 1237);
		result = prime * result + ((lugarDeTrabajo == null) ? 0 : lugarDeTrabajo.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((temasConocimiento == null) ? 0 : temasConocimiento.hashCode());
		result = prime * result + ((trabajos == null) ? 0 : trabajos.hashCode());
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
		Usuario other = (Usuario) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (id != other.id)
			return false;
		if (expert != other.expert)
			return false;
		if (lugarDeTrabajo == null) {
			if (other.lugarDeTrabajo != null)
				return false;
		} else if (!lugarDeTrabajo.equals(other.lugarDeTrabajo))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (temasConocimiento == null) {
			if (other.temasConocimiento != null)
				return false;
		} else if (!temasConocimiento.equals(other.temasConocimiento))
			return false;
		if (trabajos == null) {
			if (other.trabajos != null)
				return false;
		} else if (!trabajos.equals(other.trabajos))
			return false;
		return true;
	}

	public boolean esExperto() {
		return this.expert;
	}

	public int getId() {
		return id;
	}

	public void setId(int dni) {
		this.id = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getLugarDeTrabajo() {
		return lugarDeTrabajo;
	}

	public void setLugarDeTrabajo(String lugarDeTrabajo) {
		this.lugarDeTrabajo = lugarDeTrabajo;
	}

	public void addRevision(Revision review) {
		this.review.add(review);
	}

	public void addTrabajos(Trabajo work) {
		this.trabajos.add(work);
	}

	public List<Tema> getTemasConocimiento() {
		return this.temasConocimiento;
	}

	public void addTemasConocimiento(Tema tema) {
		this.temasConocimiento.add(tema);
		if (this.expert == false && tema.isTemaGeneral() == false) {
			this.expert = true;
		}
	}

	/**
	 * Este metodo devuelve true si en las revisiones contiene mas de tres articulos
	 * 
	 * @return
	 */
	public boolean hayCupoTrabajo() {
		return (this.review.size() < 3);
	}

	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

}
