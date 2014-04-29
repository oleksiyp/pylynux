PYLYNUX
=======

Very basic linux

Build
-----
Build procedure:

```
mvn clean package
```

Runnable result jar could be found in target/pylynux-*-jar-with-dependencies.jar

Use
---

For command help use "-h".
Available commands:
 * cat file
 * cd dir
 * chmod mod path
 * echo any string
 * ls [-l] [-a] [dir]
 * mkdir dir
 * stat file

Features
--------
* separation of kernel and user level by facade interface
* syscalls: list, mkdir, cwd, chdir, stat, open, read, write, close, exec, chmod
* security checks
* user.lib for simpler program creation

Sample session
--------------
Could be run by SampleSessionTest

TODO
----
* PathResolver test (one of central component)
* Shell as a Program

Dependencies
------------
Pylynux is dependent on following libs(JAR built-in):
 * JLine 2.11 for console handling
 * args4j as getopt
