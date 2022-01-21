package com.example.carrental.service;

import com.example.carrental.model.ApplicationUser;
import com.example.carrental.model.ApplicationUserRole;
import com.example.carrental.model.dto.ApplicationUserDto;
import com.example.carrental.model.dto.RegisterApplicationUserDto;
import com.example.carrental.repository.ApplicationUserRepository;
import com.example.carrental.repository.ApplicationUserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserRoleRepository applicationUserRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByUsername(username);
        if(applicationUserOptional.isPresent()){
            return applicationUserOptional.get();
        }
        throw new UsernameNotFoundException("User with username: " + username + " was not found.");
    }

    public Set<ApplicationUserRole> loadRolesByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByUsername(username);
        if(applicationUserOptional.isPresent()){
            return applicationUserOptional.get().getRoles();
        }
        throw new UsernameNotFoundException("User with username: " + username + " was not found.");
    }

    public Optional<Long> getLoggedInUserId(Principal principal){
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return Optional.of(userDetails.getId());
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return Optional.empty();
    }

    public boolean isAdmin(Principal principal){
        if (principal != null){
            log.info("Jesteśmy zalogowani, informacja o użytkowniku: " + principal);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            try{
                ApplicationUser userDetails = (ApplicationUser) loadUserByUsername((String) usernamePasswordAuthenticationToken.getPrincipal());
                return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_ADMIN");
            }catch (UsernameNotFoundException usernameNotFoundException){
                log.info("Nie jesteśmy zalogowani");
            }
        }
        return false;
    }

    public ApplicationUserDto getLoggedInUserDto(Long loggedInUserId) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findById(loggedInUserId);
        if(applicationUserOptional.isPresent()){
            ApplicationUser applicationUser = applicationUserOptional.get();
            return ApplicationUserDto.builder()
                    .id(applicationUser.getId())
                    .username(applicationUser.getUsername())
                    .admin(applicationUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ROLE_ADMIN"))
                    .build();
        }
        throw new UsernameNotFoundException("User with username: " + loggedInUserId + " was not found.");
    }

    public void register(RegisterApplicationUserDto dto) {
        String roleName = dto.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        Set<ApplicationUserRole> roles = new HashSet<>();
        Optional<ApplicationUserRole> optionalRole = applicationUserRoleRepository.findByName(roleName);
        if (optionalRole.isPresent()){
            roles.add(optionalRole.get());
        }else{
            log.error("Could not find role: " + roleName);
            throw new EntityNotFoundException("Could not find role: " + roleName);
        }

        ApplicationUser user = ApplicationUser.builder()
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .build();

        applicationUserRepository.save(user);
    }
}
