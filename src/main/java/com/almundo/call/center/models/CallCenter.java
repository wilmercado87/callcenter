/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.models;

/**
 *
 * @author ADMIN
 */
public class CallCenter {

    private int incomingCalls;
    private int nOperators;
    private int nSupervisor;
    private int nDirectors;

    public int getIncomingCalls() {
        return incomingCalls;
    }

    public void setIncomingCalls(int incomingCalls) {
        this.incomingCalls = incomingCalls;
    }

    public int getnOperators() {
        return nOperators;
    }

    public void setnOperators(int nOperators) {
        this.nOperators = nOperators;
    }

    public int getnSupervisor() {
        return nSupervisor;
    }

    public void setnSupervisor(int nSupervisor) {
        this.nSupervisor = nSupervisor;
    }

    public int getnDirectors() {
        return nDirectors;
    }

    public void setnDirectors(int nDirectors) {
        this.nDirectors = nDirectors;
    }

}
