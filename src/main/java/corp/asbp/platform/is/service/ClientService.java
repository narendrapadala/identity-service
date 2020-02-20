/**
 * 
 */
package corp.asbp.platform.is.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.Client;


/**
 * @author Narendra
 *
 */
public interface ClientService {

	Client saveClient(Client client) throws ASBPException;

	Client updateClient(Client client) throws Exception;

	void deleteClient(Long clientId);

	Page<Client> getAllClients(Pageable pageable);

	Client getClient(Long clientId);
}
