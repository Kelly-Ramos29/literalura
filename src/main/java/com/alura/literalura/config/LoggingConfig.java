package com.alura.literalura.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

public class LoggingConfig {

    public LoggingConfig() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        Logger hikariLogger = loggerContext.getLogger("com.zaxxer.hikari");
        hikariLogger.setLevel(Level.INFO);

        Logger hibernateSqlLogger = loggerContext.getLogger("org.hibernate.SQL");
        hibernateSqlLogger.setLevel(Level.INFO);

        Logger hibernateBinderLogger = loggerContext.getLogger("org.hibernate.binder");
        hibernateBinderLogger.setLevel(Level.WARN);

        Logger hibernateHbm2ddlLogger = loggerContext.getLogger("org.hibernate.hbm2ddl");
        hibernateHbm2ddlLogger.setLevel(Level.INFO);

        Logger hibernateTransactionLogger = loggerContext.getLogger("org.hibernate.transaction");
        hibernateTransactionLogger.setLevel(Level.INFO);

        Logger hibernateConnectionLogger = loggerContext.getLogger("org.hibernate.connection");
        hibernateConnectionLogger.setLevel(Level.INFO);
    }
}