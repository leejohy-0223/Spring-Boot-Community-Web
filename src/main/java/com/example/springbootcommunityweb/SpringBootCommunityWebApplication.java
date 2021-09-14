package com.example.springbootcommunityweb;

import com.example.springbootcommunityweb.domain.Board;
import com.example.springbootcommunityweb.domain.User;
import com.example.springbootcommunityweb.domain.enums.BoardType;
import com.example.springbootcommunityweb.repository.BoardRepository;
import com.example.springbootcommunityweb.repository.UserRepository;
import com.example.springbootcommunityweb.resolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringBootCommunityWebApplication implements WebMvcConfigurer {

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCommunityWebApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Bean // 스프링은 빈으로 생성된 메서드에 파라미터로 DI 시키는 메커니즘이 존재한다. 생성자를 통해 의존성을 주입시키는 방법과 유사하다. 이를 이용해서 CommandLineRunner를 빈으로 등록 후, 두 repo를 주입 받는다.
    public CommandLineRunner runner(final UserRepository userRepository,
                                    final BoardRepository boardRepository) throws Exception {

        return (args -> {
            // 메서드 내부에 실행이 필요한 코드를 작성한다. User 객체를 빌더 패턴을 사용해서 생성한 후, 저장한다.
            final User user = userRepository.save(User.builder()
                    .name("havi")
                    .password("test")
                    .email("havi@gmain.com")
                    .createDate(LocalDateTime.now())
                    .build());

            // 페이징 처리 테스트를 위해 빌더 패턴을 이용한다. IntStream의 rangeClosed를 사용해서 index 순서대로 Board 객체 200개를 생성해서 저장한다.
            IntStream.rangeClosed(1, 200).forEach(index ->
                    boardRepository.save(Board.builder()
                            .title("게시글" + index)
                            .subTitle("순서" + index)
                            .content("콘텐츠")
                            .boardType(BoardType.free)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .user(user)
                            .build()
                    ));
        });
    }
}
