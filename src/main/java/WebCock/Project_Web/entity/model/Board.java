package WebCock.Project_Web.entity.model;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Board {

    @Id // @Id = 기본키 지정
    @GeneratedValue
    private Long id;
    private String title;
    private String content;

    private String filename;

    private String filepath;
}