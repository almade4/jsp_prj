package member;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

	private int smsReceiveYN,emailReceiveYN;
	private String memberCode,password,name,email,phone1,phone2,phone3,
		zipcode,address,address2,ip;
	private String[] hobby;
	
	private Date inputDate;
	
}
