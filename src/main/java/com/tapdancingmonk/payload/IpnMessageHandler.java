package com.tapdancingmonk.payload;


public interface IpnMessageHandler {

    boolean processIpnMessage(String message);
    
}
