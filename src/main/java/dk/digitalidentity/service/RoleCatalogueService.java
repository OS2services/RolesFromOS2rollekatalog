package dk.digitalidentity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dk.digitalidentity.config.AppConfiguration;
import dk.digitalidentity.service.rc.dto.RoleWrapperDTO;
import dk.digitalidentity.service.rc.dto.UserReadDTO;

@Service
public class RoleCatalogueService {

	@Autowired
	private AppConfiguration configuration;

	@Autowired
	private RestTemplate restTemplate;

	public List<UserReadDTO> getUsersByRole(String roleIdentifier) throws Exception {
		String cprResourceUrl = configuration.getRc().getUrl();
		if (!cprResourceUrl.endsWith("/")) {
			cprResourceUrl += "/";
		}

		cprResourceUrl += "read/assigned/" + roleIdentifier + "?indirectRoles=true";

		HttpHeaders headers = new HttpHeaders();
		headers.add("ApiKey", configuration.getRc().getApiKey());

		HttpEntity<RoleWrapperDTO> request = new HttpEntity<>(headers);

		ResponseEntity<RoleWrapperDTO> response = restTemplate.exchange(cprResourceUrl, HttpMethod.GET, request, new ParameterizedTypeReference<RoleWrapperDTO>() {	});
		if (response.getStatusCodeValue() < 200 || response.getStatusCodeValue() > 299) {
			throw new Exception("Failed to get data from OS2rollekatalog: " + response.getStatusCodeValue());
		}

		return response.getBody().getAssignments();
	}
}
