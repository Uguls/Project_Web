package WebCock.Project_Web.entity.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Board{

    @Id @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @JoinColumn(name="member_name")
    private String board_name;

    private String board_number;

}
