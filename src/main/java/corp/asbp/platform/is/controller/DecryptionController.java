package corp.asbp.platform.is.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import corp.asbp.platform.is.dto.DecriptionUserResponseDto;
import corp.asbp.platform.is.dto.ProcessDecryptionDto;
import corp.asbp.platform.is.model.UserMeta;
import corp.asbp.platform.is.payload.GenericResponseDto;
import corp.asbp.platform.is.service.DecryptionApiSettingsService;





/**
 * @author Narendra Padala
 *
 */
@RestController
@RequestMapping("/setting")
public class DecryptionController {

	@Autowired
	DecryptionApiSettingsService decryptionApiSettingsService;

	@GetMapping("/getAllDecryptionApiSettingsByVendor")
	public GenericResponseDto<List<UserMeta> > getDecryptionApiSettingsByVendor(
			@RequestParam(required = true) Long vendor, HttpServletRequest request

	) {
		//return
		return new GenericResponseDto.GenericResponseDtoBuilder<>("success",request, decryptionApiSettingsService.getDecryptionApiSettingsByVendor(vendor)).build();
	}

	/**
	 * @param settings
	 * @param request
	 * @return
	 */
	@PostMapping("/addAndUpdateDecryptionApiSettings")
	public GenericResponseDto<List<UserMeta>> addAndUpdateDecryptionApiSettings(
			@RequestBody List<UserMeta> settings, HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("success",request, decryptionApiSettingsService.addAndUpdateDecryptionApiSettings(settings)).build();
	}

	/**
	 * @param decryption
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/processDecryption")
	public GenericResponseDto<DecriptionUserResponseDto> processDecryption(@RequestBody ProcessDecryptionDto decryption,
			HttpServletRequest request) throws IOException {
		DecriptionUserResponseDto user = decryptionApiSettingsService.processDecryption(decryption);
		return new GenericResponseDto.GenericResponseDtoBuilder<>("success",request, user).build();
	}
}
