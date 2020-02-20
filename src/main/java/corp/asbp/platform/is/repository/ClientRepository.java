package corp.asbp.platform.is.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.Client;



@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findFirstById(Long clientId);

}
