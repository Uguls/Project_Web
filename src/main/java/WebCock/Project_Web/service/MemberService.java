package WebCock.Project_Web.service;

import WebCock.Project_Web.entity.model.Member;
import WebCock.Project_Web.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public int register(Map<String, String> userInfo) {
        Member existingMember = memberRepository.findByUid(userInfo.get("uid"));
        Member existingMemberemail = memberRepository.findByEmail(userInfo.get("email"));
        if (existingMember != null && existingMemberemail != null) {
            return -1;
        }
        if (!userInfo.get("upw").equals(userInfo.get("checkpw"))) {
            return -2;
        }

        String Encodepassword = passwordEncoder.encode( userInfo.get("upw"));

        Member member = new Member();
        member.setUid(userInfo.get("uid"));
        member.setEmail(userInfo.get("email"));
        member.setRoles("ROLE_USER");
        member.setUpw(Encodepassword);

        memberRepository.save(member);
        return 1;
    }

    public Member login(Map<String, String> userInfo) {
        Member member = memberRepository.findByUid(userInfo.get("uid"));
        System.out.println("TestMemberMassage: " + member);
        if ((member == null) || !userInfo.get("upw").equals(member.getUpw())) {
            return null;
        }
        return member;
    }

    public String findId(String email) {
        System.out.println("TESTEMAILMESSAGE" + email);
        Member IDChk = memberRepository.findByEmail(email);
        System.out.println("TESTMESSAGE" + IDChk);
        if(IDChk == null) return "-1";
        String password = IDChk.getUpw();
        return password;
    }
}