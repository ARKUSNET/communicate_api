package com.droneservice.communicateapi;

import com.droneservice.communicateapi.controller.DroneController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@SpringBootTest
public class CommunicateApiApplicationTests {

    @Autowired
    private DroneController droneController;

    @Test
    public void contextLoads() {
        assertNotNull(droneController);
    }
}
