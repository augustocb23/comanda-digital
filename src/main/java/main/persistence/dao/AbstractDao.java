package main.persistence.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class AbstractDao<T, PK extends Serializable> {
	@SuppressWarnings("unchecked")
	private final Class<T> entityClass =
			(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	@PersistenceContext
	private EntityManager entityManager;

	public void save(T entity) {
		entityManager.persist(entity);
	}

	public void update(T entity) {
		entityManager.merge(entity);
	}

	public void delete(PK id) {
		entityManager.remove(entityManager.getReference(entityClass, id));
	}

	public T findById(PK id) {
		return entityManager.find(entityClass, id);
	}

	public List<T> findAll() {
		return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
	}

	boolean existsItemWithColumn(@NotNull String column, Object value, @Null PK id) {
		try {
			TypedQuery<T> query =
					entityManager.createQuery("FROM " + entityClass.getSimpleName() + " WHERE " + column +
							" = " + "?1", entityClass);
			query.setParameter(1, value);
			T result = query.getSingleResult();
			//Throws NoResultException
			Session session = entityManager.unwrap(Session.class);
			Object entityId = session.getIdentifier(result);
			//false if is the same object
			return entityId != id;
		} catch (NoResultException e) {
			return false;
		}
	}

	List<T> createQuery(String jpql, Object... params) {
		TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}
}
