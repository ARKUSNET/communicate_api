package com.droneservice.communicateapi.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.droneservice.communicateapi.service.DbService.findDroneBySerialNumberBatteryLoad(..))")
    public void findDroneBySerialNumberBatteryLoadMethods() {
    }

    @Pointcut("execution(* com.droneservice.communicateapi.service.DbService.findDroneBySerialNumberLoad(..))")
    public void findDroneBySerialNumberLoadMethods() {
    }

    @Pointcut("execution(* com.droneservice.communicateapi.service.DbService.findMedicationByCode(..))")
    public void findMedicationByCodeMethods() {
    }

    @Pointcut("execution(* com.droneservice.communicateapi.service.DbService.findMedicationLoadDetailsBySerialNumber(..))")
    public void findMedicationLoadDetailsBySerialNumberMethods() {
    }

    @Pointcut("execution(* com.droneservice.communicateapi.service.DroneServiceApiImpl.*(..)) ")
    public void anyDroneServiceApiImplMethods() {
    }
}
