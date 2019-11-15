package Ogma.service.impl;

import Ogma.domain.Role;
import Ogma.domain.User;
import Ogma.repo.UserRepo;
import Ogma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepo userRepo;
    private final MailSenderImpl mailSenderImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, MailSenderImpl mailSenderImpl,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mailSenderImpl = mailSenderImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Пользователь не найден");

        return user;
    }

    @Override
    public boolean addUser(User user) {
        User userFromDb = userRepo.findUserByUsername(user.getUsername());

        if (userFromDb != null)
            return false;

        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    @Override
    public void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Weclome to JobHunter. Please visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSenderImpl.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null)
            return false;

        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);

        return true;
    }

    @Override
    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    @Override
    public void updateProfile(User user, String username, String password, String email, String firstName,
                              String lastName, String phone) {

        if (!StringUtils.isEmpty(password))
            user.setPassword(passwordEncoder.encode(password));

        if (!StringUtils.isEmpty(username))
            user.setUsername(username);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);

        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !StringUtils.isEmpty(email) && !email.equals(userEmail)) ||
                (userEmail != null && !StringUtils.isEmpty(userEmail) && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
            user.setActive(false);
            sendMessage(user);
        }

        userRepo.save(user);

    }

    @Override
    public List<User> findUsersByRoles(Role role) {
        return userRepo.findUsersByRoles(role);
    }
}
