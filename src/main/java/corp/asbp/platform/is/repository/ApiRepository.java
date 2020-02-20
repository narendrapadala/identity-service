package corp.asbp.platform.is.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.Api;



@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {
	
	public List<Api> findByName(String name);
	
	public Api findFirstByNameAndType(String name, HttpMethod type);
	
	public Api findByNameLikeAndType(String name, HttpMethod type);

	public Api findFirstById(Long apiId);
}
