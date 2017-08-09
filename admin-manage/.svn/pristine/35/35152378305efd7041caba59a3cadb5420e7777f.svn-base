package org.bumishi.admin.domain.modle;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * 角色
 *
 */
@Entity
@Table(name="role_resource")
public class RoleResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8676534370458615293L;

	@Id
	@GeneratedValue
	private int id ;

	/** 
	 * 角色id
	 */
	private String roleId;

	/** 
	 * 资源id 
	 */
	private String resourceId;

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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}

