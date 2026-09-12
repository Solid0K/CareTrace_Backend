package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.Model.Client;
import com.krishu.caretracev2.Repository.UserRepo;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailService(UserRepo clientRepo){
        this.userRepo=clientRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Client client=userRepo.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        return User.builder().username(client.getId()).
                password(client.getPassword()).
                authorities(new SimpleGrantedAuthority("ROLE_" + client.getRole().name())).build();
    }
}
