package tech.dongfei.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.dongfei.ssyx.acl.mapper.AdminRoleMapper;
import tech.dongfei.ssyx.acl.service.AdminRoleService;
import tech.dongfei.ssyx.model.acl.AdminRole;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
}
