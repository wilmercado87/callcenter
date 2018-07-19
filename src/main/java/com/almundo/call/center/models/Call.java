/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.models;

import com.almundo.call.center.utilities.Utils;

/**
 *
 * @author ADMIN
 */
public class Call {
    private int secondsAnswerCall = Utils.getNumberRandom();
    private Employee employee;

    public int getSecondsAnswerCall() {
        return secondsAnswerCall;
    }

    public void setSecondsAnswerCall(int secondsAnswerCall) {
        this.secondsAnswerCall = secondsAnswerCall;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
