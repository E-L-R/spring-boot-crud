package service;

import dto.UpdateUserRequest;
import model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    void save(User user);
    User findById(Long id);
    void update(Long id, UpdateUserRequest request);
    void delete(Long id);
}
