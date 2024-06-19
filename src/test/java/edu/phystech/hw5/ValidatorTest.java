package edu.phystech.hw5;

import edu.phystech.hw5.annotation.validation.NotBlank;
import edu.phystech.hw5.annotation.validation.Size;
import edu.phystech.hw5.exception.ValidationException;
import edu.phystech.hw5.service.Validator;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author kzlv4natoly
 */


class BlankAndSizeValidator implements Validator {
    private String field_content(Field field, Object object) {
        try {
            return field.get(object).toString();
        }
        catch (IllegalAccessException e) {
            field.setAccessible(true);
            try {
                return field.get(object).toString();
            }
            catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void validate(Object object) throws ValidationException {
        for (var f : object.getClass().getDeclaredFields()) {
            if (!(f.getType() == String.class))
                continue;

            if (f.isAnnotationPresent(Size.class)) {
                Size size = f.getAnnotation(Size.class);
                int field_size = this.field_content(f, object).length();
                if (field_size < size.min() | field_size > size.max()) {
                    throw new ValidationException(f.getAnnotation(Size.class).message());
                }
            }
            if (f.isAnnotationPresent(NotBlank.class)) {
                if (this.field_content(f, object).isBlank()) {
                    throw new ValidationException(f.getAnnotation(NotBlank.class).message());
                }
            }
        }
    }
}


public class ValidatorTest {

    private Validator validator = new BlankAndSizeValidator();

    @Test
    void notBlankWorks() {
        class Example {

            @NotBlank
            private final String x;

            @NotBlank(message = "This is a very important field and it can't be empty!")
            private final String y;

            Example(String x, String y) {
                this.x = x;
                this.y = y;
            }
        }

        Assertions.assertDoesNotThrow(() -> validator.validate(new Example("123", "567")));
        ValidationException exception =
                Assertions.assertThrows(ValidationException.class, () -> validator.validate(new Example("11", "")));
        Assertions.assertEquals("This is a very important field and it can't be empty!", exception.getMessage());
    }

    @Test
    void sizeWorks() {
        class Example {
            @Size(max = 52, message = "Long live Saint Petersburg!")
            private final String x;

            @Size(min = 5, max = 11)
            private final String y;

            Example(String x, String y) {
                this.x = x;
                this.y = y;
            }
        }

        Assertions.assertDoesNotThrow(() -> validator.validate(new Example("123", "567765")));
        ValidationException exception =
                Assertions.assertThrows(ValidationException.class, () -> validator.validate(new Example("", "")));
        Assertions.assertEquals("Long live Saint Petersburg!", exception.getMessage());
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(new Example("", "0000000000000")));
    }

}
