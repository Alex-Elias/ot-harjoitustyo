package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void startingBalanceCorrect(){
        assertEquals(10, kortti.saldo());
    }
    @Test
    public void canLoadCardCorrectly(){
        kortti.lataaRahaa(990);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    @Test
    public void canOtaRahaaWhenEnoughMoney(){
        kortti = new Maksukortti(1000);
        kortti.otaRahaa(450);
        assertEquals("saldo: 5.50", kortti.toString());
    }
    @Test
    public void balanceDoesNotChangeWhenNotEnoughMoney(){
        kortti.otaRahaa(300);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void otaRahaaReturnsTrueWhenEnoughMoney(){
        assertTrue(kortti.otaRahaa(2));
        
    }
    @Test
    public void otaRahaaReturnsFalseWhenNotEnoughMoney(){
        assertFalse(kortti.otaRahaa(15));
    }
}
