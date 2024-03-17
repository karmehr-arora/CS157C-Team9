package edu.team9.fitconnect.repository;

import edu.team9.fitconnect.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    @AllowFiltering //Not good for production because it adds "ALLOW FILTERING" to query
    Optional<User> findByEmail(String email);
}
