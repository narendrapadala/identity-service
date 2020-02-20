package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.UserMeta;


@Repository
public interface UserMetaRepository extends JpaRepository<UserMeta, Long> {
	
	List<UserMeta> findAllByIdUserId(Long userId);
	
	UserMeta findFirstByIdUserIdAndIdKey(Long userId,String Key);

	void deleteByIdUserIdAndIdKey(Long userId,String Key);
}
