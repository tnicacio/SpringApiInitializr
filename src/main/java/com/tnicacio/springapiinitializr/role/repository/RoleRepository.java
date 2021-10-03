package com.tnicacio.springapiinitializr.role.repository;

import com.tnicacio.springapiinitializr.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
