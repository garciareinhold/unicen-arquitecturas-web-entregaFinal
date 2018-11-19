package com.entrega.entidades;



import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;





@Entity

public class Tema {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idTema;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private boolean temaGeneral;
	
	public Tema() {}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idTema;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (temaGeneral ? 1231 : 1237);
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
		Tema other = (Tema) obj;
		if (idTema != other.idTema)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (temaGeneral != other.temaGeneral)
			return false;
		return true;
	}


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
	
	public int getIdTema() {
		return idTema;
	}


	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}


	public void setTemaGeneral(boolean temaGeneral) {
		this.temaGeneral = temaGeneral;
	}
	
}
