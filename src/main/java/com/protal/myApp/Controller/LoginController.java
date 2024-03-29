package com.protal.myApp.Controller;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.UserService;
import com.protal.myApp.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.protal.myApp.Utils.CompressUtils.compressBytes;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.OPTIONS)
    public ResponseEntity authenticate() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity authenticateEmail(@RequestBody String email) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.OPTIONS)
    public ResponseEntity handleRegistration() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestParam(value = "imageFile", required = false) MultipartFile file, @RequestParam("name") String name,
                                   @RequestParam("lastName") String lastName, @RequestParam("telephone") Long telephone,
                                   @RequestParam("email") String email, @RequestParam("username") String username,
                                   @RequestParam("password") String password) throws IOException {
       boolean succesfulRegistration = userService.registerNewUser(file,name,lastName,telephone,email,username,password);
       if(succesfulRegistration){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
