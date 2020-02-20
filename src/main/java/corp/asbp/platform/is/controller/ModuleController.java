/**
 * 
 */
package corp.asbp.platform.is.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import corp.asbp.platform.is.model.Module;
import corp.asbp.platform.is.model.ModuleFeature;
import corp.asbp.platform.is.payload.GenericResponseDto;
import corp.asbp.platform.is.service.ModuleService;



/**
 * @author Narendra
 *
 */
@RestController
@RequestMapping("/modules")
public class ModuleController {

	private ModuleService moduleService;

	@Autowired
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	@GetMapping("/all")
	public GenericResponseDto<Page<Module>> getAllApis(@RequestParam(required = false) String searchColumn,
			@RequestParam(required = false) String searchValue, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request,
				moduleService.getAllModules(PageRequest.of(page, size))).build();
	}

	@GetMapping("/getModule")
	public GenericResponseDto<Module> getModule(@RequestParam Long moduleId, HttpServletRequest request) {

		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, moduleService.getModule(moduleId)).build();
	}

	@PostMapping("/addModule")
	public GenericResponseDto<Module> addModule(@RequestBody Module module, HttpServletRequest request)
			throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("Module saved sucessfully", request,
				moduleService.saveModule(module)).build();
	}

	@PutMapping("/updateModule")
	public GenericResponseDto<Module> updateModule(@RequestBody Module module, HttpServletRequest request)
			throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("Module updated sucessfully", request,
				moduleService.updateModule(module)).build();
	}

	@DeleteMapping("/deleteModule")
	public GenericResponseDto<Boolean> deleteModule(@RequestParam Long moduleId, HttpServletRequest request) {
		moduleService.deleteModule(moduleId);
		return new GenericResponseDto.GenericResponseDtoBuilder<>("Module deleted sucessfully", request, true).build();
	}

	@GetMapping("/allModuleFeatures")
	public GenericResponseDto<Page<ModuleFeature>> getAllModuleFeatures(
			@RequestParam(required = false) String searchColumn, @RequestParam(required = false) String searchValue,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request,
				moduleService.getAllModuleFeatures(PageRequest.of(page, size))).build();
	}

	@GetMapping("/getModuleFeature")
	public GenericResponseDto<ModuleFeature> getModuleFeature(@RequestParam Long moduleFeatureId,
			HttpServletRequest request) {

		return new GenericResponseDto.GenericResponseDtoBuilder<>(request,
				moduleService.getModuleFeature(moduleFeatureId)).build();
	}

	@GetMapping("/getModuleFeatures")
	public GenericResponseDto<Page<ModuleFeature>> getModuleFeatures(
			@RequestParam(required = false) String searchColumn, @RequestParam(required = false) String searchValue,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam Long moduleId, HttpServletRequest request) {

		return new GenericResponseDto.GenericResponseDtoBuilder<>(request,
				moduleService.getModuleFeatures(moduleId, PageRequest.of(page, size))).build();
	}

	@PostMapping("/addModuleFeature")
	public GenericResponseDto<ModuleFeature> addModuleFeature(@RequestBody ModuleFeature module,
			HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("ModuleFeature saved sucessfully", request,
				moduleService.saveModuleFeature(module)).build();
	}

	@PutMapping("/updateModuleFeature")
	public GenericResponseDto<ModuleFeature> updateModule(@RequestBody ModuleFeature module, HttpServletRequest request)
			throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("ModuleFeature updated sucessfully", request,
				moduleService.updateModuleFeature(module)).build();
	}

	@DeleteMapping("/deleteModuleFeature")
	public GenericResponseDto<Boolean> deleteModuleFeature(@RequestParam Long moduleId, HttpServletRequest request) {
		moduleService.deleteModuleFeature(moduleId);
		return new GenericResponseDto.GenericResponseDtoBuilder<>("ModuleFeature deleted sucessfully", request, true)
				.build();
	}

}
