package org.bumishi.admin.domain.repository;

import org.bumishi.admin.domain.modle.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface RoleRepository  extends CrudRepository<Role,String>{

	@Query(value="select r.* from role r join user_role ur on r.id=ur.role_id where ur.uid=?",nativeQuery=true)
    List<Role> getRoles(String userId);
}
