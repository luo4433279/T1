package org.bumishi.admin.domain.modle;

import javax.persistence.Entity;

/**
 * 用户分配角色功能中列出角色，用户已经具备的角色checked=true
 *
 * @author qiang.xie
 * @date 2016/10/28
 */

public class SelectRole {

    private String rid;//role id

    private String name;//role name

    private boolean checked;
    
    private String description;

    public SelectRole() {
    }

    public SelectRole(String rid, String name, boolean checked) {
        this.rid = rid;
        this.name = name;
        this.checked = checked;
    }

    public String getRid() {
        return rid;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getName() {
        return name;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
