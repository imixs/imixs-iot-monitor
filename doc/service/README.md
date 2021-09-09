# The Imixs IoT Monitoring Service

Imixs-IoT-Monitoring includes a microservice with a self contained web interface. To start the service you can simply run the following docker-compose command:

	$ docker-compose up

This will start a PostgreSQL Database and a Imixs-Workflow instance. 




## The Rest API

Via the Rest API which is part of the Imixs-Workflow engine you can create and process a process instance. Usually this is the way how the Arduino controller will send data to your microservice. To simulate the creation of a new process instance you can run the *curl* command:

	$ curl --user admin:adminadmin -H "Content-Type: application/xml" -H 'Accept: application/xml' -d  \
	   '<document xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema">
		<item name="$modelversion"><value xsi:type="xs:string">1.0</value></item>
		<item name="$taskid"><value xsi:type="xs:int">1000</value></item>
		<item name="$eventid"><value xsi:type="xs:int">10</value></item>
		<item name="device"><value xsi:type="xs:string">Arduino Nano</value></item>
		<item name="metrics">
	            <value xsi:type="xs:string">some data...</value>
	            <value xsi:type="xs:string">more data...</value>
	        </item>
	   </document>' \
	  http://localhost:8080/api/workflow/workitem
	

