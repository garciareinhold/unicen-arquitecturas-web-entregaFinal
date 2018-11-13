package com.entrega.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
// @Table(name="TABLA_Usuarios")
public class Usuario {
	@Id
	int dni;
	@Column(nullable = false)
	String nombre;
	@Column(nullable = false)
	String apellido;
	@Column(nullable = false)
	String lugarDeTrabajo;
	@OneToMany(mappedBy = "evaluador")
	List<Revision> revision;
	@ManyToMany(cascade = CascadeType.ALL)
	List<Trabajo> trabajos;
	@Column(nullable = false)
	boolean esExperto;
	@Column(nullable = true)
	@OneToMany(cascade = CascadeType.ALL)
	List<Tema> temasConocimiento;

	public Usuario() {
		this.revision = new ArrayList<Revision>();
		this.trabajos = new ArrayList<Trabajo>();
		this.temasConocimiento = new ArrayList<Tema>();
		this.esExperto = false;
	}

	public Usuario(String nombre, String apellido, int dni, boolean esExperto, boolean esEvaluador,
			String lugarDeTrabajo) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.lugarDeTrabajo = lugarDeTrabajo;
		this.esExperto = false;
	}

	public boolean esExperto() {
		return this.esExperto;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
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
		this.revision.add(review);
	}

	public void addTrabajos(Trabajo work) {
		this.trabajos.add(work);
	}

	public List<Tema> getTemasConocimiento() {
		return this.temasConocimiento;
	}

	public void addTemasConocimiento(Tema tema) {
		this.temasConocimiento.add(tema);
		if (this.esExperto == false && tema.isTemaGeneral() == false) {
			this.esExperto = true;
		}
	}

	/**
	 * Este metodo devuelve true si en las revisiones contiene mas de tres articulos
	 * 
	 * @return
	 */
	public boolean hayCupoTrabajo() {
		return (this.revision.size() < 3);
	}

	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

}
