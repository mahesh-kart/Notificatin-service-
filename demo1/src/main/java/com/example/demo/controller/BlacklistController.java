
package com.example.demo.controller;

import com.example.demo.exception.InvalidRequestException;
import com.example.demo.model.BlackListInput;
import com.example.demo.model.Blacklist;
import com.example.demo.response.DataResponse;
import com.example.demo.response.ErrorResponse;
import com.example.demo.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/blacklist")
public class BlacklistController {

    @Autowired
    RedisService redisService;


    @PostMapping
    public ResponseEntity<Object> addNumberToBlacklist(@RequestBody BlackListInput phoneNumbers)
    {

try{
            Blacklist blackList = new Blacklist();
            for (String phoneNumber : phoneNumbers.getPhoneNumber()) {
                if (!phoneNumber.trim().isEmpty()) {
                    blackList.setPhoneNumber(phoneNumber);
                    System.out.println(phoneNumber);
                    redisService.addNumberToBlacklist(blackList);

                } else {

                    throw new InvalidRequestException("Phone no can't be empty");

                }
            }

            DataResponse dataResponse=new DataResponse();
    dataResponse.setComments("Successfully blacklisted");


            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }
        catch(Exception ex)
        {

            return new ResponseEntity<>(new ErrorResponse(ex.getLocalizedMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);

        }

    }


//
//    @GetMapping("/{phoneNumber}")
//    public Blacklist isPresent(@PathVariable String phoneNumber)
//    {
//        return redisService.numberIsPresent(phoneNumber);
//    }


    @GetMapping("/")
    public Page<String> findAllBlacklistedNumber(@RequestParam Optional<Integer> page)
    {
       return redisService.findAllBlackListedNumber(page);


    }

    @DeleteMapping("/{phoneNumber}")
    public void removeNumberFromBlacklist(@PathVariable String phoneNumber)
    {

        redisService.removeNumberFromBlacklist(phoneNumber);
    }


}
