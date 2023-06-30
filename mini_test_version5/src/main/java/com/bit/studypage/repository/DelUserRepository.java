package com.bit.studypage.repository;

import com.bit.studypage.entity.DelUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelUserRepository extends JpaRepository<DelUsers,Long> {
}
