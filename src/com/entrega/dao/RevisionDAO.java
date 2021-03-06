package com.entrega.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.entrega.controllers.EMF;
import com.entrega.entidades.Revision;


public class RevisionDAO implements DAO<Revision, Integer> {
	private static RevisionDAO daoRevision;

	private RevisionDAO() {
	}

	public static RevisionDAO getInstance() {
		if (daoRevision == null)
			daoRevision = new RevisionDAO();
		return daoRevision;
	}

	public Revision findById(Integer id) {
		EntityManager entityManager= EMF.createEntityManager();
		Revision review = entityManager.find(Revision.class, id);

		return review;

	}

	public Revision persist(Revision review) {
		EntityManager entityManager= EMF.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(review);
		entityManager.getTransaction().commit();
		entityManager.close();

		return review;
	}

	public List<Revision> findAll( ) {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT r FROM Revision r");
		entityManager.getTransaction().commit();
		List<Revision> revisiones = query.getResultList();
		entityManager.close();
		return revisiones;
	}

	public boolean delete(Integer id) {
		throw new UnsupportedOperationException();
	}

	public Revision update(Integer id, Revision newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
