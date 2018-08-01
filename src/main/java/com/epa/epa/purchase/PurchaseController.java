package com.epa.epa.purchase;

import com.epa.epa.authentication.RequiresPermission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("purchase/")
public class PurchaseController {

  PurchaseService purchaseService;

  @PatchMapping(path = "{employeeId}")
  @RequiresPermission
  public @ResponseBody
  void purchaseItem(@RequestBody PurchaseTotal purchaseTotal, @PathVariable String employeeId) {
    purchaseService.purchaseItem(employeeId, purchaseTotal);
  }
}

