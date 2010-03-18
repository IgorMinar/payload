package com.tapdancingmonk.payload;

import com.tapdancingmonk.payload.model.Transaction;

public interface IpnMessageHandler {

    void processIpnMessage(String message, Transaction txn);
    
}
