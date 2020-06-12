package id.co.PPMToolFullStack.exceptions;

public class InvalidLogInResponse {
	private String username;
	private String password;
	
	public InvalidLogInResponse() {
		this.username = "Invalid Username";
		this.password = "Invalid Password";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
