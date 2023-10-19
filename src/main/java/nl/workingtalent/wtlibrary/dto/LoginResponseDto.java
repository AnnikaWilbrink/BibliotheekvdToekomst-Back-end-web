package nl.workingtalent.wtlibrary.dto;

public class LoginResponseDto {

	private boolean success;

	private String token;

	private String name;

	private String role;

	private long userId;

	public LoginResponseDto(boolean success) {
		super();
		this.success = success;
	}

	public LoginResponseDto(boolean success, String token, String name, String role, long userId) {
		super();
		this.success = success;
		this.token = token;
		this.name = name;
		this.role = role;
		this.userId = userId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
