package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.Transport;

/**
 * Класс слоя доступа к объектам, основанный на JPA
 * 
 * @author Mironov S.V.
 * @since 31.03.2012
 * 
 */
@Repository(value = "daoJPA")
public class DAOJPA implements IDAO {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public DAOJPA() {
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<Transport> getAllTransports() {
		return getAll(new Transport());
	}

	public void saveTransport(Transport transport) {
		save(transport);
	}

	@Transactional(readOnly = true)
	public Transport getTransport(String number) {
		Query query = entityManager
				.createQuery("SELECT t FROM Transport t WHERE t.number = :number");
		query.setParameter("number", number);
		Transport result = null;
		try {
			result = (Transport) query.getSingleResult();
		} catch (javax.persistence.NoResultException exception) {
			System.out.println("Not found transport with number: " + number);
		}

		return result;
	}

	@Transactional(readOnly = true)
	private <T> List<T> getAll(T type) {
		Query query = entityManager.createQuery("select o from "
				+ type.getClass().getName() + " o");

		return query.getResultList();
	}

	@Transactional
	private void save(Object object) {
		entityManager.persist(object);
	}

	@Transactional
	private void remove(Object object) {
		entityManager.remove(object);
	}

}
