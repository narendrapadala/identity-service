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

import corp.asbp.platform.is.model.Client;
import corp.asbp.platform.is.payload.GenericResponseDto;
import corp.asbp.platform.is.service.ClientService;



/**
 * @author Narendra
 *
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

	private ClientService clientService;

	@Autowired
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping("/all")
	public GenericResponseDto<Page<Client>> getAllClients(@RequestParam(required = false) String searchColumn,
			@RequestParam(required = false) String searchValue, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
		return new GenericResponseDto.GenericResponseDtoBuilder<>(request,
				clientService.getAllClients(PageRequest.of(page, size))).build();
	}

	@GetMapping("/getClient")
	public GenericResponseDto<Client> getClient(@RequestParam Long clientId, HttpServletRequest request) {

		return new GenericResponseDto.GenericResponseDtoBuilder<>(request, clientService.getClient(clientId)).build();
	}

	@PostMapping("/addClient")
	public GenericResponseDto<Client> addClient(@RequestBody Client api, HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("Client saved sucessfully", request,
				clientService.saveClient(api)).build();
	}

	@PutMapping("/updateClient")
	public GenericResponseDto<Client> updateClient(@RequestBody Client api, HttpServletRequest request) throws Exception {
		return new GenericResponseDto.GenericResponseDtoBuilder<>("Client updated sucessfully", request,
				clientService.updateClient(api)).build();
	}

	@DeleteMapping("/deleteClient")
	public GenericResponseDto<Boolean> deleteClient(@RequestParam Long clientId, HttpServletRequest request) {
		clientService.deleteClient(clientId);
		return new GenericResponseDto.GenericResponseDtoBuilder<>("Client deleted sucessfully", request, true).build();
	}

	
}
