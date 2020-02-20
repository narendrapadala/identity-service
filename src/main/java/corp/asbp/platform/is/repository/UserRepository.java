package corp.asbp.platform.is.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findFirstById(Long userId);
	
	User findFirstByEmail(String email);

	User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
