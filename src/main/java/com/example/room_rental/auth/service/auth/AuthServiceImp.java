package com.example.room_rental.auth.service.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.room_rental.utils.cookie.model.CookieRequest;
import com.example.room_rental.utils.cookie.service.CookieService;
import com.example.room_rental.utils.email.service.EmailService;
import com.example.room_rental.utils.otp.OTPGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.room_rental.auth.constain.ERole;
import com.example.room_rental.auth.dto.request.SignInForm;
import com.example.room_rental.auth.dto.request.SignUpForm;
import com.example.room_rental.auth.dto.response.JwtResponse;
import com.example.room_rental.auth.model.Role;
import com.example.room_rental.auth.repository.RoleRepository;
import com.example.room_rental.auth.service.jwts.jwts.JwtProvider;
import com.example.room_rental.auth.service.jwts.userdetail.UserPrinciple;
import com.example.room_rental.user.model.User;
import com.example.room_rental.user.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPGenerator otpGenerator;
    @Autowired
    private CookieService cookieService;

    // @Autowired
    // private JwtAuthTokenFilter jwtAuthTokenFilter;
    @Override
    public ResponseEntity<?> signIn(@Valid SignInForm signInForm, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
            CookieRequest cookieRequest = new CookieRequest();
            cookieRequest.setName("accessToken");
            cookieRequest.setValue(jwt);
            cookieService.setCookie(response, cookieRequest);
            return ResponseEntity
                    .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(), userDetails.getUsername(),
                            userDetails.getEmail(), userDetails.getAvatar(), userDetails.getAuthorities()));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Override
    public ResponseEntity<?> signUp(@Valid SignUpForm signUpForm, HttpServletResponse response) {
        try {
            if (userRepository.existsByEmail(signUpForm.getEmail()) ||
                    userRepository.existsByUsername(signUpForm.getUsername())) {
                return new ResponseEntity<>("Email or username already exists", HttpStatus.CONFLICT);
            }
            User user = new User();
            BeanUtils.copyProperties(signUpForm, user);
            user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
            Set<Role> roleSet = new HashSet<>();
            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roleSet.add(userRole);
            user.setRoles(roleSet);
            String otp = otpGenerator.generateOTP();
            CookieRequest cookieRequest = new CookieRequest();
            cookieRequest.setName(otp);
            cookieRequest.setExpried(60 * 5);
            ObjectMapper objectMapper = new ObjectMapper();
            cookieRequest.setValue(objectMapper.writeValueAsString(user));
            cookieService.setCookie(response, cookieRequest);

            System.out.println(otp);
            emailService.sendSimpleEmail(signUpForm.getEmail(), "", otp);
            return new ResponseEntity<>("Oke Oke", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failure setting cookie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> verify(String otp, HttpServletRequest request, HttpServletResponse response) {
        try {
            ResponseEntity<?> cookieResponse = cookieService.getCookie(request, otp);

            if (cookieResponse.getStatusCode() == HttpStatus.OK) {
                Object body = cookieResponse.getBody();
                if (body instanceof Object) {
                    String jsonString = (String) body;
                    ObjectMapper objectMapper = new ObjectMapper();
                    User user = objectMapper.readValue(jsonString, User.class);
                    userRepository.save(user);
                    cookieService.clearCookie(response, otp);
                    return new ResponseEntity<>("Register user success", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid user representation in the cookie",
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else if (cookieResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>("OTP cookie not found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("Failure retrieving cookie", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failure retrieving cookie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
