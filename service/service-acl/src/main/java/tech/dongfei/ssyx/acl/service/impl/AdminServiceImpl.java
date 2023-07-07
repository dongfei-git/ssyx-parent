package tech.dongfei.ssyx.acl.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.dongfei.ssyx.acl.mapper.AdminMapper;
import tech.dongfei.ssyx.acl.service.AdminService;
import tech.dongfei.ssyx.model.acl.Admin;
import tech.dongfei.ssyx.vo.acl.AdminQueryVo;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String username = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(username)) {
            wrapper.eq(Admin::getUsername, username);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Admin::getName, name);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }
}
