package com.news.backendproject.controller.user;

import com.google.code.kaptcha.Producer;
import com.news.backendproject.annotation.AccessRestriction;
import com.news.backendproject.entity.ApiResponse;
import com.news.backendproject.entity.GenaralDataResponse;
import com.news.backendproject.service.SendEmailCaptchaService;
import com.news.backendproject.service.UserGetLoginStatusService;
import com.news.backendproject.service.UserLoginService;
import com.news.backendproject.service.UserRegisterService;
import com.news.backendproject.verify.BotCaptchaVerification;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * UserController:控制所有用户关联的行为
 */
@RestController
@Validated//启用参数验证
public class UserController {
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserGetLoginStatusService userGetLoginStatusService;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BotCaptchaVerification botCaptchaVerification;
    @Autowired
    private SendEmailCaptchaService sendEmailCaptchaService;
    //发送人机验证码
    @AccessRestriction(message = "验证码请求过于频繁,1分钟后再试")
    @GetMapping("/sendBotCaptcha")
    public void generateCaptcha(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String operationType) throws IOException {
        // 禁用缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 设置响应类型为图片
        response.setContentType("image/jpeg");

        // 生成验证码原文
        // 验证码原文为value
        String captchaOraginal = kaptchaProducer.createText();
        // 设置业务场景operationType+sessionId作为验证码唯一标识key
        // 验证码Key：业务场景+sessionId
        String redisKey =operationType+request.getSession().getId();
        // 存入redis
        // 存入Redis，设置60秒过期
        stringRedisTemplate.opsForValue().set(
                redisKey,
                captchaOraginal,
                60,
                TimeUnit.SECONDS
        );
        // 生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(captchaOraginal);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    //人机验证码的验证接口
    @GetMapping("/botCheck")
    public ApiResponse<GenaralDataResponse>botCheck(
            //限制验证码格式
            @RequestParam @Pattern(regexp = "^[0-9a-z]{4}$") String captcha,
            @RequestParam String operationType,
            HttpServletRequest request){
        System.out.println(operationType);
        if (!(operationType.equals("register") || operationType.equals("forgot"))) {
            //抛出400错误,前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400,"非法请求",new GenaralDataResponse(false,null));
        }
        String redisKey =operationType+request.getSession().getId();
       return botCaptchaVerification.verify(captcha,redisKey);
    }

    @AccessRestriction(limit =30,message = "获取登录状态过于频繁")
    @GetMapping("/getLoginStatus")
    public ApiResponse<GenaralDataResponse> getLoginStatus(HttpServletRequest request){
        return userGetLoginStatusService.getLoginStatus(request);
    }

    @AccessRestriction(limit = 5,message = "登录过于频繁,1分钟后再试",limitKey = false)
    @PostMapping("/getLoginResponse")
    public ApiResponse<GenaralDataResponse> getLoginResponse(
            //@RequestParam定义的参数必须传入，否则400错误
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            @RequestParam String operationType,
            HttpServletRequest request){
        //限制访问携带的验证码来自登录业务
        if (!operationType.equals("login")) {
            //抛出400错误,前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400,"非法验证请求",new GenaralDataResponse(false,null));
        }
        String redisKey =operationType+request.getSession().getId();
        return userLoginService.userLogin(username,password,captcha,redisKey);
    }

    //todo:完成邮箱验证码的存储与读取
    //发送邮箱验证码
    @AccessRestriction(limit = 1,message = "验证码请求过于频繁,1分钟后再试",limitKey = false)
    @GetMapping("/sendEmailCaptcha")
    public  ApiResponse<GenaralDataResponse> sendEmailCaptcha(
            @RequestParam String email,
            @RequestParam String operationType,
            HttpServletRequest request
    ){
        String redisKey = operationType + request.getSession().getId();
        if (!(operationType.equals("register")||operationType.equals("forgot"))) {
            //抛出400错误,前端使用try-catch配合element-plus处理
            return new ApiResponse<>(400,"非法请求",new GenaralDataResponse(false,null));
        }
            return sendEmailCaptchaService.send(email,redisKey);
    }
    //注册接口包含验证邮箱验证码
    @AccessRestriction(limit = 5,message = "注册过于频繁,1分钟后再试",limitKey = false)
    @PostMapping("/getRegisterResponse")
    public ApiResponse<GenaralDataResponse> getRegisterResponse(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String email,
            @RequestParam String emailCaptcha,
            @RequestParam String operationType,
            HttpServletRequest request
    ){
        if(!operationType.equals("register")){
            return new ApiResponse<>(400,"非法请求",new GenaralDataResponse(false,null));
        }
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        System.out.println("confirmPassword:"+confirmPassword);
        System.out.println("email:"+email);
        System.out.println("emailCaptcha:"+emailCaptcha);
        System.out.println("operationType:"+operationType);
        System.out.println(request.getHeader("X-Forwarded-For"));
        return userRegisterService.userRegister(null);
    }


    @GetMapping("/getUserProfile")
    public ApiResponse<GenaralDataResponse> getUserProfile(){
        GenaralDataResponse data = new GenaralDataResponse(false,null);
        return new ApiResponse<>(200,"获取信息",data);
    }
}
