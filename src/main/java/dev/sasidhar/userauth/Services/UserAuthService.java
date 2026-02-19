package dev.sasidhar.userauth.Services;

import dev.sasidhar.userauth.Exceptions.IncorrectCredentials;
import dev.sasidhar.userauth.Exceptions.UserAlreadyExists;
import dev.sasidhar.userauth.Exceptions.UserNotFound;
import dev.sasidhar.userauth.Models.Role;
import dev.sasidhar.userauth.Models.State;
import dev.sasidhar.userauth.Models.User;
import dev.sasidhar.userauth.Repositories.RoleRepository;
import dev.sasidhar.userauth.Repositories.UserRepository;
import dev.sasidhar.userauth.pojos.User_Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserAuthService implements IUserAuthService{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SecretKey secretKey;

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
        BCrypt is a one way hash - Once encrypted is cannot be decrypted
        the only way to check is to rehash the password and compare the already encrypted one
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

    @Override
    public User adminSignUp(String name,String email,String password) {
        Optional<User> u = userRepo.findUserByEmail(email);
        if(u.isPresent()){
            throw new UserAlreadyExists("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setLastModifiedAt(new Date());
        user.setState(State.ACTIVE);

        Optional<Role> getRole = roleRepo.findByName("ADMIN");
        if(getRole.isEmpty()){
            Role role = new Role();
            role.setName("ADMIN");
            role.setCreatedAt(new Date());
            role.setLastModifiedAt(new Date());
            role.setState(State.ACTIVE);
            user.setRoles(List.of(roleRepo.save(role)));
        } else {
            user.setRoles(List.of(getRole.get()));
        }
        return userRepo.save(user);
    }
    public User_Token userLogin(String email, String password){
        Optional<User> user = userRepo.findUserByEmail(email);

                if(user.isPresent()){
                    if(passwordEncoder.matches(password,user.get().getPassword())) {
                        /*
                        Generate JWT token
                        STEP 1 : generate payload
                            - createdAT : "iat",
                            - expiry : "exp",
                            - userId : "usrId",
                            - creator : "iss",
                            - scope : "scope
                         STEP 2 : Generate token
                            - to generate token we need a secretKey
                            - which are autowiring - this requires a Alogorithm like HS256
                            - using the prev generated payload we generate a token

                         */
                        // STEP 1
                        HashMap<String,Object> Hm = new HashMap<>();
                        long createdTime = System.currentTimeMillis();
                        Hm.put("iat",createdTime);
                        Hm.put("exp", createdTime + 1000000);
                        Hm.put("usrId", user.get().getId());
                        Hm.put("scope", user.get().getRoles());

                        // STEP 2
                        String token = Jwts.builder().claims(Hm).signWith(secretKey).compact();
                        System.out.println(token);
                        System.out.println(token.length());






                        return new User_Token(user.get(),token);
                    }
                    else
                        throw new IncorrectCredentials("Incorrect credentials entered");
                }
        throw new UserNotFound("User not found");
    }

    @Override
    public Boolean validateToken(String token) {
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        System.out.println(claims);
        long currtime = System.currentTimeMillis();
        return currtime <= (Long) claims.get("exp");
    }

    @Override
    public Boolean validateAdminToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();
            long currtime = System.currentTimeMillis();
            
            // Check if token is expired
            if(currtime > (Long) claims.get("exp")) {
                return false;
            }
            
            // Get roles from token
            List<?> roles = (List<?>) claims.get("scope");
            
            // Check each role to see if it's ADMIN
            for(Object role : roles) {
                Map<?, ?> roleMap = (Map<?, ?>) role;
                String roleName = (String) roleMap.get("name");
                if("ADMIN".equals(roleName)) {
                    return true;  // Found ADMIN role!
                }
            }
            
            return false;  // No ADMIN role found
        } catch (Exception e) {
            return false;
        }
    }
}
