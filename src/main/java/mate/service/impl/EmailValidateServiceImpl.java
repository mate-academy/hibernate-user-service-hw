package mate.service.impl;

import mate.lib.Service;
import mate.service.EmailValidateService;

@Service
public class EmailValidateServiceImpl implements EmailValidateService {
    private static final int MIN_LENGTH = 6;

    @Override
    public boolean validate(String email) {
        return (!email.isEmpty())
                && isAtInEmail(email)
                && isGreaterMinLength(email)
                && isAtInEmail(email);
    }

    private boolean isGreaterMinLength(String email) {
        return email.length() >= MIN_LENGTH;
    }

    private boolean isAtInEmail(String email) {
        return email.contains("@");
    }

    private boolean isDoteInEmail(String email) {
        return email.contains(".");
    }
}
