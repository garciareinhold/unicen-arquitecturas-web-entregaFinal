package com.entrega.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.entrega.controllers.EMF;
import com.entrega.entidades.Tema;


public class TemaDAO implements DAO<Tema, Integer> {
	private static TemaDAO daoTema;

	public TemaDAO() {
	}

	public static TemaDAO getInstance() {
		if (daoTema == null) {
			daoTema = new TemaDAO();
			;
		}

		return daoTema;
	}

	@Override
	public Tema persist(Tema tema ) {
		EntityManager entityManager= EMF.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(tema);
		entityManager.getTransaction().commit();
		entityManager.close();

		return tema;
	}
	public Tema findByName(String nombre) {
		Tema retorno;
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createNativeQuery(
				"SELECT * FROM `tema` WHERE name=:name",
				Tema.class);
		query.setParameter("name", nombre);
		entityManager.getTransaction().commit();
		retorno =(Tema)query.getSingleResult();
		return retorno;
	}

	@Override
	public Tema update(Integer id, Tema newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tema findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tema> findAll( ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer id) {
//		Tema tema = this.findById(id, entityManager);
//		if (tema != null) {
//			entityManager.getTransaction().begin();
//			entityManager.remove(tema);
//			entityManager.getTransaction().commit();
//			return true;
//		} else {
//			return false;
//		}
		return false;
	}

}
