package tech.dongfei.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.dongfei.ssyx.acl.mapper.RolePermissionMapper;
import tech.dongfei.ssyx.acl.service.RolePermissionService;
import tech.dongfei.ssyx.model.acl.RolePermission;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
