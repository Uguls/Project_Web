package WebCock.Project_Web.repository;

import WebCock.Project_Web.entity.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public interface MemberRepository extends JpaRepository<Member, Long> {
//    public Member findById(String uid);
//}

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByUid(String uid);
    public Member findByEmail(String email);
}
