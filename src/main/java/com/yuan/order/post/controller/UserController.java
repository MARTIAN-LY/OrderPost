package com.yuan.order.post.controller;

import com.alibaba.druid.util.StringUtils;
import com.yuan.order.post.controller.view.UserView;
import com.yuan.order.post.error.BusinessException;
import com.yuan.order.post.error.EmBusinessError;
import com.yuan.order.post.response.CommonReturnType;
import com.yuan.order.post.service.UserService;
import com.yuan.order.post.service.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    HttpServletRequest httpServletRequest;

    /** 用户获取 otp 短信接口 */
    @ResponseBody
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
        // 按照一定的规则生成 OTP 验证码
        Random random = new Random(System.currentTimeMillis());
        int otp = random.nextInt(90000) + 10000;
        String otpCode = String.valueOf(otp);

        // 将 OTP 验证码同用户手机号关联
        httpServletRequest.getSession().setAttribute(telephone, otpCode);

        // 将 OTP 验证码通过短信通道发送给用户
        System.out.println("OTPCode " + otpCode);

        return CommonReturnType.create(null);
    }

    /** 用户注册接口 */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType register(
            @RequestParam(name = "type") Short type,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "school_id") String schoolId,
            @RequestParam(name = "alias") String alias,
            @RequestParam(name = "password") String password
    ) throws BusinessException {

        System.out.println("用户注册");
        System.out.println("type=" + type);
        System.out.println("phone=" + phone);
        System.out.println("school=" + schoolId);
        System.out.println("alias=" + alias);
        System.out.println("password=" + password);

        UserModel userModel = new UserModel();
        userModel.setType(type);
        userModel.setPhone(phone);
        userModel.setSchoolId(schoolId);
        userModel.setAlias(alias);
        userModel.setEncryptPassword(encode(password));

        userService.userRegister(userModel);

        return CommonReturnType.create("注册成功");
    }

    /** 用户登录接口 */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType login(
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "password") String password
    ) throws BusinessException {

        System.out.println("用户登录： phone=" + phone + ", password=" + password);

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        // 检查登录是否成功
        UserModel userModel = userService.validUserLogin(phone, encode(password));

        //将登录凭证加入到用户成功登录的 session 内
        httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(userModel.getType());
    }


    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/get")
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        // 调用 service 服务获取对应 id 的用户并返回给前端
        // 需要将核心领域模型转化为UI可使用的模型 Model->View
        // 返回通用对象
        UserModel userModel = userService.getUserById(id);

        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXISTS);
        }
        UserView userView = UserView.viewFromModel(userModel);
        return CommonReturnType.create(userView);
    }

    public String encode(String s) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }
}