package org.bumishi.admin.domain.modle;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * 用户
 *
 */
@Entity
public class User implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7321861018989409550L;

	/** 主键ID */
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	/** 登录名称 */
	private String username;

	/** 密码 */
	private String password;

	/**密码加密的盐*/
	private String salt;

	/** 邮箱 */
	private String email;

	/** 是否禁用 */
	private boolean disabled;

	/** 创建时间 */
	private Date createtime;

	/** 最后登录时间 */
	private Date lasttime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isRoot(){
		return "root".equals(username);
	}
}
