package carrot.challenge.domain.Interest.service;

import carrot.challenge.domain.Interest.dto.Interest;
import carrot.challenge.domain.Interest.repository.InterestRepository;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.repository.DBItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{

    private final InterestRepository interestRepository;
    private final DBItemRepository dbItemRepository;

    @Override
    public void addInterest(Long itemId, Long userId) {
        Interest interest = new Interest();
        interest.setItem_id(itemId);
        interest.setUser_id(userId);
        interestRepository.save(interest);
    }

    @Override
    public void deleteInterest(Long itemId, Long userId) {
        Optional<Interest> findInterest = interestRepository.findByUserIdItemId(userId, itemId);
        if (findInterest.isEmpty())
            return;
        interestRepository.delete(findInterest.get());
    }

    @Override
    public List<Item> findByInterest(Long userId) {
        List<Interest> interestList = interestRepository.findByUserId(userId);
        List<Item> items = new ArrayList<>();
        for (Interest interest : interestList) {
            Item item = dbItemRepository.findById(interest.getItem_id()).get();
            items.add(item);
        }
        return items;
    }
}
