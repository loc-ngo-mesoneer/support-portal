package io.locngo.support.portal.service;

import io.locngo.support.portal.controller.LoginRequest;
import io.locngo.support.portal.controller.RegisterRequest;
import io.locngo.support.portal.domain.User;

public interface IUserService {
    User register(RegisterRequest request);
    
    String login(LoginRequest request);
}
