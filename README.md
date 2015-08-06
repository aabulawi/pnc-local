# pnc-local
Run PNC builds locally.

#Build
mvn clean install

#Usage
To build you just need to pass an valid configuration file.

```
java -jar local-build.jar -f <path-to-configuration>
```

Configurations should in the following form
```
[
{ "name" : <name>, "scm" : <scm with # appended>, "command" : <build command>, "dependencies" : <list of dependencies based on the name> },
.
.
.
{ "name" : <name>, "scm" : <scm with # appended>, "command" : <build command>, "dependencies" : <list of dependencies based on the name> }
]
```
 
Example configurations can be found https://github.com/ahmedlawi92/pnc-local/tree/master/example-configurations.
