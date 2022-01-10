package carrot.challenge.user.repository;

import carrot.challenge.user.dto.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Map<Long, User> data = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(data.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        for (User a : data.values()) {
            if (userEmail.equals(a.getEmail()))
                return Optional.ofNullable(a);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<User> findByNickname(String userNickname) {
        for (User a : data.values()) {
            if (userNickname.equals(a.getNickname()))
                return Optional.ofNullable(a);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPhoneNumber) {
        for (User a : data.values()) {
            if (userPhoneNumber.equals(a.getPhone_number()))
                return Optional.ofNullable(a);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(Long userId, User updateParam) {
        User findUser = findById(userId).get();
        findUser.setUsername(updateParam.getUsername());
        findUser.setEmail(updateParam.getEmail());
        findUser.setPassword(updateParam.getPassword());
        findUser.setPhone_number(updateParam.getPhone_number());
        findUser.setNickname(updateParam.getNickname());
    }
}
