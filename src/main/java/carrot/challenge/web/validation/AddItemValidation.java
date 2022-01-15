package carrot.challenge.web.validation;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.web.add.AddForm;
import carrot.challenge.web.item.ItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AddItemValidation implements Validator {



    @Override
    public boolean supports(Class<?> clazz) {
        return ItemForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemForm itemForm = (ItemForm) target;
        if (itemForm.getPrice() == null)
            errors.rejectValue("price", "null");
    }
}
