package com.microAPI.entidades;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity

public  class Trabajo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = true)
	@ManyToMany(cascade= CascadeType.MERGE)
	@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name="Trabajo_id",referencedColumnName="id"), 
	@PrimaryKeyJoinColumn(name="temasConocimiento_idTema",referencedColumnName="idTema") 	})
	protected List<Tema> temasConocimiento;
	
	@Column(nullable = false)
	private String nombre;
	
//	@ManyToMany(fetch=FetchType.LAZY)
//	@JoinTable(name = "trabajo_usuario",
//	joinColumns = { @JoinColumn(name = "trabajos_id") },
//	inverseJoinColumns = { @JoinColumn(name = "autores_dni") })
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name="trabajo_usuario", joinColumns={@JoinColumn(name="trabajos_id")},
	inverseJoinColumns={@JoinColumn(name="autores_dni")})
	@Column(nullable = true)
	@JsonIgnoreProperties(value= {"trabajos"}, allowSetters=true)
	private List<Usuario> autores;
	
	@OneToMany(mappedBy = "trabajo")
	@Column(nullable = true)
	@JsonIgnoreProperties(value= "trabajo", allowSetters=true)
	private List<Revision> revisiones;

	public Trabajo() {
		this.temasConocimiento= new ArrayList<Tema>();
		this.autores = new ArrayList<Usuario>();
		this.revisiones = new ArrayList<Revision>();
	}

	/**
	 * Este m�todo devuelve true si el usuario no es de la autoria del trabajo ni
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
		for (int i = 0; i < this.autores.size(); i++) {
			if(this.autores.get(i).getId()==evaluador.getId()) {
				System.out.println("/////////////////////********");
				return false;
			}
		}
		if (temasTrabajo.size() <= temasEvaluador.size()
				&& (this.hayCupoRevision() && (evaluador.hayCupoTrabajo()))) {

			for (int i = 0; i < temasEvaluador.size(); i++) {
				if (!this.temasConocimiento.contains(temasEvaluador.get(i)))
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
	public List<Usuario> getAutores() {

		return this.autores;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autores == null) ? 0 : autores.hashCode());
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((revisiones == null) ? 0 : revisiones.hashCode());
		result = prime * result + ((temasConocimiento == null) ? 0 : temasConocimiento.hashCode());
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
		Trabajo other = (Trabajo) obj;
		if (autores == null) {
			if (other.autores != null)
				return false;
		} else if (!autores.equals(other.autores))
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (revisiones == null) {
			if (other.revisiones != null)
				return false;
		} else if (!revisiones.equals(other.revisiones))
			return false;
		if (temasConocimiento == null) {
			if (other.temasConocimiento != null)
				return false;
		} else if (!temasConocimiento.equals(other.temasConocimiento))
			return false;
		return true;
	}

	public List<Revision> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(List<Revision> revisiones) {
		this.revisiones = revisiones;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTemasConocimiento(List<Tema> temasConocimiento) {
		this.temasConocimiento = temasConocimiento;
	}

	public void setAutores(List<Usuario> autores) {
		this.autores = autores;
	}
	
}
