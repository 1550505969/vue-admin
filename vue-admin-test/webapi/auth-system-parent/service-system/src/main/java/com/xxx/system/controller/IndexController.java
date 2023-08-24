package com.xxx.system.controller;

import com.xxx.common.result.Result;
import com.xxx.common.utils.JwtHelper;
import com.xxx.model.vo.LoginVo;
import com.xxx.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api("用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    //login
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String, Object> map = sysUserService.login(loginVo);
        return Result.ok(map);
    }

    //{
    //     "code": 20000,
    //     "data": {
    //         "roles": [
    //             "admin"
    //         ],
    //         "introduction": "I am a super administrator",
    //         "avatar": "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
    //         "name": "Super Admin"
    //     }
    // }
    //info
    @GetMapping("info")
    public Result info(HttpServletRequest request){
        //获取请求头token字符串
        String token = request.getHeader("token");
        System.out.println(token);

        //从token字符串获取用户名称
        String username = JwtHelper.getUsername(token);
        System.out.println(username);

        //根据用户名获取用户信息(基本信息 菜单权限 按钮权限数据)
        Map<String, Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }
}
