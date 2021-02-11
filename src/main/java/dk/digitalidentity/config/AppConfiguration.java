package dk.digitalidentity.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import dk.digitalidentity.controller.api.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "configuration")
public class AppConfiguration {
	private String apiKey;
	private RoleCatalogueConfiguration rc = new RoleCatalogueConfiguration();
	private List<UserDTO> testData = new ArrayList<>();
}
