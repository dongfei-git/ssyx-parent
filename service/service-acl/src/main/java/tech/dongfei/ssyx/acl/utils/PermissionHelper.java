package tech.dongfei.ssyx.acl.utils;

import tech.dongfei.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {

    public static List<Permission> buildPermission(List<Permission> allPermissionList) {
        //最终数据封装的List集合
        List<Permission> trees = new ArrayList<>();
        //遍历所有菜单list集合，得到第一层数据，pid=0
        for (Permission permission : allPermissionList) {
            if (permission.getPid() == 0) {
                permission.setLevel(1);
                trees.add(findChildren(permission, allPermissionList));
            }
        }
        return trees;
    }

    //递归查询，找子节点
    private static Permission findChildren(Permission permission,
                                           List<Permission> allPermissionList) {
        permission.setChildren(new ArrayList<>());
        //遍历allPermissionList，判断当前节点id == pid是否一致，直到找不到为止
        for (Permission it : allPermissionList) {
            if (permission.getId().longValue() == it.getPid().longValue()) {
                int level = permission.getLevel()+1;
                it.setLevel(level);
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
                //封装下一次的数据
                permission.getChildren().add(findChildren(it, allPermissionList));
            }
        }
        return permission;
    }

}
