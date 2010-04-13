package com.tapdancingmonk.payload;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.apphosting.api.DeadlineExceededException;
import com.google.inject.Inject;
import com.tapdancingmonk.payload.dao.EntityNotFoundException;
import com.tapdancingmonk.payload.dao.ProductDao;
import com.tapdancingmonk.payload.dao.TransactionDao;
import com.tapdancingmonk.payload.model.Product;
import com.tapdancingmonk.payload.model.Transaction;
import com.tapdancingmonk.payload.model.Transaction.Status;

@Path("/ipn/{product_id}")
public class IpnListenerResource {
    
    private final static transient Logger logger = 
            Logger.getLogger(IpnListenerResource.class.getName());
    
    private final ProductDao pDao;
    private final TransactionDao tDao;
    private final IpnMessageHandler ipnHandler;
    private final PayloadProperties properties;
    
    @Inject
    public IpnListenerResource(
            ProductDao pDao,
            TransactionDao tDao,
            IpnMessageHandler ipnHandler,
            PayloadProperties properties) {
        
        this.pDao = pDao;
        this.tDao = tDao;
        this.ipnHandler = ipnHandler;
        this.properties = properties;
        
    }

    @POST
    public void receiveIpnMessage(
            @PathParam("product_id") String productId,
            @FormParam("receiver_email") String receiverEmail,
            @FormParam("txn_id") String transactionId,
            @FormParam("txn_type") String transactionType,
            @FormParam("payment_status") String paymentStatus,
            @FormParam("payer_email") String payerEmail,
            @FormParam("first_name") String firstName,
            @FormParam("last_name") String lastName,
            String messageBody) {
        
        // TODO validation:
        //    payer_email should be validated
        //    receiver_email should be validated and verified
        //    paymentStatus should be "Completed"
        //    amount should == product price 
        //    transaction_type should be checked?
        
        Product product = pDao.find(productId);
        Transaction txn = getTransaction(
                transactionId, firstName, lastName, payerEmail, product);        
        
        boolean txnVerified = false;
        
        try {
            txnVerified = ipnHandler.processIpnMessage(messageBody);
                        
            if (txn.getStatus() == Status.FULFILLED) {
                logger.log(Level.WARNING, String.format(
                        "TX %s has already been fulfilled - duplicate transaction?", transactionId));
            } else {
                txn.setStatus(txnVerified ? Status.VERIFIED : Status.INVALID);
            }
        } catch (DeadlineExceededException ex) {
            // TODO anything? Perhaps a special status for INCOMPLETE?
        } finally {
            tDao.save(txn);
        }
            
        // note that paypal indicates we should do this validation *after* verifying the transaction
        if (! receiverEmail.equals(properties.getReceiverEmail())) {
            logger.log(Level.WARNING, String.format(
                    "Bad receiver email: %s. Full message: %s", receiverEmail, messageBody));
        }
        
        if (! "Completed".equals(paymentStatus)) {
            logger.log(Level.WARNING, String.format(
                    "Unexpected payment status.  Expected 'Completed', received '%s'", transactionType));
        }        
        
    }
    
    private Transaction getTransaction(
            String txId, 
            String firstName, 
            String lastName, 
            String payerEmail, 
            Product product) {
        
        Transaction txn = null;
        
        try {
            txn = tDao.findByTxId(txId); 
        } catch (EntityNotFoundException ex) {
            txn = new Transaction(firstName, lastName, payerEmail, txId, product);
        }
        
        return txn;
    }

}
