package Ogma.service;

import Ogma.domain.Role;
import Ogma.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserDetails loadUserByUsername(String username);

    boolean addUser(User user);

    void sendMessage(User user);

    boolean activateUser(String code);

    void saveUser(User user, String username, Map<String, String> form);

    void updateProfile(User user, String username, String password, String email, String firstName,
                       String lastName, String phone);

    List<User> findUsersByRoles(Role role);
}
