package carrot.challenge.domain.user.repository;

import carrot.challenge.domain.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class DBUserRepository implements UserRepository{

    private final EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByNickname(String userNickname) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPhoneNumber) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(Long userId, User updateParam) {

    }
}
