package pl.polsl.rest.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.rest.api.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByNameAndPassword(final String name, final String password);

    Optional<User> findUserByName(final String name);

    @Override
    List<User> findAll();
}
