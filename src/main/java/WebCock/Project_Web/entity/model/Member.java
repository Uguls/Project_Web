package WebCock.Project_Web.entity.model;

import javax.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
public class Member {

    @Id
    @GeneratedValue
    private long id;                // id(PK)
    private String username;        // 이름
    private String upw;             // 비밀번호(암호화)
    private String rawpw;           // 비밀번호
    private String uid;             // id
    private String email;           // 이메일
    private String roles; //ROLE_USER, ROLE_ADMIN

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}