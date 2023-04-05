package WebCock.Project_Web.controller;

import WebCock.Project_Web.entity.model.Member;
import WebCock.Project_Web.service.FindPasswordMail;
import WebCock.Project_Web.service.MemberService;
import WebCock.Project_Web.service.RegisterMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    RegisterMail registerMail;
    @Autowired
    FindPasswordMail findPasswordMail;

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public int loginMember(@RequestBody Map<String, String> userInfo) {
        System.out.println(userInfo);
        Member loggedMember = memberService.login(userInfo);
        if (loggedMember != null) {
            System.out.println("SUCCESS");
            return 123;
        } else {
            System.out.println("FAIL");
            return -1;
        }
    }

    @PostMapping("/register")
    public int registerMember(@RequestBody Map<String, String> userInfo) {
        System.out.println("TestMessageUserInfo "+userInfo);
        int result = memberService.register(userInfo);
        System.out.println("TestMessageresult " + result);
        return result;
    }

    @RequestMapping(value = "/registermail")
    public String mailConfirm(@RequestBody Map<String, String> email) throws Exception {
        System.out.println(email);
        String code = registerMail.sendSimpleMessage(email.get("email"));
        System.out.println("사용자에게 발송한 인증코드 ==> " + code);
        return code;
    }

    @RequestMapping(value = "/findId", method = RequestMethod.POST)
    public String findId(@RequestBody Map<String, String> email) throws Exception {
//        String IDChk = memberService.findId(email.get("email"));
        String code = findPasswordMail.sendSimpleMessage(email.get("email"));
        return code;
    }

    @RequestMapping(value = "/findidcodecheck")
    public int mailcheck(@RequestBody Map<String, String> codecheck) {
        int result = registerMail.mailCheck(codecheck.get("code"));
        return result;
    }

//    @RequestMapping(value = "/findId", method = RequestMethod.GET)
//    public String findId(@RequestParam("uid") String userInfo) {
//        Map<String , String> map = new HashMap<String, String>();
//        map.put("uid", userInfo);
//        String result = memberService.findId(map);
//        return result;
//    }

}