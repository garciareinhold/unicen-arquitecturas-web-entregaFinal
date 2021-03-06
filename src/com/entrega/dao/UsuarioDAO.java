package com.entrega.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.entrega.controllers.EMF;
import com.entrega.entidades.Revision;
import com.entrega.entidades.Trabajo;
import com.entrega.entidades.Usuario;


public class UsuarioDAO implements DAO<Usuario, Integer> {

	private static UsuarioDAO daoUsuario;

	private UsuarioDAO() {
	}

	public static UsuarioDAO getInstance() {
		if (daoUsuario == null) {
			daoUsuario = new UsuarioDAO();
			;
		}

		return daoUsuario;
	}

	public Usuario findById(Integer id) {
		EntityManager entityManager= EMF.createEntityManager();
		Usuario user = entityManager.find(Usuario.class, id);

		return user;
	}

	public Usuario persist(Usuario user ) {
		EntityManager entityManager= EMF.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		entityManager.close();
		return user;
	}

	public List<Usuario> findAll( ) {
		EntityManager entityManager= EMF.createEntityManager();

		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery("SELECT u FROM Usuario u");
		entityManager.getTransaction().commit();
		List<Usuario> usuarios = query.getResultList();
		return usuarios;
	}

	public boolean delete(Integer id ) {
		EntityManager entityManager= EMF.createEntityManager();
		Usuario user = this.findById(id);
		if (user != null) {
			entityManager.getTransaction().begin();
			entityManager.remove(user);
			entityManager.getTransaction().commit();
			entityManager.close();

			return true;
		} else {
			return false;
		}

	}
	
	
	public List<Trabajo> findTrabajos(Integer id ) {
//	 * Se obtienen todos los trabajos asignados a un Revisor
		EntityManager entityManager= EMF.createEntityManager();

			List<Trabajo> trabajos= new ArrayList<Trabajo>();
			entityManager.getTransaction().begin();
//			Query query = entityManager.createNativeQuery(
//					"SELECT t.* FROM revision r Join trabajo t on(r.trabajo_id=t.id and r.evaluador_dni = :revId)",
//					Trabajo.class);
			Query query = entityManager.createQuery("SELECT t FROM Revision r JOIN Trabajo t ON t.id=r.trabajo WHERE r.evaluador.id= :revId");
			query.setParameter("revId", id);
			entityManager.getTransaction().commit();
			trabajos = query.getResultList();
			entityManager.close();
			return trabajos;

	}
	public List<Trabajo> findTrabajosByUser(Integer id ) {
		EntityManager entityManager= EMF.createEntityManager();
		entityManager.getTransaction().begin();
		String jpql="SELECT t FROM Usuario u JOIN u.trabajos t WHERE u.id=:id";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("id", id);
		entityManager.getTransaction().commit();
		List<Trabajo> trabajos = query.getResultList();
		entityManager.close();
		return trabajos;
	}

	public List<Revision> findRevisiones(Integer id, Calendar desde, Calendar hasta) {
		EntityManager entityManager= EMF.createEntityManager();

		System.out.println(id);
		System.out.println(desde);
		System.out.println(hasta);

		entityManager.getTransaction().begin();
	
		Query query = entityManager.createQuery("SELECT r FROM Revision r WHERE r.evaluador.id= :evalId AND r.fechaRevision BETWEEN :desde AND :hasta");
		query.setParameter("evalId", id);
		query.setParameter("desde", desde);
		query.setParameter("hasta", hasta);
		entityManager.getTransaction().commit();
		List<Revision> revisiones = query.getResultList();

		return revisiones;
	}
	
	
//	este metodo hace lo mismo que el findTrabajosByUser???
//	public List<Trabajo> findTrabajosAutores(Integer id, EntityManager entityManager) {
//		entityManager.getTransaction().begin();
//		Query query = entityManager.createNativeQuery(
//				"SELECT t.* FROM trabajo t JOIN usuario_trabajo u on(u.trabajos_id=t.id) WHERE autores_dni= :autId",
//				Trabajo.class);
//		query.setParameter("autId", id);
//		entityManager.getTransaction().commit();
//		List<Trabajo> revisiones = query.getResultList();
//		return revisiones;
//	}

	public Usuario update(Integer id, Usuario newEntityValues, EntityManager entityManager) {
		return null;
	}

		@Override
		public Usuario update(Integer id, Usuario entity) {
			EntityManager entityManager = EMF.createEntityManager();
			Usuario entityAux = entityManager.find(Usuario.class, id);
			if (entityAux == null) {
				entityManager.close();
				return null;
			} else {
				entityManager.getTransaction().begin();			
				entityAux.setNombre(entity.getNombre());
				entityAux.setApellido(entity.getApellido());
				entityAux.setLugarDeTrabajo(entity.getLugarDeTrabajo());
				entityManager.getTransaction().commit();
				entityManager.close();
				return entityAux;
			}
		}	
	

}