# aruino

In the following section you will find some information how to setup a aruino controller with the aruino IDE and how to install the Imixs-IoT libraries on your aruino device.
The documentation we explain the setup based on the [aruino Nano RP2040](https://docs.arduino.cc/hardware/nano-rp2040-connect). The  RP2040 includes already a Wifi controller which makes it easy to connect the controller with a Imixs-Workflow Service. 

## The aruino IDE


First of all you should install the official aruino IDE. A step-by-step install guide for Linux can be found [here](https://www.arduino.cc/en/Guide/Linux).

First download the  latest realease from [here](https://www.arduino.cc/en/software) or find all releases on the [Github release page](https://github.com/arduino/Arduino/releases/).


Extract the package in your preferred directory - e.g. /opt/aruino/ide/. In the following exampl I use the version 1.8.15. Replace the version number with the one you have downloaded:


	$ mkdir -p /opt/aruino/ide/
	$ tar -xf arduino-1.8.15-linux64.tar.xz -C /opt/aruino/ide/
	
Switch into the IDE directory and run the install script

	$ cd /opt/aruino/ide/arduino-1.8.15/
	$ sudo ./install.sh

To get permissions for the dialout you possible need to add your current user to this group. This can be done with the command:

	$ sudo usermod -a -G dialout $USER

You will need to log out and log in again for this change to take effect. You can verify this with the command 'id'

	$ id
	uid=1000(ardoniouser) gid=1000(ardoniouser) Gruppen=1000(ardoniouser),20(dialout),....
	

Now you can start the aruino IDE with:

	$ aruino	
	
	
<img src="./images/ide-screen-01.png" />

	


## Installing Mbed OS Nano boards

A step-by-step guide to install the Nano RP2040 Connect board can be found [here](https://docs.arduino.cc/software/ide-v1/tutorials/getting-started/cores/arduino-mbed_nano).

First navigate to Tools > Board > Board Manager.

In this dialog you can search for your Ardono Board. For the [aruino Nano RP2040](https://docs.arduino.cc/hardware/nano-rp2040-connect) find the one named **Arduino Mbed OS Nano Boards** and install it.

<img src="./images/ide-screen-02.png" />

Exit the board manager, and go to Tools > Board > Arduino > Arduino Mbed OS Nano Boards. Here you can see all the Mbed boards listed, where you can select the board you are using. You have now successfully installed the core.

In my case this is

	Tools > Board > Arduino > Arduino Mbed OS Nano Boards > aruino Nano RP2040 Connect


## Testing your Board

To test yoru board you can choose one of the examples provided in the Arduino IDE.

Navigate to File > Examples > 01.Basics > Blink.

Next click on the 'Upload' button to install and run the test programm.


