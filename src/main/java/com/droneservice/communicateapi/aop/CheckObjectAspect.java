package com.droneservice.communicateapi.aop;

import com.droneservice.communicateapi.data.request.DroneMedicationLoadRequest;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.MedicationLoadDetails;
import com.droneservice.communicateapi.exception.DroneSrvRuntimeException;
import com.droneservice.communicateapi.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Aspect
@Slf4j
public class CheckObjectAspect {

    private final DbService dbService;

    @Autowired
    public CheckObjectAspect(DbService dbService) {
        this.dbService = dbService;
    }

    @Around("Pointcuts.findDroneBySerialNumberBatteryLoadMethods()")
    public Object findDroneBySerialNumberBatteryLoadAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        if (result == null) {
            throw new DroneSrvRuntimeException("Drone not found by serial number!");
        }
        return result;
    }

    @Around("Pointcuts.findDroneBySerialNumberLoadMethods()")
    public Object findDroneBySerialNumberLoadAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        if (result == null) {
            throw new DroneSrvRuntimeException("Drone specified does not exist");
        }
        if (((Drone) result).getBatteryCapacity().compareTo(BigDecimal.valueOf(0.25)) < 0) {
            throw new DroneSrvRuntimeException("The Drone cannot be loaded, battery below 25%");
        }
        return result;
    }

    @Around("Pointcuts.findMedicationByCodeMethods()")
    public Object findMedicationByCodeAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        if (result == null) {
            throw new DroneSrvRuntimeException("Medication specified does not exist");
        }
        return result;
    }

    @Around("Pointcuts.findMedicationLoadDetailsBySerialNumberMethods()")
    public Object findMedicationLoadDetailsBySerialNumberAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        if (result == null) {
            throw new DroneSrvRuntimeException("Drone specifies is not loaded or does not exist");
        }
        return result;
    }

    @Before("Pointcuts.anyDroneServiceApiImplMethods() && args(medicationLoad)")
    public void anyDroneServiceApiImplAdvice(JoinPoint joinPoint, DroneMedicationLoadRequest medicationLoad) {
        if (medicationLoad != null) {
            MedicationLoadDetails checkMedicationLoad = dbService.findMedicationLoadDetailsByCode(medicationLoad.getCode());
            if (checkMedicationLoad != null) {
                throw new DroneSrvRuntimeException("Medication code has already been loaded, try another one");
            }
        }
    }
}
