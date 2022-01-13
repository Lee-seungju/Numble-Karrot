package carrot.challenge.domain.user.service;

import carrot.challenge.web.add.AddForm;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.MemoryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoryUserService implements UserService {

    private final MemoryUserRepository memoryUserRepository;

    @Override
    public void justSave(User user) {
        memoryUserRepository.save(user);
    }

    @Override
    public Long save(AddForm addForm) {
        if (memoryUserRepository.findByEmail(addForm.getEmail()).isPresent() ||
                memoryUserRepository.findByNickname(addForm.getNickname()).isPresent() ||
                memoryUserRepository.findByPhoneNumber(addForm.getPhone_number()).isPresent())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        User saveUser = memoryUserRepository.save(makeUserFromAddForm(addForm));

        return saveUser.getId();
    }

    User makeUserFromAddForm(AddForm addForm) {
        User user = new User();
        user.setEmail(addForm.getEmail());
        user.setPassword(addForm.getPassword());
        user.setPhone_number(addForm.getPhone_number());
        user.setNickname(addForm.getNickname());
        user.setUsername(addForm.getUsername());
        return user;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return memoryUserRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return memoryUserRepository.findByEmail(userEmail);
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPN) {
        return memoryUserRepository.findByPhoneNumber(userPN);
    }

    @Override
    public Optional<User> findByNickName(String userNick) {
        return memoryUserRepository.findByNickname(userNick);
    }

    @Override
    public void updateUser(Long userId, User user) {
        memoryUserRepository.update(userId, user);
    }
}
