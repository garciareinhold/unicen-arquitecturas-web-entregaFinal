package com.entrega.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity

public  class Trabajo {
	@Id
	@GeneratedValue
	int id;
	@Column(nullable = true)
	@OneToMany(cascade= CascadeType.ALL)
	List<Tema> temasConocimiento;
	@Column(nullable = false)
	String nombre;
	@ManyToMany(mappedBy = "trabajos")
	@Column(nullable = true)
	List<Usuario> autores;
	@OneToMany(mappedBy = "trabajo")
	@Column(nullable = true)
	List<Revision> revisiones;

	public Trabajo() {
		this.autores = new ArrayList<Usuario>();
		this.revisiones = new ArrayList<Revision>();
		this.temasConocimiento= new ArrayList<Tema>();
	}

	/**
	 * Este método devuelve true si el usuario no es de la autoria del trabajo ni
	 * trabaja en el mismo lugar que su colega
	 * 
	 * @param user
	 * @return
	 */
	public boolean evaluadorHabilitado(Usuario user) {
		if (this.autores.contains(user))
			return false;
		for (int i = 0; i < this.autores.size(); i++) {
			if (user.getLugarDeTrabajo() == this.autores.get(i).getLugarDeTrabajo())
				return false;
		}
		return true;
	}

	/**
	 * controla si el trabajo y el evaluador son compatibles para aceptar la
	 * revision
	 * 
	 * @param evaluador
	 * @return
	 */
	public  boolean aceptaRevision(Usuario evaluador) {
		boolean retorno = true;
		List<Tema> temasTrabajo = this.getTemasConocimiento();
		List<Tema> temasEvaluador = evaluador.getTemasConocimiento();

		if (temasTrabajo.size() <= temasEvaluador.size()
				&& (this.hayCupoRevision() && (evaluador.hayCupoTrabajo()))) {

			for (int i = 0; i < temasEvaluador.size(); i++) {
				if (!this.temasConocimiento.contains(temasEvaluador.get(i).getName()))
					retorno = false;
			}

		}

		else {

			retorno = false;

		}

		return retorno;
	};

	protected boolean hayCupoRevision() {
		return (this.revisiones.size()<3);
	}

	@Override
	public String toString() {
		return "Trabajo [id=" + id + ", nombre=" + nombre + ", autores=" + autores + ", revisiones=" + revisiones + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void addTemasConocimiento(Tema tema) {
		this.temasConocimiento.add(tema);
	}

	public List<Tema> getTemasConocimiento() {

		return this.temasConocimiento;
	}

	public void addAutor(Usuario autor) {
		autores.add(autor);

	}

	public void addReview(Revision review) {
		this.revisiones.add(review);
	}

	public int getId() {
		return this.id;
	}

}
