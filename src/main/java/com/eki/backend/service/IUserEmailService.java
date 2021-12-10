package com.eki.backend.service;

public interface IUserEmailService {

     void  sendUserActivateEmail(String email, String userName, String backendUrl);

     void sendProductBuyEmail(String email,String content);
}
