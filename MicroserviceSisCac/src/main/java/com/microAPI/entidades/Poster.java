package com.microAPI.entidades;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class Poster extends Trabajo {
	
	public boolean aceptaRevision(Usuario evaluador) {
		if(!this.hayCupoRevision()) 
			{
			return false;
			}
		else {
			List<Tema> temasEvaluador = evaluador.getTemasConocimiento();
			for (int i = 0; i < temasEvaluador.size(); i++) {
				if (this.temasConocimiento.contains(temasEvaluador.get(i).getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
