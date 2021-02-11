package dk.digitalidentity.service.rc.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleWrapperDTO {
	private List<UserReadDTO> assignments;
}