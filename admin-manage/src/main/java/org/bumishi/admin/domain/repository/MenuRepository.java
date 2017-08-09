package org.bumishi.admin.domain.repository;

import java.util.List;

import org.bumishi.admin.domain.modle.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface MenuRepository extends CrudRepository<Menu,String>{

	@Query(value="select m.* from menu m join role_menu rm on m.id=rm.menu_id where rm.role_id=?",nativeQuery=true)
    List<Menu> roleMenus(String roleId);
    @Query(value="select m.* from menu m join role_menu rm on m.id=rm.menu_id join user_role ur on rm.role_id=ur.role_id "
    		+ "where m.disabled=0 and ur.uid=?",nativeQuery=true)
    List<Menu> getNavMenus(String userId);
}
