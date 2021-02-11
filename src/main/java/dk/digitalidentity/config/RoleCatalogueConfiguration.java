package dk.digitalidentity.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dk.digitalidentity.config.model.RoleIdentifierMapping;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class RoleCatalogueConfiguration {
	private boolean enabled = false;
	private String url = "";
	private String apiKey = "";

	private List<RoleIdentifierMapping> mapping = new ArrayList<>();
}
