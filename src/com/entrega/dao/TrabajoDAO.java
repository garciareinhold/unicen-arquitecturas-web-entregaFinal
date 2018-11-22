package com.entrega.dao;


import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Query;

import com.entrega.controllers.EMF;

import com.entrega.entidades.Trabajo;


public class TrabajoDAO implements DAO<Trabajo, Integer> {

	private static TrabajoDAO daoTrabajo;

	private TrabajoDAO() {
	}

	public static TrabajoDAO getInstance() {
		if (daoTrabajo == null)
			daoTrabajo = new TrabajoDAO();
		return daoTrabajo;
	}

	public Trabajo findById(Integer id) {
		EntityManager entityManager= EMF.createEntityManager();
		Trabajo work = entityManager.find(Trabajo.class, id);
		entityManager.close();
		return work;
	}

	public Trabajo findByNombre(String nombre ) {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
//		Query query = entityManager.createNativeQuery("SELECT * FROM trabajo WHERE nombre= :name", Trabajo.class);
		Query query = entityManager.createQuery("SELECT t FROM Trabajo WHERE t.nombre = :name");
		query.setParameter("name", nombre);
		entityManager.getTransaction().commit();
		Trabajo work = (Trabajo) query.getSingleResult();
		entityManager.close();

		return work;
	}

	public Trabajo persist(Trabajo work) {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(work);
		entityManager.getTransaction().commit();
		entityManager.close();

		return work;
	}

	public List<Trabajo> findAll() {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT t FROM Trabajo t");
		entityManager.getTransaction().commit();
		List<Trabajo> trabajos = query.getResultList();
		entityManager.close();
		return trabajos;
	}

	public boolean delete(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Trabajo update(Integer id, Trabajo newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Trabajo> findTrabajosByUserAndTema(String nombre, int id) {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT t FROM Usuario u JOIN u.trabajos t JOIN t.temasConocimiento tc  WHERE u.id= :userId AND tc.name= :name");
		query.setParameter("name", nombre);
		query.setParameter("userId", id);

		entityManager.getTransaction().commit();
		List<Trabajo> trabajos = query.getResultList();
		entityManager.close();
		return trabajos;
	}
	public List<Trabajo> findTrabajosReviewByUserAndTema(String nombre, int id) {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT t FROM Usuario u JOIN u.review r JOIN r.trabajo t JOIN t.temasConocimiento tc  WHERE u.id= :userId AND tc.name= :name");
		query.setParameter("name", nombre);
		query.setParameter("userId", id);

		entityManager.getTransaction().commit();
		List<Trabajo> trabajos = query.getResultList();
		entityManager.close();
		return trabajos;
	}
}
