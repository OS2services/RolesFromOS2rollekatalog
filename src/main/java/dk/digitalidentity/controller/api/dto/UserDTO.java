package dk.digitalidentity.controller.api.dto;

import dk.digitalidentity.service.rc.dto.UserReadDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private String userId;
	private String uuid;

	public UserDTO() {
		
	}

	public UserDTO(UserReadDTO user) {
		this.userId = user.getUserId();
		this.uuid = user.getExtUuid();
	}
}
