package dao;

import java.util.List;

import domain.Transport;

/**
 * Интерфейс, описывающий классы слоя доступа к объектам
 * 
 * @author Mironov S.V.
 * @since 31.03.2012
 */
public interface IDAO {
	void initialize();

	public List<Transport> getAllTransports();

	public Transport getTransport(String number);

	public void saveTransport(Transport transport);

}
