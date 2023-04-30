package WebCock.Project_Web.entity.model;

import javax.persistence.*;

import WebCock.Project_Web.dto.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private long id; // id(PK)

    private String username; // 이름
    private String upw; // 비밀번호(암호화)
    private String rawpw; // 비밀번호
    private String uid; // id
    private String email; // 이메일

    @Enumerated(EnumType.STRING) // 문자열로 저장
    private Role role; // ROLE_USER, ROLE_ADMIN

    @Builder
    public Member(Long id, String username, String email, String upw, String uid, Role role, String rawpw) {
        this.username = username;
        this.email = email;
        this.upw = upw;
        this.uid = uid;
        this.role = role;
        this.rawpw = rawpw;
    }
}