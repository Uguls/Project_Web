package WebCock.Project_Web.repository;

import WebCock.Project_Web.entity.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> { // <Entity명, 기본키의 데이터값>


}
