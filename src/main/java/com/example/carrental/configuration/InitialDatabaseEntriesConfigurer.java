package com.example.carrental.configuration;

import com.example.carrental.model.ApplicationUser;
import com.example.carrental.model.ApplicationUserRole;
import com.example.carrental.repository.ApplicationUserRepository;
import com.example.carrental.repository.ApplicationUserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDatabaseEntriesConfigurer implements ApplicationListener<ContextRefreshedEvent> {
    private final static String ADMIN_USERNAME = "admin";
    private final static String ADMIN_PASSWORD = "nimda";

    private final static String ROLE_ADMIN = "ROLE_ADMIN";
    private final static String ROLE_USER = "ROLE_USER";
    private final static String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    private final static List<String> AVAILABLE_ROLES = Arrays.asList(ROLE_ADMIN, ROLE_USER, ROLE_EMPLOYEE);


    private final BCryptPasswordEncoder encoder;
    private final ApplicationUserRepository applicationUserRepository;
    private final ApplicationUserRoleRepository applicationUserRoleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createRoles();
        createUsers();
    }

    private void createUsers() {
        addUserIfNotExists(ADMIN_USERNAME, ADMIN_PASSWORD, AVAILABLE_ROLES);
        addUserIfNotExists("user", "user", Arrays.asList(ROLE_USER));
        addUserIfNotExists("employee", "employee", Arrays.asList(ROLE_EMPLOYEE));
    }

    private void addUserIfNotExists(String username, String password, List<String> availableRoles) {
        if(!applicationUserRepository.existsByUsername(username)){

            Set<ApplicationUserRole> roles = new HashSet<>();
            for (String role : availableRoles) {
                Optional<ApplicationUserRole> optionalRole = applicationUserRoleRepository.findByName(role);
                if (optionalRole.isPresent()){
                    roles.add(optionalRole.get());
                }else{
                    log.error("Could not find role: " + role);
                }
            }

            ApplicationUser user = ApplicationUser.builder()
                    .username(username)
                    .password(encoder.encode(password))
                    .roles(roles)
                    .build();
            applicationUserRepository.save(user);
        }
    }

    private void createRoles() {
        for (String availableRole: AVAILABLE_ROLES) {
            addIfNotExists(availableRole);
        }
    }

    private void addIfNotExists(String availableRole) {
        if(!applicationUserRoleRepository.existsByName(availableRole)){
            ApplicationUserRole role = ApplicationUserRole.builder().name(availableRole).build();
            applicationUserRoleRepository.save(role);
        }
    }
}
