package edu.js.project.service.impl;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BlacklistToken {

    private Set<String> blackListToken = ConcurrentHashMap.newKeySet();

    public void addToBlacklist(String token){

        blackListToken.add(token);
        System.out.println(blackListToken);

    }

    public boolean isBlacklisted(String token){

        System.out.println(blackListToken);
        return blackListToken.contains(token);

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BlacklistToken{");
        sb.append("blackListToken=").append(blackListToken);
        sb.append('}');
        return sb.toString();
    }
}
