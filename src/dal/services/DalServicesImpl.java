package dal.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import exceptions.FatalException;
import util.AppContext;
import util.AppContext.DependanceInjection;
import util.MonLogger;
import util.Util;

class DalServicesImpl implements DalServices, DalBackendServices {

  @DependanceInjection
  private AppContext appContext;
  @DependanceInjection
  private MonLogger monLogger;


  private ThreadLocal<Connection> connThrLoc = new ThreadLocal<>();
  private DataSource dataSource = null;

  private void connect() {
    if (dataSource == null) {
      setUpDataSource();
    }
    if (connThrLoc.get() == null) {
      try {
        connThrLoc.set(dataSource.getConnection());
      } catch (SQLException ex) {
        monLogger.getMonLog().fine(Util.stackTraceToString(ex));
        throw new FatalException("Erreur de connexion à la base de données");
      }
    }
  }

  private void freeConnection() {
    if (connThrLoc.get() == null) {
      throw new FatalException("Erreur de programmation");
    }
    try {
      if (!connThrLoc.get().isClosed()) {
        try {
          connThrLoc.get().setAutoCommit(true);
        } catch (SQLException ex) {
          monLogger.getMonLog().fine(Util.stackTraceToString(ex));
        }
        connThrLoc.get().close();
      }
    } catch (SQLException ex) {
      throw new FatalException("Erreur de connexion à la base de données");
    } finally {
      connThrLoc.remove();
    }
  }


  @Override
  public void startTransaction() {
    connect();
    try {
      connThrLoc.get().setAutoCommit(false);
    } catch (SQLException ex) {
      freeConnection();
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur dans la méthode startTransaction()");
    }

  }

  public void commitTransaction() {

    if (connThrLoc.get() == null) {
      throw new FatalException("Erreur de programmation");
    }
    try {
      connThrLoc.get().commit();
      freeConnection();
    } catch (SQLException ex) {
      monLogger.getMonLog().severe("Erreur SQL: " + Util.stackTraceToString(ex));
      rollback();
    }

  }

  @Override
  public void rollback() {

    if (connThrLoc.get() == null) {
      throw new FatalException("Erreur de programmation");
    }
    try {
      connThrLoc.get().rollback();
    } catch (SQLException ex) {
      freeConnection();
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Erreur base de données rollback()");
    }
  }

  public PreparedStatement getPreparedStatement(String query) {

    if (connThrLoc.get() == null) {
      throw new FatalException("Erreur de programmation");
    }
    PreparedStatement ps = null;
    try {
      ps = connThrLoc.get().prepareStatement(query);
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Impossible de récuperer le preparedStatement");
    }
    return ps;
  }

  public PreparedStatement getPreparedStatementForInsert(String query) {

    if (connThrLoc.get() == null) {
      throw new FatalException("Erreur de programmation");
    }
    PreparedStatement ps = null;
    try {
      ps = connThrLoc.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    } catch (SQLException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Impossible de récuperer le preparedStatement");
    }
    return ps;
  }

  private void setUpDataSource() {
    if (dataSource != null) {
      return;
    }
    try {
      Class.forName(appContext.getValueProp("driverClassName"));
    } catch (ClassNotFoundException ex) {
      monLogger.getMonLog().fine(Util.stackTraceToString(ex));
      throw new FatalException("Driver Postgresql manquant !");
    }

    ConnectionFactory connectionFactory =
        new DriverManagerConnectionFactory(appContext.getValueProp("url"),
            appContext.getValueProp("ndcDB"), appContext.getValueProp("mdpDB"));

    PoolableConnectionFactory poolableConnectionFactory =
        new PoolableConnectionFactory(connectionFactory, null);

    ObjectPool<PoolableConnection> connectionPool =
        new GenericObjectPool<>(poolableConnectionFactory);

    poolableConnectionFactory.setPool(connectionPool);

    dataSource = new PoolingDataSource<>(connectionPool);
  }
}
