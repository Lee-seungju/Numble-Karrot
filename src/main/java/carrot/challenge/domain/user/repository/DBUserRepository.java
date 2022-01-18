package carrot.challenge.domain.user.repository;

import carrot.challenge.domain.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class DBUserRepository implements UserRepository{

    private final EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        List<User> result = em.createQuery("select m from User m where m.email =: email", User.class)
                .setParameter("email", userEmail)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByNickname(String userNickname) {
        List<User> result = em.createQuery("select u from User u where u.nickname =: nickname", User.class)
                .setParameter("nickname", userNickname)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPhoneNumber) {
        List<User> result = em.createQuery("select u from User u where u.phone_number =: phone_number", User.class)
                .setParameter("phone_number", userPhoneNumber)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    @Override
    public void update(Long userId, User updateParam) {
        updateParam.setUser_id(userId);
        em.merge(updateParam);
    }
}
