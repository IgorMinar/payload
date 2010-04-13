package com.tapdancingmonk.payload;


public class DefaultIpnMessageHandler implements IpnMessageHandler {

    public boolean processIpnMessage(String message) {
        
        // TODO implement back office
        //  1. check receiver address is correct
        //  2. send message back to paypal
        //  3. receive VERIFIED or INVALID and return true or false
        
        return false;
        
    }

}
