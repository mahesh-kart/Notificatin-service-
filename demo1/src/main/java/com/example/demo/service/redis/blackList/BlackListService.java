package com.example.demo.service.redis.blackList;

import com.example.demo.model.Blacklist;

public interface BlackListService {

    public void saveInBlacklist(Blacklist blacklist);

    public boolean ifExistsBlacklist(String id);

    public void deleteFromBlacklist(String id);





}
