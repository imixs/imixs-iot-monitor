# Imixs-Workflow IoT

The Imixs-Workflow IoT project provides a set of connectors and services to process data generated by IoT devices with the Imixs-Workflow platform. With the provided adapter classes data produced by a micro controller like [Arduino](https://www.arduino.cc/) A 32 bit microcontroller RP2040(https://datasheets.raspberrypi.org/rp2040/rp2040-datasheet.pdf) can be easily processed just by describing a workflow in a BPMN 2.0 model.

In this way it is for example possible to react on senor data like  temperature, humidity, movement, acceleration and any other things provided by a IoT device, and start some kind of human centric workflow. This includes monitoring, alerting or control IoT devices.

Imixs-Workflow IoT provides a set of adapter classes to connect a IoT device to the Imixs Workflow engine without the need for in-depth knowledge in micro controller programming. 
For example, reaching a certain temperature range can directly trigger an alarm from a technician. 

Imixs-Workflow IoT includes a Web Server with a modern Web interface to monitor connected IoT devices and react on certain events. 

## Arduino Nano RP2040 Connect

The Arduino Nano RP2040 Connect has the same form-factor and pinout as the original Arduino Nano 8-bit AVR board, but this is a powerful board with both an RP2040 and an ESP32, the latter embedded within a u-blox NINA-W102 WiFi and Bluetooth module.

The board has some pretty impressive features:

 - RP2040 Dual-core MCU
 - WiFi 801.11b/g/n
 - Bluetooth and BLE 4.2
 - 6-axis IMU with Machine Learning
 - MEMS Microphone Module
 - Cryptographic Coprocessor
 - Internal switching power supply
 - 16Mb Flash Memory
 - Onboard RGB LED

Read the [section Arduino](./doc/arduino/README.md) for details how to work with Arduino boards.

## Sensor Data

In the following basic szenario the Ardoino controller is connected directly to two external sensors. 



     ╔═╧═╧═╧═╧═╧═╧══╗
     ║ Ardoino Nano ║
     ╚═╤═╤═╤═╤═╤═╤══╝
         │   │          ╭────────╮ 
         │   ╰──────────┤Sensor-2│   
         │              ╰────────╯
    ╭────┴───╮     
    │Sensor-1│     
    ╰────────╯     

The data can be controlled by Imixs-IoT based on a BPMN 2.0 model definition. 


## Inter Controller Communication

In a more complex situation you can also connect an existing microcontroler to the Ardoino Contrller. In this case you need to re-map the GPIO and connect the Ardoino controller. 


     ╔═╧═╧═╧═╧═╧═╧═╧═╧══╗
     ║                  ║
     ║ Ext Conroller-1  ║
     ║                  ║
     ╚═╤═╤═╤═╤═╤═╤═╤═╤══╝
             │
             ╰─────────╮
                     ╔═╧═╧═╧═╧═╧═╧══╗
                     ║ Ardoino Nano ║
                     ╚═╤═╤═╤═╤═╤═╤══╝

                     
 
 
# Docker
 
To build the artifact from sources run:

	$ mvn clean install

To build the new Docker Image run:

	$ mvn clean install -Pdocker
	
To run Wildfly Server in debug mode (port 8787) you can build the Docker Image with:

	$ mvn clean install -Pdebug

Run

To start the Imixs-Process Manger with docker-compose, run:

	$ docker-compose -f docker-compose-dev.yaml up
