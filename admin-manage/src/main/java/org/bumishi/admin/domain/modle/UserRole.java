package org.bumishi.admin.domain.modle;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * 角色对应的资源
 *
 */
@Entity
@Table(name="user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 5928960711829129762L;

	@Id
	@GeneratedValue
	private int id ;

	/** 
	 * 角色id
	 */
	private String roleId;

	/** 
	 * 菜单id 
	 */
	private String uid;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	
}

