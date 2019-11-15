package Ogma.repo;

import Ogma.domain.Role;
import Ogma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User findByActivationCode(String code);

    List<User> findUsersByRoles(Role role);
}
