package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.entity.Token;

import java.util.List;

public interface TokenService {
    void save(Token token);
    List<Token> fetch();
}
