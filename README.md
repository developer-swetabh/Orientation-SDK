# Orientation-SDK
Created an Orientation SDK using AIDL which will return phone orientation using <b>Sensor</b> 
<br>
The project contains 3 parts:
<ol><li>Library</li>
<li>Server app</li>
<li>Client App</li></ol>
<h2>Library</h2>
The library part contains the necessary AIDL file require to interact the client and server.
<h2>Server</h2>
The Server part contains the OrientationEngine which will compute the orientation from the Sensor Service.
<h2>Client</h2>
The Client part is a demo application which will communicate to server to get the orientation.
<h2>Highlights of SDK (Library + Server)</h2>
<ul><li>The SDK is using Sensor.TYPE_ROTATION_VECTOR as the parameter to get data from Sensor Service</li>
<li>The sensor is providing data at certain interval of time</li>
<li>Multiple applications/clients can connect to service using the SDK a time to get the orientation data.</li> 
<li>The SDK is using <b>OrientationEngine</b> class which is a <b>Singleton class</b>. The role of this class is to compute the orientation and send the result to client.</li></ul>

