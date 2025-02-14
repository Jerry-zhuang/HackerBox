//package com.sends.hackerbox.config;
//
//import com.sends.hackerbox.util.PasswordGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
////@EnableWebSecurity
//public class SecurityConfig {
//
//    private final String encodedAdminPassword;
//    private final PasswordEncoder passwordEncoder;
//
//    public SecurityConfig() {
//        this.passwordEncoder = new BCryptPasswordEncoder();
//        // 初始化密码，如果未设置则生成强密码
//        String rawAdminPassword = initAdminPassword();
//        this.encodedAdminPassword = passwordEncoder.encode(rawAdminPassword);
//    }
//
//    private String initAdminPassword() {
//        // 从配置文件中读取 password
//        String password = System.getProperty("admin.password");
//        if (password == null || password.isEmpty()) {
//            String generatedPassword = PasswordGenerator.generateStrongPassword();
//            System.out.println("账号:  admin 密码： " + generatedPassword);
//            return generatedPassword;
//        }
//        return password;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
//                )
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("admin")
//                .password(encodedAdminPassword)
//                .passwordEncoder(pass -> encodedAdminPassword)
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return passwordEncoder;
//    }
//}