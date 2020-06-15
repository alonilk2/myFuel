package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import control.sqlController;

public class saleTemplateDBController {
	
	private List<saleTemplate> saleList;
	private sqlController sqlcontrol;
	
	
	public saleTemplateDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
		saleList = new ArrayList<saleTemplate>();
	}
	
	public void initializeList() {
		Statement stm;
		try {
			stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM saletemplate");
			while(rs.next()) {
				
					saleTemplate f = new saleTemplate(rs.getString(1),rs.getFloat(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
					saleList.add(f);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<saleTemplate> getSaleList() {
		return saleList;
	}

	public ListIterator<saleTemplate> getListIterator() {
		return saleList.listIterator();
	}

}
