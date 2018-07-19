/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.threads;

import com.almundo.call.center.models.Call;
import com.almundo.call.center.models.Employee;
import com.almundo.call.center.models.Estado;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author ADMIN
 */
public class ProcessCallRunnable implements Runnable {

    private static final Logger LOG = Logger.getLogger(ProcessCallRunnable.class);

    private Call call;
    private long initialTime;
    private List<Employee> employees;

    public ProcessCallRunnable(Call call, long initialTime, List<Employee> employees) {
        this.call = call;
        this.initialTime = initialTime;
        this.employees = employees;
    }

    @Override
    public void run() {
        LOG.info("start process call:[" + Thread.currentThread().getName() + "]");
        try {
            /**
             * Se simula dormir el hilo con un tiempo aleatorio entre 5 y 10
             * segundos, que se establece la duracion de la llamada
             */
            LOG.info("Employee, name:[" + this.call.getEmployee().getName()
                    + "] type:[" + this.call.getEmployee().getEmployeeType().toString() + "]"
                    + ", timeCalling:[" + this.call.getSecondsAnswerCall() + " Seg.]");
            Thread.sleep(this.call.getSecondsAnswerCall() * 1000);
            //Se actualiza la disponiblidad del empleado en la lista de empleados
            this.updateEmployeeAvailable(this.call.getEmployee());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOG.log(Level.ERROR, "InterruptedException", ex);
        }

        LOG.info("End process call:[" + Thread.currentThread().getName() + "],"
                + " Time Seconds:[ " + (System.currentTimeMillis() - this.initialTime) / 1000 + " seg]");
    }

    private void updateEmployeeAvailable(Employee employeeUpdate) {
        for (Employee employee : employees) {
            if (employee.getName().equals(employeeUpdate.getName())) {
                employee.setEstado(Estado.DISPONIBLE);
                break;
            }
        }
    }

}
