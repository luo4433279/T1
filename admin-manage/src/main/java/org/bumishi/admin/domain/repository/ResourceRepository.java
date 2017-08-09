package org.bumishi.admin.domain.repository;

import java.util.List;

import org.bumishi.admin.domain.modle.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface ResourceRepository  extends CrudRepository<Resource,String>{
   
    @Query(value="select re.* from  role_resource rr  join resource re on re.id=rr.resource_id where rr.role_id=?",nativeQuery=true)
    List<Resource> listByRole(String roleId);

    @Query(value="select * from resource WHERE disabled=0",nativeQuery=true)
    List<Resource> getEnableResources();

}
