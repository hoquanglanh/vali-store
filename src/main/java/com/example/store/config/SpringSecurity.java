package com.example.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // Kích hoạt Spring Security
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService; // Dịch vụ UserDetailsService để quản lý người dùng

    // Bean để mã hóa mật khẩu sử dụng thuật toán BCrypt
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Cấu hình Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF để tránh lỗi trong môi trường phát triển
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/").permitAll() // Cho phép tất cả truy cập trang chủ
                                .requestMatchers("/register/**").permitAll() // Cho phép truy cập trang đăng ký
                                .requestMatchers("/images/**").permitAll() // Cho phép truy cập thư mục ảnh
                                .requestMatchers("/index").permitAll() // Cho phép truy cập trang index
                                .requestMatchers("/products").permitAll() // Cho phép truy cập danh sách sản phẩm
                                .requestMatchers("/admin/products").hasRole("ADMIN") // Chỉ ADMIN mới được truy cập trang quản lý sản phẩm
                                .requestMatchers("/users").hasRole("ADMIN") // Chỉ ADMIN mới được truy cập trang quản lý người dùng
                                .anyRequest().permitAll() // Các request khác đều được cho phép
                )
                .formLogin(form ->
                        form
                                .loginPage("/login") // Trang đăng nhập tùy chỉnh
                                .loginProcessingUrl("/login") // URL xử lý form đăng nhập
                                .successHandler(authenticationSuccessHandler()) // Xử lý thành công sau khi đăng nhập
                                .permitAll() // Cho phép tất cả truy cập trang đăng nhập
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL logout
                                .permitAll() // Cho phép tất cả thực hiện logout
                );

        return http.build(); // Trả về cấu hình bảo mật
    }

    // Cấu hình dịch vụ xác thực người dùng và mã hóa mật khẩu
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) // Sử dụng UserDetailsService để quản lý người dùng
                .passwordEncoder(passwordEncoder()); // Sử dụng BCryptPasswordEncoder để mã hóa mật khẩu
    }

    // Xử lý thành công sau khi đăng nhập dựa trên vai trò
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")); // Kiểm tra vai trò ADMIN
            if (isAdmin) {
                response.sendRedirect("/admin/products"); // Chuyển hướng đến trang admin nếu là ADMIN
            } else {
                response.sendRedirect("/products"); // Chuyển hướng đến trang chủ nếu là người dùng thường
            }
        };
    }
}
