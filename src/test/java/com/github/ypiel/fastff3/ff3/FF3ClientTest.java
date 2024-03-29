package com.github.ypiel.fastff3.ff3;

import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.service.FF3Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FF3ClientTest {

    private FF3Client client;

    @BeforeEach
    public void beforeEach(){
        client = new FF3Client();
    }

    @Test
    public void testGetAccountsByType(){
        List<Account> accountsByType = client.getAccountsByType(FF3Client.AccountType.ASSET_ACCOUNT);
        Assertions.assertEquals(15, accountsByType.size());
    }

}