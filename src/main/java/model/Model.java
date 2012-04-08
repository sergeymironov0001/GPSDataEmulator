package model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.IDAO;
import domain.Transport;

/**
 * Класс сервиса
 * 
 * @author Mironov S.V.
 * @since 31.03.2012
 * 
 */

class Model implements IModel {

	@Autowired
	private IDAO dao;

	public Model() {
	}

	public IDAO getDao() {
		return dao;
	}

	public void setDao(IDAO dao) {
		this.dao = dao;
	}

	@Transactional
	public List<Transport> getAllTransports() {
		return getDao().getAllTransports();
	}

	@Transactional
	public void saveTransport(Transport transport) {
		getDao().saveTransport(transport);
	}

	@Transactional
	public Transport getTransport(String number) {
		return getDao().getTransport(number);
	}

}
