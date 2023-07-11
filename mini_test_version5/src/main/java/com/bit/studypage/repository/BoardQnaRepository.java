package com.bit.studypage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bit.studypage.entity.BoardQna;

//JpaRepository를 상속받도록 함으로써 기본적인 동작이 모두 가능
//JpaRepository<대상으로 지정할 엔티티, 해당 엔티티의 PK의 타입>.
@Repository
public interface BoardQnaRepository extends JpaRepository<BoardQna, Long> {

    //페이징
    Page<BoardQna> findAllByOrderByBoardIdDesc(Pageable pageable);

}