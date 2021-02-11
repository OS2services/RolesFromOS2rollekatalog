package dk.digitalidentity.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dk.digitalidentity.config.AppConfiguration;
import dk.digitalidentity.controller.api.dto.UserDTO;
import dk.digitalidentity.service.RoleCatalogueService;
import dk.digitalidentity.service.rc.dto.UserReadDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {

	@Autowired
	private RoleCatalogueService roleCatalogueService;

	@Autowired
	private AppConfiguration config;

	@GetMapping("/api/whoHasRole")
	public ResponseEntity<?> whoHasRole(@RequestParam(name = "identifier", required = true) String identifier) {
		log.info("whoHasRole = " + identifier + " called");

		List<UserDTO> list = null;
		try {
			String roleIdentifier = mapIdentifier(identifier);

			if (!config.getRc().isEnabled()) {
				log.info("Returning stubbed result");
				list = config.getTestData();
			}
			else {
				if (!StringUtils.hasLength(config.getRc().getApiKey()) || !StringUtils.hasLength(config.getRc().getUrl())) {
					return new ResponseEntity<String>("OS2rollekatalog integration not configured correctly (missing URL and ApiKey)", HttpStatus.INTERNAL_SERVER_ERROR);
				}

				List<UserReadDTO> users = roleCatalogueService.getUsersByRole(roleIdentifier);
	
				list = users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
			}
		}
		catch (Exception ex) {
			log.error("Unexpected error", ex);

			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		
		log.info("Found " + list.size() + " users with role: " + identifier);

		return ResponseEntity.ok(list);
	}

	private String mapIdentifier(String identifier) throws Exception {
		List<String> roleIdentifiers = config.getRc().getMapping().stream()
				.filter(m -> m.getIdentifier().equals(identifier))
				.map(m -> m.getRoleIdentifier())
				.collect(Collectors.toList());

		if (roleIdentifiers.size() == 0) {
			throw new Exception("No role mapping configured for " + identifier);
		}
		else if (roleIdentifiers.size() > 1) {
			throw new Exception("More than one role mapping found for " + identifier);
		}

		return roleIdentifiers.get(0);
	}
}
