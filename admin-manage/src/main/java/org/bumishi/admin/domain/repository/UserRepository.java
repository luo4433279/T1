package org.bumishi.admin.domain.repository;

import org.bumishi.admin.domain.modle.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface UserRepository  extends CrudRepository<User,String> {
    User findByUsername(String username);
}
