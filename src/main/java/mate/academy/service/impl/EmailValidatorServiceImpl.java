package mate.academy.service.impl;

import java.util.regex.Pattern;
import mate.academy.exception.DataValidationException;
import mate.academy.lib.Service;
import mate.academy.service.EmailValidatorService;

@Service
public class EmailValidatorServiceImpl implements EmailValidatorService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+@\\w+\\.\\w+");

    @Override
    public boolean isValid(String email) throws DataValidationException {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new DataValidationException("Email isn't valid");
        }
        return true;
    }
}
