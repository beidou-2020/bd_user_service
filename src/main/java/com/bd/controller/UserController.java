package com.bd.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.PageParam;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.User;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.bd.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 逻辑登录
     * @return
     */
    @GetMapping(value = "/login")
    @ResponseBody
    public Result login(@Valid UserLoginDTO userLoginDTO) {
        User userInfo = userService.login(userLoginDTO.getAccount(), userLoginDTO.getPassword());
        if (Objects.isNull(userInfo)){
            return Result.fail(ResultCode.AUTH_FAIL.code(), ResultCode.AUTH_FAIL.msg());
        }
        return Result.ok(userInfo);
    }

    /**
     * 用户注册
     * @param userInfoParame
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public Result register(@RequestBody @Valid RegisterUserParame userInfoParame) {
        String account = userInfoParame.getAccount();
        User userInfo = userService.findByAccount(account);
        if (Objects.nonNull(userInfo)){
            log.error("{}的用户信息已经存在，userInfo:{}。", account, JSONObject.toJSONString(userInfo));
            return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
        }
        User user = userService.insertUserInfo(userInfoParame);
        if (Objects.isNull(user)){
            return Result.fail(ResultCode.SERVER_ERROR.code(), ResultCode.SERVER_ERROR.msg());
        }
        return Result.ok(user);
    }

    /**
     * 用户列表
     * @param query
     * @param pageParam
     * @return
     */
    @GetMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Result list(UserQuery query, PageParam pageParam){
        PageInfo<User> pageInfo = userService.listByPage(query, pageParam);
        return Result.ok(pageInfo);
    }

    /**
     * 添加用户信息
     * @param addUserDTO
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid AddUserDTO addUserDTO){
        String account = addUserDTO.getAccount();
        User userInfo = userService.findByAccount(account);
        if (Objects.nonNull(userInfo)){
            log.error("{}的用户信息已经存在，userInfo:{}", account, JSONObject.toJSONString(userInfo));
            return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
        }

        User user = userService.add(addUserDTO);
        if (Objects.isNull(user)){
            return Result.fail(ResultCode.SERVER_ERROR.code(), ResultCode.ERROR.msg());
        }
        return Result.ok(user);
    }

    /**
     * 更新用户信息
     * @param updateUserDTO
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody @Valid UpdateUserDTO updateUserDTO){
        User update = userService.update(updateUserDTO);
        if (Objects.isNull(update)){
            return Result.fail(ResultCode.SERVER_ERROR.code(), "服务器异常：更新用户失败！");
        }
        return Result.ok(update);
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable(name = "id") Long id){
        Boolean delete = userService.delete(id);
        if (!delete){
            return Result.fail(ResultCode.SERVER_ERROR.code(), "服务器异常：删除用户失败！");
        }
        return Result.ok(delete);
    }

    /**
     * 获取用户详情
     * @param id
     * @return
     */
    @GetMapping("/userDetails/{id}")
    @ResponseBody
    public Result userDetails(@PathVariable(name = "id") Long id){
        User userInfo = userService.findById(id);
        return Result.ok(userInfo);
    }
}
