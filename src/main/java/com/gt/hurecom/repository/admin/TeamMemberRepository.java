package com.gt.hurecom.repository.admin;

import com.gt.hurecom.entity.admin.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    boolean existsByUser_IdAndTeam_IdAndEndDateIsNull(Long userId, Long TeamId);

    List<TeamMember> findByUser_IdAndEndDateIsNull(Long userId);

    List<TeamMember> findByTeam_IdAndEndDateIsNull(Long teamId);

    List<TeamMember> findByEndDateIsNull();

    @Query("""
           SELECT COUNT(tm)
           FROM TeamMember tm
           WHERE tm.team.id = :teamId
           AND tm.endDate IS NULL
    """)
    Long countActiveMembers(Long teamId);

}
