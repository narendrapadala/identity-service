package corp.asbp.platform.is.service;

import java.io.IOException;
import java.util.List;

import corp.asbp.platform.is.dto.DecriptionUserResponseDto;
import corp.asbp.platform.is.dto.ProcessDecryptionDto;
import corp.asbp.platform.is.model.UserMeta;




/**
 * @author Narendra
 */
public interface DecryptionApiSettingsService {

	List<UserMeta> addAndUpdateDecryptionApiSettings(List<UserMeta> userMeta);
	
	List<UserMeta> getDecryptionApiSettingsByVendor(Long userId);
	
	UserMeta getDecryptionApiSettingsByVendorAndKey(Long userId,String key);
	
	DecriptionUserResponseDto processDecryption(ProcessDecryptionDto decryption) throws IOException;
	
}
