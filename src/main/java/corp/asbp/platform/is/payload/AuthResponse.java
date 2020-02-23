package corp.asbp.platform.is.payload;

public class AuthResponse {
	
    private String accessToken;
    private String session;
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken,String session) {
        this.accessToken = accessToken;
        this.session = session;
    }

    public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
