package com.sparta.todoparty.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;      // spring security에서 지원

    private final UserRepository userRepository;
    public void signup(UserRequestDto userRequestDto){
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        System.out.println(username);
        System.out.println(password);
        if(userRepository.findByUsername(username).isPresent()){    // DB에 요청들어온 username이 isPresent(존재하다)한다면
                                                                    // optional<>이라 isPresent 사용가능
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        // 없으면 유저 만들고 repository에 저장
        User user = new User(username, password);
        userRepository.save(user);
    }


    public void login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }
}
