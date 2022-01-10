package carrot.challenge.domain.user.service;

import carrot.challenge.web.add.AddForm;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoryUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public void justSave(User user) {
        userRepository.save(user);
    }

    @Override
    public Long save(AddForm addForm) {
//        if (checkAlreadyUser(addForm, mav)) {
//            User saveUser = userRepository.save(makeUserFromAddForm(addForm));
//            return saveUser.getId();
//        }
        User saveUser = userRepository.save(makeUserFromAddForm(addForm));

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

//    boolean checkAlreadyUser(AddForm addForm, ModelAndView mav) {
//        if (addForm.getUsername().length() == 0 || addForm.getEmail().length() == 0 ||
//                addForm.getNickname().length() == 0 || addForm.getPassword().length() == 0 ||
//                addForm.getPhone_number().length() == 0) {
//            setMessageAndURL("입력받지 못한 항목이 있습니다.", "/user/add", mav);
//            return false;
//        }
//        if (userRepository.findByEmail(addForm.getEmail()).isPresent()) {
//            setMessageAndURL("이미 가입된 이메일입니다.", "/user/add", mav);
//            return false;
//        }
//        if (userRepository.findByNickname(addForm.getNickname()).isPresent()) {
//            setMessageAndURL("이미 사용중인 닉네임입니다.", "/user/add", mav);
//            return false;
//        }
//        if (userRepository.findByPhoneNumber(addForm.getPhone_number()).isPresent()) {
//            setMessageAndURL("이미 가입된 핸드폰 번호입니다.", "/user/add", mav);
//            return false;
//        }
//        setMessageAndURL("성공적으로 가입을 완료하였습니다.", "/user", mav);
//        return true;
//    }

    void setMessageAndURL(String message, String url, ModelAndView mav) {
        mav.addObject("message", message);
        mav.addObject("href", url);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public Optional<User> findByPhoneNumber(String userPN) {
        return userRepository.findByPhoneNumber(userPN);
    }

    @Override
    public Optional<User> findByNickName(String userNick) {
        return userRepository.findByNickname(userNick);
    }
}
