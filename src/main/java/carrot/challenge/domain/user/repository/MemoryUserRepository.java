package carrot.challenge.domain.user.repository;

import carrot.challenge.domain.user.dto.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static final Map<Long, User> data = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setUser_id(++sequence);
        data.put(user.getUser_id(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(data.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return findAll().stream()
                .filter(m -> m.getEmail().equals(userEmail))
                .findFirst();
    }

    @Override
    public Optional<User> findByNickname(String userNickname) {
        return findAll().stream()
                .filter(m -> m.getNickname().equals(userNickname))
                .findFirst();
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPhoneNumber) {
        return findAll().stream()
                .filter(m -> m.getPhone_number().equals(userPhoneNumber))
                .findFirst();
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
        findUser.setUploadFileName(updateParam.getUploadFileName());
        findUser.setStoreFileName(updateParam.getStoreFileName());
    }

    public void clearStore() {
        data.clear();
    }
}
