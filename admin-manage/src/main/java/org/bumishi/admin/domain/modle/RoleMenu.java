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
@Table(name="role_menu")
public class RoleMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7752788578141373924L;

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
	private String menuId;

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

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	
}

