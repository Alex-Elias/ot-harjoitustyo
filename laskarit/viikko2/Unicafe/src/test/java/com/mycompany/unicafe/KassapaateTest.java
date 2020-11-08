package com.mycompany.unicafe;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    @Before
    public void setUp(){
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    @Test
    public void kassapaateStartsWithCorrectAmountOfMoney(){
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void kassapaateStartsWithCorrectAmountOfPurchases(){
        assertEquals(0,kassa.maukkaitaLounaitaMyyty()+kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void MoneyIncreasesCorrectlyWithSyoEdullisesti(){
        kassa.syoEdullisesti(240);
        assertEquals(100240,kassa.kassassaRahaa());
    }
    @Test
    public void returnsCorrectAmountOfMoneyWithSyoEdullisesti(){
        assertEquals(10,kassa.syoEdullisesti(250));
    }
    @Test
    public void syoEdullisestiIncreasesPurchaseAmountCorrectly(){
        kassa.syoEdullisesti(240);
        assertEquals(1,kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void moneyIncreasesCorrectlyWithSyoMaukkaasti(){
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    @Test
    public void retunsCorrectAmountOfMoneyWithSyoMaukkaasti(){
        assertEquals(10,kassa.syoMaukkaasti(410));
    }
    @Test
    public void syoMaukkaastiIncreasesPurchasesAmountCorrectly(){
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kassaRahaaDoesNotChangeWhenNotEnoughMoneySyoEdullisesti(){
        kassa.syoEdullisesti(20);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void returnsAllMoneyWhenNotEnoughMoneySyoEdullisesti(){
        assertEquals(10, kassa.syoEdullisesti(10));
    }
    @Test
    public void purchaseAmountDoesNotChangeWhenNotEnoughMoneySyoEdullisesti(){
        kassa.syoEdullisesti(10);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void kassaRahaaDoesNotChangeWhenNotEnoughMoneySyoMaukkaasti(){
        kassa.syoMaukkaasti(20);
        assertEquals(100000,kassa.kassassaRahaa());
    }
    @Test
    public void returnsAllMoneyWhenNotEnoughMoneySyoMaukkaasti(){
        assertEquals(30,kassa.syoMaukkaasti(30));
    }
    @Test
    public void purchaseAmountDoesNotChangeWhenNotEnoughMoneySyoMaukkaasti(){
        kassa.syoMaukkaasti(30);
        assertEquals(0,kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void returnsTrueWhenKorttiHasEnoughMoneySyoEdullisesti(){
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void returnsTrueWhenKorttiHasEnoughMoneySyoMaukkaasti(){
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    @Test
    public void purchaseAmountChangesWhenKorttiHasEnoughMoneySyoEdullisesti(){
        kassa.syoEdullisesti(kortti);
        assertEquals(1,kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void purchaseAmountChangesWhenKorttiHasEnoughMoneySyoMaukkaasti(){
        kassa.syoMaukkaasti(kortti);
        assertEquals(1,kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void returnsFalseWhenKorttiDoesNotHaveEnoughMoneySyoMaukkaasti(){
        kortti = new Maksukortti(100);
        assertFalse(kassa.syoMaukkaasti(kortti));
    } 
    @Test
    public void returnsFalseWhenKorttiDoesNotHaveEnoughMoneySyoEdullisesti(){
        kortti = new Maksukortti(100);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void purchaseAmountDoesNotChangeWhenKorttiDoesNotHaveEnoughMoneySyoEdullisesti(){
        kortti = new Maksukortti(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(0,kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void purchaseAmountDoesNotChangeWhenKorttiDoesNotHaveEnoughMoneySyoMaukkaasti(){
        kortti = new Maksukortti(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0,kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void korttiBalanceDoesNotChangeWhenNotEnoughMoneySyoEdullisesti(){
        kortti = new Maksukortti(100);
        kassa.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
    }
    @Test
    public void korttiBalanceDoesNotChangeWhenNotEnoughMoneySyoMaukkaasti(){
        kortti = new Maksukortti(100);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    @Test
    public void kassapaateMoneyDoesNotChangeWithKorttiSyoEdullisesti(){
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void kassapaateMoneyDoesNotChangeWithKorttiSyoMaukkaasti(){
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void lataaRahaaKortilleDepositsCorrectSum(){
        kassa.lataaRahaaKortille(kortti, 10);
        assertEquals(1010, kortti.saldo());
    }
    @Test
    public void lataaRahaaKortilleKassapaateSumChangesCorrectly(){
        kassa.lataaRahaaKortille(kortti, 10);
        assertEquals(100010, kassa.kassassaRahaa());
    }
    @Test
    public void lataaRahaaKortilleKassapaateSumDoesNotChangeWithNegativeValue(){
        kassa.lataaRahaaKortille(kortti, -10);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void lataaRahaaKortilleKorttiSumDoesNotChangeWithNegativeValue(){
        kassa.lataaRahaaKortille(kortti, -10);
        assertEquals(1000, kortti.saldo());
    }
    
}
