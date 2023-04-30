package WebCock.Project_Web.controller;

import WebCock.Project_Web.config.jwt.JwtProperties;
import WebCock.Project_Web.dto.JwtToken;
import WebCock.Project_Web.service.FindPasswordMailService;
import WebCock.Project_Web.service.LoginService;
import WebCock.Project_Web.service.RegisterMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Controller
//@RequestMapping(path="/api")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Autowired
    RegisterMailService registerMailService;
    @Autowired
    FindPasswordMailService findPasswordMailService;


    @GetMapping("user")
    public String user() {
        return "<h1>user</h1>";
    }
    @GetMapping("admin")
    public String admin() {
        return "<h1>admin</h1>";
    }

    @RequestMapping(value = "/login")
    // Json형태로 날아온 값들을 Map으로 묶고 userInfo에 저장
    public ResponseEntity<JwtToken> loginSuccess(@RequestBody Map<String, String> loginForm) {

        JwtToken token = loginService.login(loginForm);
        System.out.println("token == "+token);
        return ResponseEntity.ok()
                .header(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token.getAccessToken())
                .body(token);
    }

    @PostMapping("/register")
    public int registerMember(@RequestBody Map<String, String> userInfo) {
        System.out.println("TestMessageUserInfo "+userInfo);
        int result = loginService.register(userInfo);
        System.out.println("TestMessageresult " + result);
        return result;
    }

    @RequestMapping(value = "/registermail")
    public String mailConfirm(@RequestBody Map<String, String> email) throws Exception {
        System.out.println(email);
        String code = registerMailService.sendSimpleMessage(email.get("email"));
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);
        return code;
    }

    @RequestMapping(value = "/findId", method = RequestMethod.POST)
    public String findId(@RequestBody Map<String, String> email) throws Exception {
//        String IDChk = memberService.findId(email.get("email"));
        String code = findPasswordMailService.sendSimpleMessage(email.get("email"));
        return code;
    }

    @PostMapping("/findidcodecheck")
    public int mailcheck(@RequestBody Map<String, String> codecheck) {
        int result = registerMailService.mailCheck(codecheck.get("code"));
        return result;
    }
}