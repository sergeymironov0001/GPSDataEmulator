package model;

import java.util.List;

import domain.Transport;

/**
 * Интерфейс сервиса
 * 
 * @author Mironov S.V.
 * @since 31.03.2012
 */
public interface IModel {

	public List<Transport> getAllTransports();

	public Transport getTransport(String number);

	public void saveTransport(Transport transport);

}
