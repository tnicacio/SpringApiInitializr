package com.tnicacio.springapiinitializr.user.repository;

import com.tnicacio.springapiinitializr.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
