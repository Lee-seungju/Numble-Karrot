package carrot.challenge.domain.user.service;

import carrot.challenge.domain.Interest.dto.Interest;
import carrot.challenge.domain.user.repository.DBUserRepository;
import carrot.challenge.web.add.AddForm;
import carrot.challenge.domain.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DBUserRepository dbUserRepository;

    @Override
    public void justSave(User user) {
        dbUserRepository.save(user);
    }

    @Override
    public Long save(AddForm addForm) {
        if (dbUserRepository.findByEmail(addForm.getEmail()).isPresent() ||
                dbUserRepository.findByNickname(addForm.getNickname()).isPresent() ||
                dbUserRepository.findByPhoneNumber(addForm.getPhone_number()).isPresent())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        User saveUser = dbUserRepository.save(makeUserFromAddForm(addForm));

        return saveUser.getUser_id();
    }

    User makeUserFromAddForm(AddForm addForm) {
        User user = User.builder()
                .email(addForm.getEmail())
                .password(addForm.getPassword())
                .phone_number(addForm.getPhone_number())
                .nickname(addForm.getNickname())
                .username(addForm.getUsername())
                .build();
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return dbUserRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return dbUserRepository.findByEmail(userEmail);
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPN) {
        return dbUserRepository.findByPhoneNumber(userPN);
    }

    @Override
    public Optional<User> findByNickName(String userNick) {
        return dbUserRepository.findByNickname(userNick);
    }

    @Override
    public String UserInterestItem(Long userId, Long itemId) {
        User user = dbUserRepository.findById(userId).get();
        if (user.getInterests().isEmpty())
            return "false";
        Optional<Interest> findItem = user.getInterests().stream()
                .filter(m -> m.getItem_id().equals(itemId))
                .findFirst();
        if (findItem.isEmpty())
            return "false";
        return "true";
    }

    @Override
    public void updateUser(Long userId, User user) {
        dbUserRepository.update(userId, user);
    }
}
