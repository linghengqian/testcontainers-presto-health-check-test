# testcontainers-presto-health-check-test

- For https://github.com/testcontainers/testcontainers-java/pull/8946 .
- Execute the following command on the Ubuntu 22.04.4 LTS instance with `SDKMAN!` and Docker.

```bash
sdk install java 17.0.11-ms
sdk use java 17.0.11-ms

git clone git@github.com:linghengqian/testcontainers-presto-health-check-test.git
cd ./testcontainers-presto-health-check-test/
./mvnw clean test
```

- Since checking `======== SERVER STARTED ========` in the log is not enough to implement healthcheck,
  such error logs will be unstable. However,
  it seems that the Presto documentation does not mention any available healthcheck methods.

```bash
$./mvnw clean test
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 9.917 s <<< FAILURE! -- in com.lingh.PrestoTest
[ERROR] com.lingh.PrestoTest.test -- Time elapsed: 9.713 s <<< ERROR!
java.sql.SQLException: Query failed (#20240717_030349_00000_nnbf6): Presto server is still initializing
        at com.facebook.presto.jdbc.PrestoResultSet.resultsException(PrestoResultSet.java:1841)
        at com.facebook.presto.jdbc.PrestoResultSet.getColumns(PrestoResultSet.java:1751)
        at com.facebook.presto.jdbc.PrestoResultSet.<init>(PrestoResultSet.java:121)
        at com.facebook.presto.jdbc.PrestoStatement.internalExecute(PrestoStatement.java:272)
        at com.facebook.presto.jdbc.PrestoStatement.execute(PrestoStatement.java:230)
        at com.lingh.PrestoTest.test(PrestoTest.java:56)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
Caused by: com.facebook.presto.spi.PrestoException: Presto server is still initializing
        at com.facebook.presto.security.AccessControlManager$InitializingSystemAccessControl.checkCanSetUser(AccessControlManager.java:874)
        at com.facebook.presto.security.AccessControlManager.lambda$checkCanSetUser$0(AccessControlManager.java:168)
        at com.facebook.presto.security.AccessControlManager.authenticationCheck(AccessControlManager.java:809)
        at com.facebook.presto.security.AccessControlManager.checkCanSetUser(AccessControlManager.java:168)
        at com.facebook.presto.security.AccessControlUtils.checkPermissions(AccessControlUtils.java:41)
        at com.facebook.presto.dispatcher.DispatchManager.createQueryInternal(DispatchManager.java:279)
        at com.facebook.presto.dispatcher.DispatchManager.lambda$createQuery$0(DispatchManager.java:254)
        at com.facebook.airlift.concurrent.BoundedExecutor.drainQueue(BoundedExecutor.java:78)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)

11:03:49.775 [Thread-5] DEBUG org.testcontainers.shaded.com.github.dockerjava.core.command.AbstrDockerCmd - Cmd: 0577e4d3761c3b9952afdc70a9bc4e881c07cb85df92b432e3db62b94bc58651,SIGTERM
[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Errors: 
[ERROR]   PrestoTest.test:56 » SQL Query failed (#20240717_030349_00000_nnbf6): Presto server is still initializing
[INFO] 
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  12.237 s
[INFO] Finished at: 2024-07-17T11:03:49+08:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.2.5:test (default-test) on project testcontainers-presto-health-check-test: 
[ERROR] 
[ERROR] Please refer to /home/linghengqian/TwinklingLiftWorks/git/public/testcontainers-presto-health-check-test/target/surefire-reports for the individual test results.
[ERROR] Please refer to dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
```