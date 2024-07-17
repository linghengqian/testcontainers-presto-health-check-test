package com.lingh;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "resource"})
@Testcontainers
public class PrestoTest {

    @Container
    public GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("prestodb/presto:0.288"))
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("default", Transferable.DEFAULT_DIR_MODE),
                    "/opt/presto-server/etc"
            )
            .withExposedPorts(8080)
            .waitingFor(
                    new HttpWaitStrategy()
                            .forPath("/v1/cluster")
                            .forPort(8080)
                            .forResponsePredicate(anObject -> {
                                try {
                                    return new ObjectMapper()
                                            .readTree(anObject)
                                            .get("activeWorkers")
                                            .asInt() > 0;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })
            );

    @Test
    void test() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "test");
        try (Connection connection = DriverManager.getConnection("jdbc:presto://" + container.getHost() + ":" + container.getMappedPort(8080) + "/", props);
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE memory.default.table_with_array AS SELECT 1 id, ARRAY[1, 42, 2, 42, 4, 42] my_array
                    """);
            try (ResultSet resultSet = statement.executeQuery(
                    """
                            SELECT nationkey, element
                            FROM tpch.tiny.nation
                            JOIN memory.default.table_with_array twa ON nationkey = twa.id
                            CROSS JOIN UNNEST(my_array) a(element)
                            ORDER BY element OFFSET 1 FETCH FIRST 3 ROWS ONLY
                            """
            )
            ) {
                List<Integer> actualElements = new ArrayList<>();
                while (resultSet.next()) {
                    actualElements.add(resultSet.getInt("element"));
                }
                assertThat(actualElements, is(Arrays.asList(2, 4, 42)));
            }
        }
    }
}
