package service;

import dao.RoleRepository;
import dto.UpdateUserRequest;
import dao.UserRepository;
import lombok.RequiredArgsConstructor;
import model.Role;
import model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(resolveRoles(user.getRoles()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public void update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return;
        }

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());

        // Only update password if a new one was provided
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Resolve roles from DB
        if (request.getRoles() != null) {
            Set<Role> resolvedRoles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                roleRepository.findByName(roleName).ifPresent(resolvedRoles::add);
            }
            user.setRoles(resolvedRoles);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    private Set<Role> resolveRoles(Set<Role> roles) {
        Set<Role> resolved = new HashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                roleRepository.findByName(role.getName()).ifPresent(resolved::add);
            }
        }
        return resolved;
    }
}
