/**
 * 
 */
package corp.asbp.platform.is.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import corp.asbp.platform.is.model.Module;




/**
 * @author Narendra
 *
 */
public class ModuleSequenceGenerator extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Module module = (Module) object;
		if(module.getId()!=null) {
			return module.getId();
		}
		return super.generate(session, object);
	}
}
