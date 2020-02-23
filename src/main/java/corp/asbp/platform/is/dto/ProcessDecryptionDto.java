package corp.asbp.platform.is.dto;

import lombok.Data;

/**
 * @author Narendra Padala
 *
 */
@Data
public class ProcessDecryptionDto {
	private Long vendor;
	private String ecryptionStr;
	private String redirectUri;
}
