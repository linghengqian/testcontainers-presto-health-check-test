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
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 11.00 s <<< FAILURE! -- in com.lingh.PrestoTest
[ERROR] com.lingh.PrestoTest.test -- Time elapsed: 10.90 s <<< ERROR!
java.sql.SQLException: Query failed (#20240716_105708_00000_xzrms): No nodes available to run query
        at com.facebook.presto.jdbc.PrestoResultSet.resultsException(PrestoResultSet.java:1841)
        at com.facebook.presto.jdbc.PrestoResultSet$ResultsPageIterator.computeNext(PrestoResultSet.java:1821)
        at com.facebook.presto.jdbc.PrestoResultSet$ResultsPageIterator.computeNext(PrestoResultSet.java:1760)
        at com.facebook.presto.jdbc.internal.guava.collect.AbstractIterator.tryToComputeNext(AbstractIterator.java:141)
        at com.facebook.presto.jdbc.internal.guava.collect.AbstractIterator.hasNext(AbstractIterator.java:136)
        at com.facebook.presto.jdbc.internal.guava.collect.TransformedIterator.hasNext(TransformedIterator.java:42)
        at com.facebook.presto.jdbc.internal.guava.collect.Iterators$ConcatenatedIterator.getTopMetaIterator(Iterators.java:1311)
        at com.facebook.presto.jdbc.internal.guava.collect.Iterators$ConcatenatedIterator.hasNext(Iterators.java:1327)
        at com.facebook.presto.jdbc.PrestoResultSet.next(PrestoResultSet.java:146)
        at com.facebook.presto.jdbc.PrestoStatement.internalExecute(PrestoStatement.java:291)
        at com.facebook.presto.jdbc.PrestoStatement.execute(PrestoStatement.java:230)
        at com.lingh.PrestoTest.test(PrestoTest.java:49)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
Caused by: com.facebook.presto.spi.PrestoException: No nodes available to run query
        at com.facebook.presto.spi.NodeManager.getRequiredWorkerNodes(NodeManager.java:34)
        at com.facebook.presto.plugin.memory.MemoryMetadata.beginCreateTable(MemoryMetadata.java:217)
        at com.facebook.presto.plugin.memory.MemoryMetadata.beginCreateTable(MemoryMetadata.java:71)
        at com.facebook.presto.metadata.MetadataManager.beginCreateTable(MetadataManager.java:834)
        at com.facebook.presto.execution.scheduler.TableWriteInfo.createWriterTarget(TableWriteInfo.java:96)
        at com.facebook.presto.execution.scheduler.TableWriteInfo.createWriterTarget(TableWriteInfo.java:118)
        at com.facebook.presto.execution.scheduler.TableWriteInfo.createTableWriteInfo(TableWriteInfo.java:76)
        at com.facebook.presto.execution.scheduler.SectionExecutionFactory.createSectionExecutions(SectionExecutionFactory.java:175)
        at com.facebook.presto.execution.scheduler.SqlQueryScheduler.createStageExecutions(SqlQueryScheduler.java:354)
        at com.facebook.presto.execution.scheduler.SqlQueryScheduler.<init>(SqlQueryScheduler.java:243)
        at com.facebook.presto.execution.scheduler.SqlQueryScheduler.createSqlQueryScheduler(SqlQueryScheduler.java:172)
        at com.facebook.presto.execution.SqlQueryExecution.planDistribution(SqlQueryExecution.java:627)
        at com.facebook.presto.execution.SqlQueryExecution.start(SqlQueryExecution.java:469)
        at com.facebook.presto.$gen.Presto_0_288_15f14bb____20240716_105704_1.run(Unknown Source)
        at com.facebook.presto.execution.SqlQueryManager.createQuery(SqlQueryManager.java:319)
        at com.facebook.presto.dispatcher.LocalDispatchQuery.lambda$startExecution$8(LocalDispatchQuery.java:211)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)

[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Errors: 
[ERROR]   PrestoTest.test:49 » SQL Query failed (#20240716_105708_00000_xzrms): No nodes available to run query
[INFO] 
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  12.766 s
[INFO] Finished at: 2024-07-16T18:57:09+08:00
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