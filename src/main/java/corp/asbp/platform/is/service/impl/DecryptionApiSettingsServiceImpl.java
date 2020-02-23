package corp.asbp.platform.is.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import corp.asbp.platform.is.dto.DecriptionUserResponseDto;
import corp.asbp.platform.is.dto.ProcessDecryptionDto;
import corp.asbp.platform.is.exception.ASBPException;
import corp.asbp.platform.is.model.UserMeta;
import corp.asbp.platform.is.repository.UserMetaRepository;
import corp.asbp.platform.is.service.DecryptionApiSettingsService;
import corp.asbp.platform.is.service.OAuth2ClientRegistration;
import corp.asbp.platform.is.util.Constants;



@Service
public class DecryptionApiSettingsServiceImpl implements DecryptionApiSettingsService{

	private static final Logger LOG = LoggerFactory.getLogger(DecryptionApiSettingsService.class);
	
	@Autowired
	UserMetaRepository userMetaRepo;
	
	@Autowired
	OAuth2ClientRegistration oAuth2ClientRegistration;
	
	@Override
	public List<UserMeta> addAndUpdateDecryptionApiSettings(List<UserMeta> userMeta) {
		
		//check
		if (userMeta != null && userMeta.size() > 0) {
			
			for(UserMeta uMeta : userMeta ) {
				userMetaRepo.save(uMeta);
			}
			//update oauth2 bean settings
			oAuth2ClientRegistration.updateOAuth2ClientRegistrations();
		}
		//return
		return userMeta;
	}

	@Override
	public List<UserMeta> getDecryptionApiSettingsByVendor(Long userId) {
		//save
		return userMetaRepo.findAllByIdUserId(userId);
	}

	@Override
	public DecriptionUserResponseDto processDecryption(ProcessDecryptionDto decryption) throws ASBPException {
		//set default null
		DecriptionUserResponseDto dr = new DecriptionUserResponseDto();
		// check
		if (decryption.getVendor() != null) {
			List<UserMeta> settings  = getDecryptionApiSettingsByVendor(decryption.getVendor());

			// check
			if (settings != null) {
				
				String apiEndpoint = null;
				String apiKey = null;
				String apiResponseFormat = null;
				String apiResponseMpping = null;
				
				
				for(UserMeta userMeta : settings) {
					//check
					if(userMeta.getId().getKey().equals(Constants.DECRYPTION_API_ENDPOINT)) {
						apiEndpoint = userMeta.getValue();
					}
					//check
					if(userMeta.getId().getKey().equals(Constants.DECRYPTION_API_KEY)) {
						apiKey = userMeta.getValue();
					}
					//check
					if(userMeta.getId().getKey().equals(Constants.DECRYPTION_API_RESPONSE_FORMAT)) {
						apiResponseFormat = userMeta.getValue();
					}
					
					//check
					if(userMeta.getId().getKey().equals(Constants.DECRYPTION_API_RESPONSE_MAPPING)) {
						apiResponseMpping = userMeta.getValue();
					}
				}
				
				LOG.info("Vendor id : " + decryption.getVendor());
				LOG.info("Encryption api end point : " + apiEndpoint);
				LOG.info("Encryption api key : " + apiKey);
				LOG.info("Encryption text : " + decryption.getEcryptionStr());
				LOG.info("apiResponseMpping : " + apiResponseFormat);
			
				try {
					OkHttpClient client = new OkHttpClient();

					
					//set here empty when live/ test use case came url 
					String extra = "&mode=cbc&alg=arcfour";
					//Todo if need i.e change request header format
					MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
					RequestBody body = RequestBody.create(mediaType, "text=" + decryption.getEcryptionStr() + "&key="
							+ apiKey + extra);
					
					Request request = new Request.Builder().url(apiEndpoint).method("POST", body)
							.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

					try {
						
						Response response = client.newCall(request).execute();
						String returnResponse = "";
						returnResponse = response.body().string();
						
						LOG.info("Decription API Response : " + returnResponse);

						HashMap<String, Object> resp = new HashMap<String, Object>();
						HashMap<String, Object> maping = new HashMap<String, Object>();

						ObjectMapper mapper = new ObjectMapper();
						
						// Convert Map to JSON
						resp = (HashMap<String, Object>) mapper.readValue(returnResponse.replaceAll("\\\\", ""),
								new TypeReference<Map<String, Object>>() {
								});
						
						LOG.info("Response Mapper : " + resp.toString());

						maping = (HashMap<String, Object>) mapper.readValue(apiResponseMpping.replaceAll("\\\\", ""),
								new TypeReference<Map<String, Object>>() {
								});
						
						LOG.info("Mapping Mapper : " + maping.toString());
						
						if (maping.get("uniqueid") != null && resp.containsKey(maping.get("uniqueid"))) {
							dr.setUniqueId((String) resp.get(maping.get("uniqueid")));
						}
						if (maping.get("firstname") != null && resp.containsKey(maping.get("firstname"))) {

							dr.setFirstName((String) resp.get(maping.get("firstname")));
						}
						if (maping.get("lastname") != null && resp.containsKey(maping.get("lastname"))) {

							dr.setLastName((String) resp.get(maping.get("lastname")));
						}
						if (maping.get("email") != null && resp.containsKey(maping.get("email"))) {
							dr.setEmail((String) resp.get(maping.get("email")));
						}
						
						// return
						return dr;
					} catch (JsonGenerationException e) {						
						LOG.info("JsonGenerationException error  : " + e.getMessage());
						e.printStackTrace();
					} catch (JsonMappingException ex) {
						LOG.info("JsonMappingException error  : " + ex.getMessage());
						ex.printStackTrace();
						throw new ASBPException(
								"Invalid json response from vendor api end point", ex);
					} catch (IOException e) {
						LOG.info("IOException error  : " + e.getMessage());
						e.printStackTrace();
					}
				} catch (HttpClientErrorException ex) {
					LOG.info("Exception error  : " + ex.getMessage());
					ex.printStackTrace();
					throw new ASBPException(
							"The vendor encrytion key or endpoint id is invalid, please add a valid details", ex);
				}
			
			}
		}
		//return
		return dr;

	}

	@Override
	public UserMeta getDecryptionApiSettingsByVendorAndKey(Long userId, String key) {		
		return userMetaRepo.findFirstByIdUserIdAndIdKey(userId,key);
	}

}
