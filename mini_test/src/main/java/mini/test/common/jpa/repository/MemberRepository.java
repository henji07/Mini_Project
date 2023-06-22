package mini.test.common.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mini.test.common.jpa.entity.Member;


@Repository //스프링 빈으로 등록, 스프링이 해줌
public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	//중복 체크 
	List<Member> findAllByUserId(String userId);

}
