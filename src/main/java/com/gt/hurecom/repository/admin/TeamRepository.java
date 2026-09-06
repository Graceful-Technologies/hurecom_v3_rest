package com.gt.hurecom.repository.admin;

import com.gt.hurecom.dto.admin.TeamListResponse;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Team> findByIdIn(List<Long> ids);

    @Query("""
           SELECT new com.gt.hurecom.dto.admin.TeamListResponse(
                t.id,
                t.name,
                t.active,
                t.createdDate,
                COUNT(tm.id)
           )
           FROM Team t
           LEFT JOIN TeamMember tm ON tm.team.id = t.id AND tm.endDate IS NULL
           GROUP BY t.id, t.name, t.active, t.createdDate
           """)
    List<TeamListResponse> getTeams();

    @Query("""
           SELECT t
           FROM Team t
           LEFT JOIN FETCH t.createdUser
           LEFT JOIN FETCH t.lastModifiedUser
           WHERE t.id = :id
           """)
    Optional<Team> getTeamById(Long id);

    @Query("""
		SELECT t
		FROM Team t
		WHERE t.active = true
		AND NOT EXISTS (
			SELECT ja.id
			FROM JobAssignment ja
			WHERE ja.team.id = t.id
			AND ja.job.id = :jobId
			AND ja.endDate IS NULL
		)
	""")
    List<Team> findTeamsNotAssignedToJob(Long jobId);
}
