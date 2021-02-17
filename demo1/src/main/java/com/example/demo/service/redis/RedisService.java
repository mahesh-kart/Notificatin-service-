package com.example.demo.service.redis;

import com.example.demo.model.Blacklist;
import org.springframework.data.domain.Page;
import sun.tools.tree.ShiftRightExpression;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RedisService {

    public void addNumberToBlacklist(Blacklist blackList);

    public void removeNumberFromBlacklist(String phoneNumber);

    public Page<String> findAllBlackListedNumber(Optional<Integer> page);

//    public Blacklist numberIsPresent(String phoneNumber);

    public boolean checkIfExist(String phoneNumber);

}
