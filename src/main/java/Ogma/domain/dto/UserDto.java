package Ogma.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {

	@NotBlank(message = "Username can't be empty")
	private String username;

	@NotBlank(message = "First Name can't be empty")
	private String firstName;
	@NotBlank(message = "Last Name can't be empty")
	private String lastName;
	private boolean active;
	private String activationCode;

	@Email(message = "Email is not correct")
	@NotBlank(message = "Please fill the Email address")
	private String email;
	@Pattern(regexp = "\\+\\d(-\\d{3}){2}-\\d{4}",
			message = "Please, use right phone number format. For example: +7-903-503-9832")
	@NotBlank(message = "Phone can't be null")
	private String phone;
}
