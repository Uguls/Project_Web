package WebCock.Project_Web.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private long id;                // id

    private String uid;

    @Column(length=200)
    private String upw;

    private String email;

    public Member() {}

    public Member(String uid, String upw) {
        this.uid = uid;
        this.upw = upw;
    }

    public static Member create(String uid, String upw) {
        return new Member(uid, upw);
    }
}