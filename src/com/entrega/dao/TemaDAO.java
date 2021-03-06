package com.entrega.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.entrega.controllers.EMF;
import com.entrega.entidades.Tema;
import com.entrega.entidades.Usuario;



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
//		entityManager.createNativeQuery("ALTER TABLE trabajo_tema DROP PRIMARY KEY, ADD PRIMARY KEY(temasConocimiento_idTema,id);").executeUpdate();
//		entityManager.createNativeQuery("ALTER TABLE usuario_tema DROP PRIMARY KEY, ADD PRIMARY KEY(temasConocimiento_idTema,id);").executeUpdate();
		entityManager.persist(tema);
		entityManager.getTransaction().commit();
		entityManager.close();

		return tema;
	}
	public Tema findByName(String nombre) {
		Tema retorno;
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT t FROM Tema t WHERE t.name= :name");
		query.setParameter("name", nombre);
		entityManager.getTransaction().commit();
		retorno =(Tema)query.getSingleResult();
		entityManager.close();
		return retorno;
	}

	@Override
	public Tema update(Integer id, Tema newEntityValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tema findById(Integer id) {
		EntityManager entityManager= EMF.createEntityManager();
		Tema tema = entityManager.find(Tema.class, id);
		return tema;
	}

	@Override
	public List<Tema> findAll( ) {
		EntityManager entityManager= EMF.createEntityManager();

		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT t FROM Tema t");
		entityManager.getTransaction().commit();
		List<Tema> temas = query.getResultList();
		entityManager.close();
		return temas;
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
