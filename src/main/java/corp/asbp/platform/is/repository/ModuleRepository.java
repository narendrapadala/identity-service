package corp.asbp.platform.is.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import corp.asbp.platform.is.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

	Module findFirstById(Long moduleId);

}
