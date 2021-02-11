
package com.example.demo.controller;

import com.example.demo.exception.DatabaseCrashException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.BlackListInput;
import com.example.demo.model.Blacklist;
import com.example.demo.response.data;
import com.example.demo.response.error;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/v1/blacklist")
public class BlacklistController {

    @Autowired
    RedisService redisService;


    @PostMapping
    public ResponseEntity<Object> addNumberToBlacklist(@RequestBody BlackListInput phone_numbers)
    {

try{
            Blacklist blackList = new Blacklist();
            for (String phoneNumber : phone_numbers.getPhone_numbers()) {
                if (!phoneNumber.trim().isEmpty()) {
                    blackList.setPhoneNumber(phoneNumber);
                    System.out.println(phoneNumber);
                    redisService.addNumberToBlacklist(blackList, phoneNumber);

                } else {
//                    throw new DatabaseCrashException();

                    // yet to be handled case
                }
            }

            data Data=new data();
            Data.setComments("Successfully blacklisted");


            return new ResponseEntity<>(Data, HttpStatus.OK);
        }
        catch(Exception ex)
        {

            return new ResponseEntity<>(new error(ex.getLocalizedMessage(), "mc bc"), HttpStatus.BAD_REQUEST);

        }

    }


//
//    @GetMapping("/{phoneNumber}")
//    public Blacklist isPresent(@PathVariable String phoneNumber)
//    {
//        return redisService.numberIsPresent(phoneNumber);
//    }


    @GetMapping("/")
    public List<String> findAllBlacklistedNumber()
    {
       return redisService.findAllBlackListedNumber();


    }

    @DeleteMapping("/{phoneNumber}")
    public void removeNumberFromBlacklist(@PathVariable String phoneNumber)
    {

        redisService.removeNumberFromBlacklist(phoneNumber);
    }


}
//
//import com.example.demo.service.redis.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/v1/blacklist")
//public class BlacklistController {
//@Autowired
//    RedisService redisService;
//
//    @PostMapping("/")
//    public void addNumberToBlacklist(@RequestBody String phoneNo){
//        List<String> elephantList = Arrays.asList(phoneNo.split(","));
//        int i=0;
//        while(i<elephantList.size()) {
//            redisService.addNumberToBlacklist(elephantList.get(i));
//            i++;
//        }
////        redisService.addNumberToBlacklist(phoneNo);
//    }
//    @GetMapping("/all")
//    public Set showAllPhoneNo(){
//        return redisService.showAllPhoneNo();
//    }
//    @DeleteMapping("/{phoneNo}")
//    public void removeNumberFromBlacklist(@PathVariable String phoneNo){
//        redisService.removeNumberFromBlacklist(phoneNo);
//    }
//
//}
