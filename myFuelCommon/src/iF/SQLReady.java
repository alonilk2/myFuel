package iF;

import java.sql.Connection;
import java.sql.Statement;

public interface SQLReady {
	public abstract int createNewAddSqlStatement(Connection conn);

}
