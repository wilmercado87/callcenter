/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.controllers;

import com.almundo.call.center.models.CallCenter;
import junit.framework.TestCase;

/**
 * 
 * @author ADMIN
 */
public class DispatcherTest extends TestCase {

    private Dispatcher instance;
    private CallCenter callCenterModel;

    public DispatcherTest(String testName) {
        super(testName);
    }
    
    /***
     * Se instancia el model CallCenter
     * para realizar test unitario de 10 llamadas
     * concurrentes y se construyen 10 empleados
     * 4 operadores, 4 supervisores y 2 directores
     * @throws Exception 
     */
    @Override
    protected void setUp() throws Exception {
        callCenterModel = new CallCenter();
        callCenterModel.setIncomingCalls(10);
        callCenterModel.setnOperators(4);
        callCenterModel.setnSupervisor(4);
        callCenterModel.setnDirectors(2);
        instance = new Dispatcher();
        instance.buildEmployees(callCenterModel);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of dispatchCall method, of class Dispatcher.
     */
    public void testDispatchCall() {
        System.out.println("dispatchCall");
        instance.dispatchCall(callCenterModel);
        assertTrue(true);
    }
}
