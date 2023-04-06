package WebCock.Project_Web.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays.*;
import java.util.List;


@Entity
@Data
public class Member {

    @Id
    @GeneratedValue
    private long id;                // id
    private String username;
    private String upw;
    private String uid;
    private String email;
    private String roles; //ROLE_USER, ROLE_ADMIN

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public Member() {}
    public Member(String uid, String upw) {
        this.uid = uid;
        this.upw = upw;
    }

    public static Member create(String uid, String upw) {
        return new Member(uid, upw);
    }
}