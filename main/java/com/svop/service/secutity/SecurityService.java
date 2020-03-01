package com.svop.service.secutity;

/**
 * Сервис для защиты
 */
public interface SecurityService {
    String findLoggedInUsername(); //Нужно для того, что бы поприветствовать пользователя
    void autoLogin(String username,String password);// Тут происходит авторизация, получение деталей пользователя из UserDetails
}
