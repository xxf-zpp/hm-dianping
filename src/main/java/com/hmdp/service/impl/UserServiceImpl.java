package com.hmdp.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final static String USER_NICK_NAME_PREFIX = "user_";

    private final static String CODE_KEY = "codeKey";

    /**
     * 登录
     *
     * @param loginForm
     * @param session
     * @return
     */
    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //1.校验验证码
        String phone = loginForm.getPhone();
        String code = session.getAttribute(CODE_KEY).toString();
        if (!loginForm.getCode().equals(code)) {
            return Result.fail("验证码错误！！！");
        }
        //2.根据手机查询用户
        User user = query().eq("phone", phone).one();
        //3.用户不存在，是新用户，添加该用户信息
        if (user == null) {
            user = createUserWithPhone(phone);
        }
        //4.保存用户信息
        session.setAttribute("user",user);
        return Result.ok();
    }

    //添加新用户
    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        save(user);
        return user;
    }
}
