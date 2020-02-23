package corp.asbp.platform.is.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.stereotype.Service;

import corp.asbp.platform.is.enumerations.AuthProvider;
import corp.asbp.platform.is.model.User;
import corp.asbp.platform.is.model.UserMeta;
import corp.asbp.platform.is.repository.UserMetaRepository;
import corp.asbp.platform.is.repository.UserRepository;

import corp.asbp.platform.is.service.OAuth2ClientRegistration;
import corp.asbp.platform.is.util.Constants;

@Service
public class OAuth2ClientRegistrationImpl implements OAuth2ClientRegistration {

	private static final Logger LOG = LoggerFactory.getLogger(OAuth2ClientRegistration.class);

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserMetaRepository userMetaRepo;
	
	@Autowired
	ApplicationContext appContext;

	@Override
	public Boolean updateOAuth2ClientRegistrations() {
		// get all vendors
		List<User> vendors = userRepo.findAllByUserGroupId(2L);
		// check
		if (vendors.size() > 0) {
			
			LOG.info("Total Vendors : " + vendors.size() );

			
			String[] beans = appContext.getBeanDefinitionNames();
			/*
				Arrays.sort(beans);
				for (String bean : beans) {
					System.out.println(bean);
				}
			*/
			ConfigurableApplicationContext configContext = (ConfigurableApplicationContext) appContext;
			SingletonBeanRegistry beanRegistry = configContext.getBeanFactory();

			ClientRegistrationRepository b = (ClientRegistrationRepository) beanRegistry
					.getSingleton("clientRegistrationRepository");

			List<ClientRegistration> registrations = new ArrayList<>();			
			
			// list all vendors
			for (User vendor : vendors) {
				// check
				if (vendor != null) {
					// list vendor meta
					List<UserMeta> listVendorMeta = userMetaRepo.findAllByIdUserId(vendor.getId());
					// set flag
					boolean flag = false;
					
					Long vendorId=0L;
					// check
					if (listVendorMeta.size() > 0) {
						
						LOG.info("AuthProvider : " + AuthProvider.oAuth2 );
						// loop
						for (UserMeta uMeta : listVendorMeta) {
							
							LOG.info("Key : " + uMeta.getId().getKey()  + "Value : " + uMeta.getValue() );
							
							// check
							if (uMeta.getValue().equals("oAuth2")) {
								flag = true;
								vendorId = uMeta.getId().getUserId();
							}
						}
					}
					//check
					if (flag) {	
						
						LOG.info("oAuth Vendor Id : " + vendorId );

						ClientRegistration clientRegistration = customerOAuth2ClientRegistrations(listVendorMeta);
						//check
						if(clientRegistration!=null) {
							registrations.add(clientRegistration);
						}
								
					}else {
						LOG.info("Else oAuth Vendor Id : " + vendorId );
					}

				}

			}
			
			//check and save
			if(registrations.size() > 0) {
				
				
				// to remvoe bean
				BeanDefinitionRegistry factory = (BeanDefinitionRegistry)
				appContext.getAutowireCapableBeanFactory();
				factory.removeBeanDefinition("clientRegistrationRepository");
				
				ClientRegistrationRepository crr = new InMemoryClientRegistrationRepository(registrations);
				beanRegistry.registerSingleton("clientRegistrationRepository", crr);
			}
		}

		return true;
	}

	private ClientRegistration customerOAuth2ClientRegistrations(List<UserMeta> userMeta) {

		String registrationId = null;
		String clientName = null;
		String clientId = null;
		String clientSecret = null;
		String authorizationUri = null;
		String tokenUri = null;
		String userInfoUri = null;

		if (userMeta.size() > 0) {
			// loop
			for (UserMeta uMeta : userMeta) {

				
				
				// set clientName
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_CLIENT_REGISTRATION_ID)) {
					registrationId = uMeta.getValue();
				}
				// set clientName
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_CLIENT_NAME)) {
					clientName = uMeta.getValue();
				}
				// set clientId
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_CLIENT_ID)) {
					clientId = uMeta.getValue();
				}
				// set clientSecret
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_CLIENT_SECRET)) {
					clientSecret = uMeta.getValue();
				}
				// set authorizationUri
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_AUTHORIZATION_URI)) {
					authorizationUri = uMeta.getValue();
				}
				// set tokenUri
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_TOKEN_URI)) {
					tokenUri = uMeta.getValue();
				}
				// set userInfoUri
				if (uMeta.getId().getKey().equals(Constants.OAUTH2_USER_INFO_URI)) {
					userInfoUri = uMeta.getValue();
				}

			}
			
			LOG.info("vendor registrationId : " + registrationId);
			LOG.info("vendor client name : " + clientName);
			LOG.info("clientName : " + clientName);
			LOG.info("clientId : " + clientId);
			LOG.info("clientSecret : " + clientSecret);
			LOG.info("authorizationUri : " + authorizationUri);
			LOG.info("tokenUri : " + tokenUri);
			LOG.info("userInfoUri : " + userInfoUri);

			if (registrationId!=null && clientName != null && clientId != null && clientSecret != null && authorizationUri != null
					&& tokenUri != null && userInfoUri != null) {
				
				//return
				return ClientRegistration.withRegistrationId(registrationId)
						.clientId(clientId)
						.clientSecret(clientSecret)
						.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
						.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
						.scope("openid", "profile", "email", "address", "phone")
						.authorizationUri(authorizationUri)
						.tokenUri(tokenUri)
						.userInfoUri(userInfoUri)
						.userNameAttributeName(IdTokenClaimNames.SUB)
						.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
						.clientName(clientName).build();

			}else {
				LOG.info("OAuth settings are not configured properly ..!" );
			}
		}
		
		//return
		return null;
	}

}
