package day0622;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class UserDTO {
	
	private String name;
	private String email;
	private int age;
	
	public UserDTO() {
		System.out.println("UserDTO 기본생성자");
	}
	
	public UserDTO(String name, String email, int age) {
		this.name = name;
		this.email = email;
		this.age = age;
		System.out.println("UserDTO 매개변수 있는 생성자");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + ", age=" + age + "]";
	}

	
}
