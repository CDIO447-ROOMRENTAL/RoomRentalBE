package com.example.room_rental.utils.cookie.service;

import com.example.room_rental.utils.cookie.model.CookieRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class CookieServiceImpl implements CookieService {

    @Override
    public ResponseEntity<?> getCookie(HttpServletRequest request, String name) {
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        String decodedValue = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.toString());
                        return new ResponseEntity<>(decodedValue, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>("Không tìm thấy Cookie!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Đã xảy ra lỗi khi lấy Cookie!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> setCookie(HttpServletResponse response, CookieRequest cookieRequest) {
        try {
            if(cookieRequest.getExpried()==null){
                cookieRequest.setExpried(36000);
            }
            String encodedValue = URLEncoder.encode(cookieRequest.getValue(), StandardCharsets.UTF_8.toString());
            Cookie cookie = new Cookie(cookieRequest.getName(), encodedValue);
            cookie.setPath("/");
            cookie.setMaxAge(cookieRequest.getExpried());
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResponseEntity<>("Cookie được thiết lập thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Đã xảy ra lỗi khi thiết lập Cookie!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> clearCookie(HttpServletResponse response, String name) {
        try {
            String encodedValue = URLEncoder.encode("", StandardCharsets.UTF_8.toString());
            Cookie cookie = new Cookie(name, encodedValue);
            cookie.setPath("/");
            cookie.setMaxAge(360000);
            response.addCookie(cookie);
            return new ResponseEntity<>("Cookie được xoá thành công!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Đã xảy ra lỗi khi xoá Cookie!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}