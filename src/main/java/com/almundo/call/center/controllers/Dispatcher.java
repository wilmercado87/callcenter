/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.controllers;

import com.almundo.call.center.models.Call;
import com.almundo.call.center.models.CallCenter;
import com.almundo.call.center.models.Employee;
import com.almundo.call.center.models.EmployeeType;
import com.almundo.call.center.models.Estado;
import com.almundo.call.center.threads.ProcessCallRunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Clase controladora que procesa las peticiones y respuesta de la vista
 * callcenter
 *
 * @author ADMIN
 */
@Controller
public class Dispatcher {

    private static final Logger LOG = Logger.getLogger(Dispatcher.class);
    private static final int N_THREADS = 10;
    private List<Employee> employees;
    private static final String PRE_EMP = "EmpN:";

    /**
     * Validador la vista callcenter
     */
    @Autowired
    @Qualifier("callCenterValidator")
    private Validator validator;

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * Recurso para cargar la vista callcenter
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/viewCallcenter", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("callcenter", "callCenterModel", new CallCenter());
    }

    /**
     * Recurso para procesar la peticion de la vista callcenter
     *
     * @param callCenterModel
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value = "/processDispatcher", method = RequestMethod.POST)
    public String processDispatcher(@ModelAttribute("callCenterModel") @Validated CallCenter callCenterModel,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            LOG.info("Datos del Call Center incorrectos");
            return "callcenter";
        }

        LOG.info("Build Employee the Call Center with, "
                + "Operators:[" + callCenterModel.getnOperators() + "], "
                + "Supervisors:[" + callCenterModel.getnSupervisor() + "], "
                + "Directors:[" + callCenterModel.getnDirectors() + "]");
        this.buildEmployees(callCenterModel);

        LOG.info("Invoke dispatchCall:" + callCenterModel.getIncomingCalls());
        this.dispatchCall(callCenterModel);

        return "callcenter";
    }

    /**
     * Metodo que contiene la logica para atender un maximo de 10 llamadas
     * concurrentes implementando pool de hilos multitareas.
     *
     * @param callCenterModel
     */
    protected void dispatchCall(CallCenter callCenterModel) {
        List<Call> calls = this.getListCallToProcess(callCenterModel);
        /**
         * clase Executors en el que en el método "newFixedThreadPool(10
         * thread)" donde se define el número de hilos concurrentes a ejecutar
         */
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
        long init = System.currentTimeMillis();
        
        breakAll:
        for (Call call : calls) {
            int nAttempts = 0;
            Employee employeeAvailable = this.getEmployeeAvailable();
            while (employeeAvailable == null) {
                LOG.info("En el momento no hay agentes disponible, espere 5 segundos");
                try {
                    //La llamada entra en proceso de espera por 5sec 
                    Thread.sleep(5 * 1000);
                    nAttempts++;
                    if ( nAttempts == 4 ){
                        LOG.info("La disponiblidad de empleados para atender las llamadas en nula");
                        break breakAll;
                    }
                    //Despierta y vuelve a consultar disponibilidad de empleados
                    employeeAvailable = this.getEmployeeAvailable();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    LOG.log(org.apache.log4j.Level.ERROR, "InterruptedException", ex);
                }
            }
            call.setEmployee(employeeAvailable);
            ProcessCallRunnable callRunnable = new ProcessCallRunnable(call, init, employees);
            executor.execute(callRunnable);
        }
        /**
         * el metodo "shutdown" nos permite apagar el executor una vez terminada
         * toda la tarea, en caso que no va a estar todo el rato
         * preguntando(haciendo polling)si hay más procesos que ejecutar;
         */
        executor.shutdown();
        /**
         * bucle "while" para preguntar constantemente si ha concluido la
         * ejecución del executor y no seguir la secuencia del metodo hasta ver
         * finalizado
         */
        while (!executor.isTerminated()) {
            // Se espera a que terminen de ejecutarse todo el proceso de llamadas
        }

        // Instante final del procesamiento
        long end = System.currentTimeMillis();
        LOG.info("Tiempo total de procesamiento: " + (end - init) / 1000 + " Segundos");
    }

    /**
     * Retorna Lista de objetos Call con un tamaño del numero de llamadas
     * totales atender
     *
     * @param callCenterModel
     * @return
     */
    private List<Call> getListCallToProcess(CallCenter callCenterModel) {
        List<Call> calls = new ArrayList<>();
        for (int i = 0; i < callCenterModel.getIncomingCalls(); i++) {
            calls.add(new Call());
        }

        return calls;

    }

    /**
     * Retorna lista de objetos Employee de acuerdo al numero y tipo de
     * empleados (OPERATOR, SUPERVISOR, DIRECTOR)
     *
     * @param callCenterModel
     * @return
     */
    protected void buildEmployees(CallCenter callCenterModel) {
        employees = new ArrayList<>();
        for (int i = 0; i < callCenterModel.getnOperators(); i++) {
            Employee operator = new Employee(PRE_EMP + i, Estado.DISPONIBLE, EmployeeType.OPERATOR);
            employees.add(operator);
        }
        for (int i = 0; i < callCenterModel.getnSupervisor(); i++) {
            Employee superVisor = new Employee(PRE_EMP + i, Estado.DISPONIBLE, EmployeeType.SUPERVISOR);
            employees.add(superVisor);
        }
        for (int i = 0; i < callCenterModel.getnDirectors(); i++) {
            Employee director = new Employee(PRE_EMP + i, Estado.DISPONIBLE, EmployeeType.DIRECTOR);
            employees.add(director);
        }

    }

    private Employee getEmployeeAvailable() {
        Employee employeeAvailable = null;

        for (Employee employee : employees) {
            if (employee.getEstado() == Estado.DISPONIBLE) {
                employee.setEstado(Estado.OCUPADO);
                employeeAvailable = employee;
                break;
            }
        }

        return employeeAvailable;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
