package com.epa.epa.topup;

import com.epa.epa.authentication.RequiresPermission;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("topup/")
public class TopUpController {

  private final TopUpService topUpService;

  @PatchMapping(path = "{employeeId}")
  @RequiresPermission
  public @ResponseBody
  ResponseEntity<HttpStatus> topUpUser(@RequestBody TopUpAmount topUpAmount,
      @PathVariable String employeeId) {
    return topUpService.topUpUser(employeeId, topUpAmount)
        ? new ResponseEntity<>(HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}

