package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.RefreshTokenRequest;
import by.bsuir.Dormitory.dto.request.SignupRequest;
import by.bsuir.Dormitory.dto.request.UserLoginRequest;
import by.bsuir.Dormitory.dto.response.AuthenticationResponse;
import by.bsuir.Dormitory.exception.AuthException;
import by.bsuir.Dormitory.exception.MyJwtException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.mapper.UserMapper;
import by.bsuir.Dormitory.model.NotificationEmail;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.model.Verification;
import by.bsuir.Dormitory.repository.UserRepository;
import by.bsuir.Dormitory.repository.VerificationRepository;
import by.bsuir.Dormitory.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;

    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent())
            throw new AuthException("User with this email already exists");
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent())
            throw new AuthException("User with this username already exists");

        User user = userMapper.map(signupRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendSignUpEmail(user);
    }

    private String generateVerificationToken(User user) {
        String verificationToken = UUID.randomUUID().toString();
        Verification verification = new Verification();
        verification.setToken(verificationToken);
        verification.setUser(user);

        verificationRepository.save(verification);
        return verificationToken;
    }

    @Transactional
    public void verifyAccount(String token) {
        Optional<Verification> verification = verificationRepository.findByToken(token);
        verification.orElseThrow(() -> new MyJwtException("Invalid Token"));
        fetchUserAndEnable(verification.get());
    }

    private void fetchUserAndEnable(Verification verification) {
        String username = verification.getUser().getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with name: " + username));
        user.setVerified(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(UserLoginRequest userLoginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLoginRequest.getUsername(),
                userLoginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        Optional<User> user = userRepository.findByUsername(userLoginRequest.getUsername());
        String role = user.isPresent() ? user.get().getRole().toString() : "GUEST";
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(userLoginRequest.getUsername())
                .role(role)
                .build();
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException(principal.getUsername()));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    private void sendSignUpEmail(User user) {
        String token = generateVerificationToken(user);
        NotificationEmail notificationEmail = new NotificationEmail("Пожалуйста, активируйте свой аккаунт",
                user.getEmail(),
                "Спасибо, что выбрали нашу компанию, " +
                        "пожалуйста, перейдите по следующей ссылке для активации своего аккаунт: " +
                        "http://localhost:8080/auth/verification/" + token);
        mailService.sendMail(notificationEmail);
    }
}
