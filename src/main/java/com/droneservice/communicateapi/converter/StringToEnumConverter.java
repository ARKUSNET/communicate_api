package com.droneservice.communicateapi.converter;

import com.droneservice.communicateapi.util.DroneState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, DroneState> {

    @Override
    public DroneState convert(String source) {
        return DroneState.valueOf(source.toUpperCase());
    }
}