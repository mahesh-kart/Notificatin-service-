package com.example.demo.service.redis.blackList;

import com.example.demo.model.Blacklist;
import com.example.demo.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    BlacklistRepository blacklistRepository;
    @Override
    public void saveInBlacklist(Blacklist blacklist) {
        blacklistRepository.save(blacklist);

    }

    @Override
    public boolean ifExistsBlacklist(String phoneNumber) {
        return blacklistRepository.existsById(phoneNumber);
    }

    @Override
    public void deleteFromBlacklist(String phoneNumber) {
        blacklistRepository.deleteById(phoneNumber);
    }
}
