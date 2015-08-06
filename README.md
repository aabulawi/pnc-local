# pnc-local
Run PNC builds locally.

#Build
mvn clean install

#Usage
To perform a build you just need to pass a valid configuration file.

```
java -jar local-build.jar -f <path-to-configuration>
```

Configuration files be should in the following form
```
[
{ "name" : <name>, "scm" : <scm with # appended>, "command" : <build command>, "dependencies" : <list of dependencies based on the name> },
.
.
.
{ "name" : <name>, "scm" : <scm with # appended>, "command" : <build command>, "dependencies" : <list of dependencies based on the name> }
]
```
 
Example configurations can be found here, https://github.com/ahmedlawi92/pnc-local/tree/master/example-configurations.
