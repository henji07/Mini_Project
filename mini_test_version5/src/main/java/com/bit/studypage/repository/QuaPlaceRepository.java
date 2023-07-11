package com.bit.studypage.repository;

import com.bit.studypage.entity.QuaPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuaPlaceRepository extends JpaRepository<QuaPlace,Long> {
    @Query(value = "SELECT *, ( 6371 * acos( cos( radians(:lat) ) " +
            "* cos( radians( y ) ) " +
            "* cos( radians( x ) - radians(:lng) ) " +
            "+ sin( radians(:lat) ) " +
            "* sin( radians( y ) ) ) ) AS distance " +
            "FROM qua_place " +
            "HAVING distance < 3 " +
            "ORDER BY distance", nativeQuery = true)   // 가까운 순서대로 정렬합니다.
    List<QuaPlace> findNearByLocations(@Param("lat") double lat, @Param("lng") double lng);
}
