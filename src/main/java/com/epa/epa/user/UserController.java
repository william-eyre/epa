package com.epa.epa.user;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.epa.epa.authentication.RequiresNoPermission;
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
  String createNewUser(@RequestBody User userDetails) {
   return userService.createUser(userDetails);
  }

  @GetMapping(path = "{employeeId}")
  @RequiresNoPermission
  public @ResponseBody
  ResponseEntity<Object> verifyUserExists(@PathVariable String employeeId) {

    return userService.verifyUser(employeeId) ?
        new ResponseEntity<>(OK) :
        new ResponseEntity<>(NOT_FOUND);
  }

}
