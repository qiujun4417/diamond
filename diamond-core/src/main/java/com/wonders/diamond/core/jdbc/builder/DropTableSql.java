package com.wonders.diamond.core.jdbc.builder;


import com.wonders.diamond.core.exceptions.JdbcException;
import com.wonders.diamond.core.jdbc.SQLFormatter;
import com.wonders.diamond.core.jdbc.SqlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nick.
 */
public class DropTableSql {

    private static final Logger LOGGER = LoggerFactory.getLogger(DropTableSql.class);

    private SqlTemplate sqlTemplate;
    private StringBuilder sql = new StringBuilder();

    public DropTableSql(SqlTemplate sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public DropTableSql drop(String table) {
        sql.append("DROP TABLE IF EXISTS ").append(table);
        return this;
    }

    public boolean doDrop() {

        String finalSQL = sql.toString();

        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(SQLFormatter.format(finalSQL));
            }

            sqlTemplate.update(sql.toString());
        } catch (Exception e) {
            throw new JdbcException("Drop Table Error:" + SQLFormatter.format(finalSQL), e);
        }
        return true;
    }

}
