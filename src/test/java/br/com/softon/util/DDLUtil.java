package br.com.softon.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.platform.hsqldb.HsqlDbPlatform;
import org.apache.ddlutils.platform.postgresql.PostgreSqlPlatform;
import org.postgresql.ds.PGSimpleDataSource;

public class DDLUtil {

    public static void main(String[] args) throws Exception {
        final Set<String> schemas = new HashSet<String>();

        final PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("192.168.1.116");
        source.setPortNumber(5433);
        source.setDatabaseName("softon");
        source.setUser("postgres");
        source.setPassword("pw");

        final Database db = readDatabase(source);

        for (final Table table : db.getTables()) {

            for (final Table allTable : db.getTables()) {
                if (allTable.getForeignKeys() != null) {
                    for (ForeignKey fkeys : allTable.getForeignKeys()) {
                        if (fkeys.getForeignTable() != null && fkeys.getForeignTable().equals(table)) {
                            fkeys.setForeignTableName(table.getSchema() + "." + table.getName());
                        }
                    }
                }
            }
            table.setName(table.getSchema() + "." + table.getName());
            schemas.add(table.getSchema().toUpperCase());
        }

        final Platform hsqlDbPlatform = new HsqlDbPlatform();

        final String testSqlMysql = hsqlDbPlatform.getCreateTablesSql(db, false, false);

        final StringBuilder sb = new StringBuilder();

        for (String schema : schemas) {
            sb.append("CREATE SCHEMA ").append(schema).append(";\n");
        }

        sb.append(testSqlMysql);

        FileUtils.write(new File("data/h2schema.ddl"), sb.toString());
    }

    public static Database readDatabase(final PGSimpleDataSource dataSource) {
        final PostgreSqlPlatform platform = new PostgreSqlPlatform();
        platform.setDataSource(dataSource);
        platform.setSqlCommentsOn(false);
        return platform.readModelFromDatabase("model"); //, null, "coorp", null);
    }

}
