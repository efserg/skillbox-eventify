package com.skillbox.eventify.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
