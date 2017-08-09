package org.bumishi.admin.application;

import org.bumishi.admin.domain.modle.SysLog;
import org.bumishi.admin.domain.repository.SysLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xieqiang on 2016/9/17.
 */
@Service
public class SysLogService {

    @Autowired
    protected SysLogRepository sysLogRepository;

    public void create(SysLog sysLog){
        sysLogRepository.save(sysLog);
    }

    @CacheEvict(value = "syslog", allEntries = true)
    public void clear(){
        sysLogRepository.deleteAll();
    }

    @Cacheable(value = "syslog")
    public List<SysLog> list(){
        return (List<SysLog>) sysLogRepository.findAll();
    }
}
