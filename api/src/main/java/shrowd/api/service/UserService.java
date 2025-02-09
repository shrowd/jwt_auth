package shrowd.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import shrowd.api.config.AppUser;
import shrowd.api.entity.User;
import shrowd.api.enums.Role;
import shrowd.api.exception.AlreadyExistsException;
import shrowd.api.exception.ResourceNotFoundException;
import shrowd.api.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new AppUser(user);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = (AppUser) loadUserByUsername(email);
        User user = appUser.getUser();

        if (user == null) {
            throw new ResourceNotFoundException("User not found!");
        }

        return user;
    }

    @Transactional
    public void registerUser(
            String firstName,
            String lastName,
            String email,
            String password) {

        if (userRepo.existsByEmail(email)) {
            throw new AlreadyExistsException("Email address is already registered");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(Role.USER);

        userRepo.save(user);
    }
}
