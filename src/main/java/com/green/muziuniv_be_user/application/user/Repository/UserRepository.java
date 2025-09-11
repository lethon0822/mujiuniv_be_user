package com.green.muziuniv_be_user.application.user.Repository;

import com.green.muziuniv_be_user.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
