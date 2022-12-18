package mate.service.impl;

import mate.lib.Service;
import mate.service.PasswordValidateService;

@Service
public class PasswordValidateServiceImpl implements PasswordValidateService {
    private static final int MIN_LENGTH = 6;

    @Override
    public boolean validate(String password) {
        return (!password.isEmpty()) && isGreaterMinLength(password);
    }

    private boolean isGreaterMinLength(String password) {
        return password.length() >= MIN_LENGTH;
    }
}
