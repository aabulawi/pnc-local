# pnc-local
This project was made to emulate PNC builds on a developer's host machine. 

It uses the BuildCoordinator from PNC to coordinate builds, and ensure that dependencies are properly resolved. For example if project A depends on B and B depends on C, it will ensure the the build will proceed in the following order, C -> B -> A.

#Build
mvn clean install

#Usage
To perform a build you just need to pass a valid configuration file.

```
java -jar local-build.jar -f <path-to-configuration>
```

Configuration files be should a json file in the form below.
```
[
{ "name" : <name>, "scm" : <scm with # appended>, "command" : <build command>, "dependencies" : <list of dependencies based on the name> },
.
.
.
{ "name" : <name>, "scm" : <scm with # appended>, "command" : <build command>, "dependencies" : <list of dependencies based on the name> }
]
```

name : This the name of the project you are building.

scm : This contains both the scm URL and the SHA or tag appended after  #.

command : This is the command that will be executed to perform the build for that project.

dependencies : This is a list of projects that this build is dependant on. Dependencies should be listed by their names.
 
Example configurations can be found here, https://github.com/ahmedlawi92/pnc-local/tree/master/example-configurations.
