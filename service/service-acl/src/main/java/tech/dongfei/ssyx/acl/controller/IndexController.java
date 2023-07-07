package tech.dongfei.ssyx.acl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.dongfei.ssyx.common.result.Result;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {

    //1.登录
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login() {
        //返回token值
        Map<String, String> map = new HashMap<>();
        map.put("token", "token-admin");
        return Result.ok(map);
    }

    //2.获取信息
    @ApiOperation("获取信息接口")
    @GetMapping("/info")
    public Result info() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    //3.登出
    @ApiOperation("登出接口")
    @PostMapping("/logout")
    public Result logout()  {
        return Result.ok(null);
    }

}
