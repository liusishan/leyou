package com.leyou.order.web;

import com.leyou.order.pojo.Address;
import com.leyou.order.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> queryAddressList() {
        return ResponseEntity.ok(addressService.queryAddressList());
    }


}
