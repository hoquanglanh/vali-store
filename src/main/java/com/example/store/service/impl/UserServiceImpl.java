package com.example.store.service.impl;

import com.example.store.dto.UserDto;
import com.example.store.entity.Role;
import com.example.store.entity.User;
import com.example.store.repository.RoleRepository;
import com.example.store.repository.UserRepository;
import com.example.store.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository; // Repository để thao tác với bảng users
    private RoleRepository roleRepository; // Repository để thao tác với bảng roles
    private PasswordEncoder passwordEncoder; // Dùng để mã hóa mật khẩu

    // Constructor để inject các dependency cần thiết
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        // Gộp họ và tên từ DTO và set vào entity User
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // Mã hóa mật khẩu trước khi lưu vào database
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Kiểm tra xem database có người dùng nào chưa
        List<User> existingUsers = userRepository.findAll();

        Role role; // Biến lưu vai trò của người dùng
        if (existingUsers.isEmpty()) {
            // Nếu là người dùng đầu tiên, gán vai trò "ROLE_ADMIN"
            role = roleRepository.findByName("ROLE_ADMIN");
            if (role == null) {
                role = checkRoleExist("ROLE_ADMIN"); // Tạo ROLE_ADMIN nếu chưa có
            }
        } else {
            // Nếu không phải người dùng đầu tiên, gán vai trò "ROLE_USER"
            role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = checkRoleExist("ROLE_USER"); // Tạo ROLE_USER nếu chưa có
            }
        }

        // Gán vai trò cho người dùng và lưu vào database
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    // Hàm kiểm tra và tạo vai trò nếu chưa tồn tại
    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName); // Set tên vai trò
        return roleRepository.save(role); // Lưu vai trò vào database
    }

    @Override
    public User findByEmail(String email) {
        // Tìm kiếm người dùng theo email
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        // Lấy tất cả người dùng từ database và chuyển đổi sang UserDto
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    // Hàm chuyển đổi từ Entity User sang DTO UserDto
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        // Tách họ và tên từ chuỗi full name
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]); // Họ
        userDto.setLastName(name[1]); // Tên
        userDto.setEmail(user.getEmail()); // Email
        return userDto;
    }
}
