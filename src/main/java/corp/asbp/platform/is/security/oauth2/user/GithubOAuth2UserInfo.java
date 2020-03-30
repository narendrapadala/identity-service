package corp.asbp.platform.is.security.oauth2.user;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
    //ToDo find and change the exact first and last name matching attribute on the map and implement as needed
    @Override
    public String getFirstName() {
        return (String) attributes.get("name");
    }
    
    @Override
    public String getLastName() {
        return (String) attributes.get("name");
    }
}
