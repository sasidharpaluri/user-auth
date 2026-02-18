package dev.sasidhar.userauth.Services;

import dev.sasidhar.userauth.Exceptions.IncorrectCredentials;
import dev.sasidhar.userauth.Exceptions.UserAlreadyExists;
import dev.sasidhar.userauth.Exceptions.UserNotFound;
import dev.sasidhar.userauth.Models.Role;
import dev.sasidhar.userauth.Models.State;
import dev.sasidhar.userauth.Models.User;
import dev.sasidhar.userauth.Repositories.RoleRepository;
import dev.sasidhar.userauth.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAuthService implements IUserAuthService{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User userSignUp(String name,String email,String password) {
        /*
        check if the user already exists or not (with mail)
         */
        Optional<User> u = userRepo.findUserByEmail(email);

        if(u.isPresent()){
            throw new UserAlreadyExists("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        /*
        BCrypt is now way hash -
         */

        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setLastModifiedAt(new Date());
        user.setState(State.ACTIVE);
        /*
        create role if not present
         */
        Optional<Role> getRole = roleRepo.findByName("DEFAULT");

        if(getRole.isEmpty()){
            Role role = new Role();
            role.setName("DEFAULT");
            role.setCreatedAt(new Date());
            role.setLastModifiedAt(new Date());
            role.setState(State.ACTIVE);
            user.setRoles(List.of(roleRepo.save(role)));
        }
        else {
            user.setRoles(List.of(getRole.get()));
        }

        return userRepo.save(user);


    }
    public User userLogin(String email,String password){
        Optional<User> user = userRepo.findUserByEmail(email);

                if(user.isPresent()){
                    if(passwordEncoder.matches(password,user.get().getPassword())) {
                        return user.get();
                    }
                    else
                        throw new IncorrectCredentials("Incorrect credentials entered");
                }
        throw new UserNotFound("User not found");
    }
}
