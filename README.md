# Hibernate - create User service

We will continue working on our Cinema project.

Your task is to implement the following steps:
- Create models:
  - User


- Create DAO:
    - UserDAO
    

- Create service:
    - UserService:
        ````java
        public interface UserService {
            User add(User user);

            Optional<User> findByEmail(String email); // we will use this `Optional` later
        }
        ````
    
    - AuthenticationService
        ````java
        public interface AuthenticationService {
            User login(String email, String password) throws AuthenticationException;

            /**
            * We should register a new user. New user entity will contains the email and password
            * @param email - user email. should be unique for each user
            * @param password - user password
            * @return new user instance
            */
            User register(String email, String password) throws RegistrationException;
        }
        ````
    - Create your own checked `RegistrationException` and `AuthenticationException` to use it in `register()` and `login()` methods respectively

    - Please don’t forget to use salt and password hashing
- In the `mate/academy/Main.main()` method create instance of AuthenticationService using injector and test all methods from it.

[Try to avoid these common mistakes, while solving task](https://mate-academy.github.io/jv-program-common-mistakes/hibernate/add-user-service/add-user-service-checklist)
