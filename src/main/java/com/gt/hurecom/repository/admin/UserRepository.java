package com.gt.hurecom.repository.admin;

import com.gt.hurecom.entity.admin.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NullMarked
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	Optional<User> findByEmail(String username);

	boolean existsByEmailIgnoreCaseOrMobileNumber(String email, String mobileNumber);

	boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

	boolean existsByMobileNumberAndIdNot(String mobileNumber, Long id);

	List<User> findByOrganization_IdAndActiveTrue(Long organizationId);

	List<User> findByIdIn(List<Long> ids);

	@Query("""
		SELECT u
		FROM User u
		LEFT JOIN FETCH u.role
		LEFT JOIN FETCH u.organization
		WHERE u.active = true
		AND NOT EXISTS (
			SELECT tm.id
			FROM TeamMember tm
			WHERE tm.user.id = u.id
			AND tm.team.id = :teamId
			AND tm.endDate IS NULL
		)
	""")
	List<User> findUsersNotInTeam(Long teamId);

	@EntityGraph(attributePaths = {"role", "organization"})
	Page<User> findAll(Specification<User> spec, Pageable pageable);

}
