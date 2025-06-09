package br.com.fiap.motoflow.service;

import br.com.fiap.motoflow.exceptions.OperadorNotFoundException;
import br.com.fiap.motoflow.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private OperadorRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(username)).orElseThrow(
                () -> new OperadorNotFoundException("Usuario não encontrado"));
    }


}
