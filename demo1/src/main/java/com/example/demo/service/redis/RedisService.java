package com.example.demo.service.redis;

import com.example.demo.model.Blacklist;
import sun.tools.tree.ShiftRightExpression;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RedisService {

    public void addNumberToBlacklist(Blacklist blackList,String phoneNumber);

    public void removeNumberFromBlacklist(String phoneNumber);

    public List<String>findAllBlackListedNumber();

//    public Blacklist numberIsPresent(String phoneNumber);

    public boolean checkIfExist(String phoneNumber);
}
