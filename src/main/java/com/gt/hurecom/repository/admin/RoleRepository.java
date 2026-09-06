package com.gt.hurecom.repository.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gt.hurecom.entity.admin.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String roleName);

	@Query("""
		SELECT r
		FROM Role r
		WHERE r.name NOT IN ('ROLE_SYSTEM')
	""")
	List<Role> getRoles();
}
