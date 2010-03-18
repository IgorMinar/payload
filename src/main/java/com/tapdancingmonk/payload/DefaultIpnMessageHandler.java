package com.tapdancingmonk.payload;

import com.tapdancingmonk.payload.model.Transaction;

public class DefaultIpnMessageHandler implements IpnMessageHandler {

    public void processIpnMessage(String message, Transaction txn) {
        
        // TODO implement back office
        //  1. send message back to paypal
        //  2. receive VERIFIED or INVALID and set tx state
        
    }

}
