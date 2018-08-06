package com.epa.epa.user;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.epa.epa.authentication.RequiresNoPermission;
import com.epa.epa.authentication.RequiresPermission;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

  private final UserService userService;

  @PostMapping
  @RequiresNoPermission
  public @ResponseBody
  ResponseEntity<Object> createNewUser(@RequestBody User userDetails) {
   return userService.createUser(userDetails)
       ? new ResponseEntity<>(CREATED)
       : new ResponseEntity<>(BAD_REQUEST);
  }

  @GetMapping(path = "/{employeeId}")
  @RequiresPermission
  public @ResponseBody
  User verifyUserExists(@PathVariable String employeeId) {
    return userService.getUserInfo(employeeId);
  }
}
