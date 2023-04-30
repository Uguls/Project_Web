package WebCock.Project_Web.service;

import WebCock.Project_Web.config.jwt.JwtTokenProvider;
import WebCock.Project_Web.dto.JwtToken;
import WebCock.Project_Web.dto.Role;
import WebCock.Project_Web.entity.model.Member;
import WebCock.Project_Web.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class LoginService implements UserDetailsService{

    final
    MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public LoginService(MemberRepository memberRepository, BCryptPasswordEncoder encoder, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUid(uid);
        if (member == null) {
            throw new UsernameNotFoundException("User Not Found with uid: " + uid);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().getKey()));

        return new User(member.getUid(), member.getUpw(), authorities);
    }

    public JwtToken  login(Map<String, String> userinfo) {
        String uid = userinfo.get("uid");
        String upw = userinfo.get("upw");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(uid, upw);

        // 검증
        Authentication authentication = new UsernamePasswordAuthenticationToken(uid, upw, loadUserByUsername(uid).getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtToken  token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    public int register(Map<String, String> userInfo) {
        String uid = userInfo.get("uid");
        String email = userInfo.get("email");

        Member existingMember = memberRepository.findByUid(uid);
        Member existingMemberemail = memberRepository.findByEmail(email);

        if (existingMember != null || existingMemberemail != null) {
            System.out.println("중복 id 또는 중복 이메일 존재");
            return -1;
        }

        String rawPw = userInfo.get("upw");
        String encodedPw = encoder.encode(rawPw);

        Member member = Member.builder()
                .uid(uid)
                .email(email)
                .username(userInfo.get("username"))
                .upw(encodedPw)
                .rawpw(rawPw)
                .role(Role.USER)
                .build();
        memberRepository.save(member);
        System.out.println("회원가입 완료");
        return 1;
    }

    public String findPw(String email) {
        System.out.println("TESTEMAILMESSAGE" + email);
        Member IDChk = memberRepository.findByEmail(email);
        System.out.println("TESTMESSAGE" + IDChk);
        if(IDChk == null) return "-1";
        String password = IDChk.getRawpw();
        return password;
    }
    public String findId(String email) {
        System.out.println("TESTEMAILMESSAGE" + email);
        Member IDChk = memberRepository.findByEmail(email);
        System.out.println("TESTMESSAGE" + IDChk);
        if(IDChk == null) return "-1";
        String Id = IDChk.getUid();
        return Id;
    }
}