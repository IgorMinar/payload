package com.tapdancingmonk.payload;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.apphosting.api.DeadlineExceededException;
import com.google.inject.Inject;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.dao.TransactionDao;
import com.tapdancingmonk.payload.model.Product;
import com.tapdancingmonk.payload.model.Transaction;

@Path("/ipn/{product_id}")
public class IpnListenerResource {
    
    private final ProductDao pDao;
    private final TransactionDao tDao;
    private final IpnMessageHandler ipnHandler;
    
    @Inject
    public IpnListenerResource(
            ProductDao pDao,
            TransactionDao tDao,
            IpnMessageHandler ipnHandler) {
        
        this.pDao = pDao;
        this.tDao = tDao;
        this.ipnHandler = ipnHandler;
        
    }

    @POST
    public void receiveIpnMessage(
            @PathParam("product_id") String productId,
            @FormParam("receiver_email") String receiverEmail,
            @FormParam("txn_id") String transactionId,
            @FormParam("txn_type") String transactionType,
            @FormParam("payer_email") String payerEmail,
            @FormParam("first_name") String firstName,
            @FormParam("last_name") String lastName,
            String messageBody) {
        
        // TODO validation:
        //    payer_email should be validated
        //    receiver_email should be validated and verified
        //    transaction_type should be checked?
        
        Product product = pDao.find(productId);
        Transaction txn = new Transaction(
                firstName, lastName, payerEmail, transactionId, product);
        
        try {
            ipnHandler.processIpnMessage(messageBody, txn);
        } catch (DeadlineExceededException ex) {
            // TODO anything?
        } finally {
            tDao.save(txn);
        }
                
    }

}
