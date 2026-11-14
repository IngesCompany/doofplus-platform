package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlertFromReadingTest {

    private static final Sensor SENSOR = new Sensor(new CreateSensorCommand(new SensorCode("T-204"),
            new DeviceId("tb-device-204"), "Temperature", "°C", "EQ-COAT-02", 18.0, 25.0, "active", "B-26041"));

    private static Reading reading(double value) {
        return new Reading(new CreateReadingCommand("T-204", "B-26041", value, "2026-10-07T10:26:00Z", "ThingsBoard"));
    }

    @Test
    void aSmallExcessShouldBeAWarning() {
        var alert = Alert.fromOutOfRangeReading(SENSOR, reading(25.4));

        assertThat(alert.getSeverity()).isEqualTo("warning");
        assertThat(alert.getStatus()).isEqualTo("open");
        assertThat(alert.getCode().value()).isEqualTo("ALT-261007102600-T-204");
        assertThat(alert.getTitle()).isEqualTo("Temperature above the limit");
        assertThat(alert.getBatchCode()).isEqualTo("B-26041");
    }

    @Test
    void anExcessOfMoreThanTenPercentOfTheRangeShouldBeCritical() {
        assertThat(Alert.fromOutOfRangeReading(SENSOR, reading(26.0)).getSeverity()).isEqualTo("critical");
        assertThat(Alert.fromOutOfRangeReading(SENSOR, reading(15.0)).getTitle()).isEqualTo("Temperature below the limit");
    }
}
