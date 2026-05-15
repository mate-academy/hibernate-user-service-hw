package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.security.impl.AuthenticationServiceImpl;

public class Main {
    public static void main(String[] args) {
        AuthenticationService authenticationService = new AuthenticationServiceImpl();

        try {
            User firstUser = authenticationService.register("jacek@interia.pl", "");
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }
        try {
            User secondUser = authenticationService.register("marek@gmail", "HelloWorld");
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());
        }
        try {
            User thirdUser = authenticationService.register("marek@gmail", "HelloWorld");
        } catch (RegistrationException e) {
            System.out.println(e.getMessage());;
        }
        try {
            System.out.println(authenticationService.login("marek@gmail", ""));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());;
        }
        try {
            System.out.println(authenticationService.login("marek@gmail", "HelloWorld"));
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());;
        }
    }
}
