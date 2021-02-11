package com.example.demo.service.redis;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    BlacklistRepository blacklistRepository;

    @Autowired
    private RedisTemplate redisTemplate;



    private static final String KEY="BLACKLIST";



//
//    @Override
//    public Blacklist numberIsPresent(String phoneNumber)
//    {
//        System.out.println("i am from db "+phoneNumber);
//        return blacklistRepository.findById(phoneNumber).get();
//    }

    @Override
    public void addNumberToBlacklist(Blacklist blackList,String phoneNumber) {

        System.out.println("insert in db "+phoneNumber);
        redisTemplate.opsForSet().add(KEY,phoneNumber);
        blacklistRepository.save(blackList);

    }

    @Override
    public void removeNumberFromBlacklist(String phoneNumber) {
        System.out.println("delete from db");
        try {
            redisTemplate.opsForSet().remove(KEY, phoneNumber);
            blacklistRepository.deleteById(phoneNumber);
        } catch (Exception ex) {
            throw new NotFoundException("Number not blacklisted");
        }
    }

    @Override
 public List<String> findAllBlackListedNumber() {
        // not an nd point
        // checking for cache
        // as to be implemented from the db
        return blacklistRepository.findAllBlacklistedNumber();
        //return redisTemplate.opsForSet().members(KEY);
    }


    @Override
 public boolean checkIfExist(String phoneNumber){

        System.out.println("checking in cache");
        if(redisTemplate.opsForSet().isMember(KEY,phoneNumber))
            return true;
        System.out.println("Checking in db");
        //return blacklistRepository.existsById(phoneNumber);
        if(blacklistRepository.existsById(phoneNumber))
        {
            redisTemplate.opsForSet().add(KEY,phoneNumber);
            return true;
        }
        else return false;
    }


}
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Set;
//@Service
//public class RedisServiceImpl implements  RedisService{
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private static final String KEY = "BLACKLIST";
//
//    @Override
//    public void addNumberToBlacklist(String phoneNo) {
//        redisTemplate.opsForSet().add(KEY,phoneNo);
//    }
//
//    @Override
//    public void removeNumberFromBlacklist(String phoneNo) {
//        redisTemplate.opsForSet().remove(KEY,phoneNo);
//    }
//
//    @Override
//    public Set showAllPhoneNo() {
//        return redisTemplate.opsForSet().members(KEY);
//    }
//
//    @Override
//    public boolean checkIfExist(String phoneNo) {
//        return redisTemplate.opsForSet().isMember(KEY,phoneNo);
//    }
//
//
//}
