package vn.com.assistant.fqcbackend.service.imps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.entity.Token;
import vn.com.assistant.fqcbackend.repository.TokenRepository;
import vn.com.assistant.fqcbackend.service.TokenService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository _tokenRepo;

    @Override
    public void save(Token token) {
        _tokenRepo.save(token);
    }

    @Override
    public List<Token> fetch() {
        return _tokenRepo.findAll();
    }
}
