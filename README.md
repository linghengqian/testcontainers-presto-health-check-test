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
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 16.54 s -- in com.lingh.PrestoTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  18.802 s
[INFO] Finished at: 2025-01-22T23:06:57+08:00
[INFO] ------------------------------------------------------------------------
```