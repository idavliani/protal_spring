package com.protal.myApp.Controller;

import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.protal.myApp.Utils.CompressUtils.compressBytes;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.findById(userId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        System.out.println("username is " + username );
        User user = userService.findByUsername(username);
        System.out.println("user is " + user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        System.out.println(" email is " + email);
        User user = userService.findByEmail(email);
        System.out.println("user is " + user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userService.findAll();
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @GetMapping(path = "/getUser/{username}/{email}")
    public ResponseEntity<List<User>> getUserByUsernameOrEmail(@PathVariable(name = "username", required = false) String username,
                                                               @PathVariable(name = "email", required = false) String email) {
        System.out.println("username is " + username + " email is " + email);
        List<User> userList = userService.findByUsernameOrEmail(username, email);
        System.out.println("user is " + userList.size());
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping(value = "/updateUserData")
    public ResponseEntity updateUserValues(@RequestParam(value = "imageFile", required = false) MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("telephone") Long telephone,
                                           @RequestParam("password") String password,
                                           @RequestParam("id") Integer id) throws IOException {
        User user = userService.findById(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setTelephone(telephone);
        user.setPassword(password);

        if (user != null) {
            byte[] image = null;
            if (file != null) {
                image = compressBytes(file.getBytes());
                user.setImage(image);
            } else {
                image = compressBytes(user.getImage());
                user.setImage(image);
            }
            userService.saveUser(user);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}
