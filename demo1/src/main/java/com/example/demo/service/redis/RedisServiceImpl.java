package com.example.demo.service.redis;


import com.example.demo.exception.DatabaseCrashException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import com.example.demo.service.redis.blackList.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired
    BlackListService blackListService;



//
//    @Override
//    public Blacklist numberIsPresent(String phoneNumber)
//    {
//        System.out.println("i am from db "+phoneNumber);
//        return blacklistRepository.findById(phoneNumber).get();
//    }

    @Override
    public void addNumberToBlacklist(Blacklist blackList) {

        System.out.println("insert in db "+blackList.getPhoneNumber());
        redisTemplate.opsForSet().add(KEY,blackList.getPhoneNumber());
        blackListService.saveInBlacklist(blackList);

    }

    @Override
    public boolean checkIfExist(String phoneNumber){

        System.out.println("checking in cache");
        if(redisTemplate.opsForSet().isMember(KEY,phoneNumber))
            return true;
        System.out.println("Checking in db");
        //return blacklistRepository.existsById(phoneNumber);
        if(blackListService.ifExistsBlacklist(phoneNumber))
        {
            redisTemplate.opsForSet().add(KEY,phoneNumber);
            return true;
        }
        else return false;
    }

    @Override
    public void removeNumberFromBlacklist(String phoneNumber) {
        System.out.println("delete from db");

        try {
            if(checkIfExist(phoneNumber)) {
                redisTemplate.opsForSet().remove(KEY, phoneNumber);
                blackListService.deleteFromBlacklist(phoneNumber);
            }
            else
                throw new NotFoundException("Number not blacklisted");
        } catch (Exception ex) {
            throw new DatabaseCrashException("Service Crashed");

        }
    }

    @Override
 public Page<String> findAllBlackListedNumber(Optional<Integer>page) {
        // not an nd point
        // checking for cache
        // as to be implemented from the db
        return blacklistRepository.findAllBlacklistedNumber(PageRequest.of(page.orElse(0),2));
        //return redisTemplate.opsForSet().members(KEY);
    }



}
