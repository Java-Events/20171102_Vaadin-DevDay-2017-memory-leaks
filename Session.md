# Session itself

## JVM 
-XX:+HeapDumpOnOutOfMemoryError
https://blog.codecentric.de/2008/07/memory-analyse-teil-1-java-heapdumps-erzeugen/
https://blogs.oracle.com/sundararajan/programmatically-dumping-heap-from-java-applications
http://vlkan.com/blog/post/2016/08/12/hotspot-heapdump-threadump/
http://blog.ragozin.info/2015/02/programatic-heapdump-analysis.html


## GC Analyzer
10 GC logs per Day : http://gceasy.io/pricing.jsp
https://gcplot.com/app/

-XX:+PrintGCDetails -XX:+PrintTenuringDistribution -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:/path/to/file


##Docker infos
docker run -v /var/run/docker.sock:/var/run/docker.sock -d --restart=always --privileged -p 2375:2375 --name docker-sock-proxy docksal/socat
export DOCKER_HOST=tcp://192.168.0.100:2375
docker-compose up

single command on remote docker host 
docker -H tcp://remote:2375 pull ubuntu


## Yourkit 
download
Linux  : https://www.yourkit.com/download/YourKit-JavaProfiler-2017.02-b66.zip
OSX    : https://www.yourkit.com/download/YourKit-JavaProfiler-2017.02-b66-mac.zip
Windows: https://www.yourkit.com/download/YourKit-JavaProfiler-2017.02-b66.exe

To run the Agent , no lic is needed [https://www.yourkit.com/docs/java/help/attach_wizard.jsp](https://www.yourkit.com/docs/java/help/attach_wizard.jsp)  

## Debugger by yourself
https://www.beyondjava.net/blog/how-to-count-java-objects-in-memory/

## Visual VM
Use it for Instance count
remote analyzing

-Dcom.sun.management.jmxremote.rmi.port=9090
-Dcom.sun.management.jmxremote=true
-Dcom.sun.management.jmxremote.port=9090 
-Dcom.sun.management.jmxremote.ssl=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.local.only=false
-Djava.rmi.server.hostname=192.168.99.100



## Intro
Difference between TDD and APM
How to test a Vaadin App in terms of 
performance and memory leaks

### Tools

#### Flow - ?
Top visualize the CallGraph, get an idea of the time consuming part.
The main thing here is the understanding of the app itself.
How to find out, what is done in the order I navigate through the app.

#### perfino
Nice to analyse one VM on the developer machine over the night.
Max 24h of recording. 

#### stagemonitor
OS Version of an APM tool 

##### stagemonitor in Docker

docker-compose up // or docker-compose up -d for daemon mode

kibana DashBoard [http://0.0.0.0:5601](http://0.0.0.0:5601)
Elastic [http://localhost:9200](http://localhost:9200)
grafana [http://0.0.0.0:3000/](http://0.0.0.0:3000/)
create an API Key with Admin Rights at Grafana 
add this to the property file

