package com.toanlv.asynchspring.repositories;

import com.toanlv.asynchspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
