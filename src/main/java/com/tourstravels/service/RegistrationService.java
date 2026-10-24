package com.tourstravels.service;
import com.tourstravels.model.*; import com.tourstravels.repository.UserRepository; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class RegistrationService {
 private final UserRepository users; private final PasswordEncoder encoder; public RegistrationService(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @Transactional public AppUser register(String name,String email,String password){String normalized=email.trim().toLowerCase(); if(users.existsByEmail(normalized)) throw new IllegalArgumentException("An account with this email already exists."); AppUser u=new AppUser();u.setFullName(name.trim());u.setEmail(normalized);u.setPassword(encoder.encode(password));u.setRole(Role.USER);return users.save(u);}
}
