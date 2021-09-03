package gr.codelearn.eshop.base.extension;

import gr.codelearn.eshop.DataSource;
import gr.codelearn.eshop.EshopDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

@Slf4j
public class DatabaseManager implements BeforeAllCallback, AfterEachCallback, ExtensionContext.Store.CloseableResource {
    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            createStructure();
            // The following line registers a callback hook when the root test context is shutdown (when the global
            // store is shutdown, then this extension needs to also shutdown)
            context.getRoot().getStore(GLOBAL).put(this.getClass().getName(), this);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        log.info("----------------------AFTER EACH----------------------");
        truncateStructure();
    }

    @Override
    public void close() {
        // Callback that is invoked exactly once after the end of all test containers.
        // See the following: https://junit.org/junit5/docs/current/user-guide/#extensions-keeping-state
        dropStructure();
    }

    public void createStructure() {
        try (Connection connection = DataSource.getConnection(); Statement statement = connection.createStatement()) {
            //@formatter:off
            runCommands(statement,
                    EshopDemo.sqlCommands.getProperty("create.table.001"),
                    EshopDemo.sqlCommands.getProperty("create.table.002"),
                    EshopDemo.sqlCommands.getProperty("create.table.003"),
                    EshopDemo.sqlCommands.getProperty("create.table.004"),
                    EshopDemo.sqlCommands.getProperty("create.index.001"),
                    EshopDemo.sqlCommands.getProperty("create.index.002"));
            //@formatter:on
        } catch (SQLException ex) {
            log.warn("Error while creating table(s), {}.", ex.getMessage());
        }
    }

    public void truncateStructure() {
        try (Connection connection = DataSource.getConnection(); Statement statement = connection.createStatement()) {
            //@formatter:off
            runCommands(statement,
                    EshopDemo.sqlCommands.getProperty("truncate.table.001"),
                    EshopDemo.sqlCommands.getProperty("truncate.table.002"),
                    EshopDemo.sqlCommands.getProperty("truncate.table.003"),
                    EshopDemo.sqlCommands.getProperty("truncate.table.004"),
                    EshopDemo.sqlCommands.getProperty("reset.table.id.001"),
                    EshopDemo.sqlCommands.getProperty("reset.table.id.002"),
                    EshopDemo.sqlCommands.getProperty("reset.table.id.003")
            );
            //@formatter:on
        } catch (SQLException ex) {
            log.warn("Error while truncating/resetting table(s), {}.", ex.getMessage());
        }
    }

    public void dropStructure() {
        try (Connection connection = DataSource.getConnection(); Statement statement = connection.createStatement()) {
            //@formatter:off
            runCommands(statement,
                    EshopDemo.sqlCommands.getProperty("drop.table.001"),
                    EshopDemo.sqlCommands.getProperty("drop.table.002"),
                    EshopDemo.sqlCommands.getProperty("drop.table.003"),
                    EshopDemo.sqlCommands.getProperty("drop.table.004")
            );
            //@formatter:on
        } catch (SQLException ex) {
            log.warn("Error while dropping table(s), {}.", ex.getMessage());
        }
    }

    static void runCommands(Statement statement, String... commands) throws SQLException {
        for (String command : commands) {
            log.debug("'{}' command was successful with {} row(s) affected.", command,
                    statement.executeUpdate(command));
        }
    }
}
