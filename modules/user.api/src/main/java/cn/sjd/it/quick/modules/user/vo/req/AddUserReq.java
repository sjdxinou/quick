package cn.sjd.it.quick.modules.user.vo.req;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import cn.sjd.it.quick.framework.vo.BaseReq;

public class AddUserReq extends BaseReq {

	private static final long serialVersionUID = 5468628225430609101L;

	@NotBlank(message="密码不能为空")
	@Size(min=6,max=12,message="密码最小长度6,最大12")
	private String userPwd;

	@NotBlank(message="姓名不能为空")
	private String realName;

	@NotBlank(message="身份证不能为空")
	@Length(min=18,max=18)
	private String idCard;

	@NotBlank(message="性别不能为空")
	private String gender;

	@NotBlank(message="手机号码不能为空")
	@Length(min=11,max=11)
	private String mobile;

	@NotBlank(message="邮箱不能为空")
	@Email
	private String email;

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
