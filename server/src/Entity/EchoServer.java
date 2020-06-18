package Entity;

import java.io.*;
import control.UserController;
import control.sqlController;
import iF.SQLReady;
import ocsf.server.*;



public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  final public static int DEFAULT_PORT = 5555;
  
  //Global Lists ****************************************************
  
  //Controllers *****************************************************
  
  private UserController usercontrol;
  private sqlController sqlcontrol;
  private HomeFuelOrderDBController HFOControl;
  private OrderDBController OrderControl;
  private FuelTypesDBController FTControl;
  private EmployeeDBController EmployeeControl;
  private OrderFromSupplierDBController OFSControl;
  private RequestDBController ReqControl;
  private CarDBController CarControl;
  private FuelCompanyDBController FCController;
  private AnalyticSystemDBController ASControl;
  private saleTemplateDBController saleTempControl;
  private FuelTypeTempDBController FTTControl;
  
  //Constructors ****************************************************
  
  public EchoServer(int port) 
  {
    super(port);
    usercontrol = new UserController();
  }

  public void handleMessageFromClient(Object msg, ConnectionToClient client)  {
	System.out.println("Message received: " + msg + " from " + client);
	if(msg instanceof Request) {
		try {
			ReqControl.requestParser((Request)msg, client);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	else try {
		handleObjectsFromClient(msg, client);
	} catch (IOException e) {
		e.printStackTrace();
	}

  }
  private void handleObjectsFromClient(Object msg, ConnectionToClient client) throws IOException {
	  if(msg != null) {
		  if(msg instanceof SQLReady) {
			  //New HomeFuelOrder instance creation
			  if(msg instanceof HomeFuelOrder) {
				  HomeFuelOrder newOrder = (HomeFuelOrder)msg;
				  HFOControl.addNewOrderToDB(newOrder, client);
				  //Reduce Specific Fuel Type Stock:
				  FuelType tempFuelType = FTControl.findEqualFuelType(newOrder.getFueltype());
				  if(tempFuelType != null) {
					  if(FTControl.checkFuelStock(tempFuelType).booleanValue())
						  OFSControl.createNewOrderFromFuelSupplier(tempFuelType);
				  }
				  return;
			  }
			  else if(msg instanceof Order) {
				  Order newOrder = (Order)msg;
				  OrderControl.addNewOrderToDB(newOrder, client);
				  FuelType tempFuelType = FTControl.findEqualFuelType(newOrder.getFueltype());
				  //Reduce Specific Fuel Type Stock:
				  if(tempFuelType != null) {
					  if(FTControl.checkFuelStock(tempFuelType).booleanValue())
						  OFSControl.createNewOrderFromFuelSupplier(tempFuelType);
				  }
				  return;
			  }
			  else if (msg instanceof Customer) {
				  Customer customer=(Customer)msg;
				  User user=(User)msg;
				  int userID = user.createNewAddSqlStatementUser(sqlcontrol.getConnection());
				  int custID = customer.createNewAddSqlStatement(sqlcontrol.getConnection());
				  if(userID == -1 || custID==-1) client.sendToClient(false);
				  else {
					  client.sendToClient(true);
				  }
				  return;
			  }
			  else if (msg instanceof Car) {
				  Car car=(Car)msg;
				  int carID = car.createNewAddSqlStatement(sqlcontrol.getConnection());
				  if(carID == -1) client.sendToClient(false);
				  else {
					  client.sendToClient(true);
				  }
				  return;
			  }
		  }
	  }
  }
  public saleTemplateDBController getSaleTemplateControler() {
		return this.saleTempControl;
	}
	public void setSaleTemplateController(saleTemplateDBController saleControl) {
		this.saleTempControl=saleControl;
	}
  public HomeFuelOrderDBController getHFOControl() {
		return HFOControl;
	}

	public void setHFOControl(HomeFuelOrderDBController hFOControl) {
		HFOControl = hFOControl;
	}
	  public CarDBController getCarControl() {
			return CarControl;
		}

		public void setCarControl(CarDBController carControl) {
			CarControl = carControl;
		}

	public OrderDBController getOrderControl() {
		return OrderControl;
	}

	public void setOrderControl(OrderDBController orderControl) {
		OrderControl = orderControl;
	}

	public FuelTypesDBController getFTControl() {
		return FTControl;
	}

	public void setFTControl(FuelTypesDBController fTControl) {
		FTControl = fTControl;
	}

	public EmployeeDBController getEmployeeControl() {
		return EmployeeControl;
	}

	public void setEmployeeControl(EmployeeDBController employeeControl) {
		EmployeeControl = employeeControl;
	}

	public OrderFromSupplierDBController getOFSControl() {
		return OFSControl;
	}

	public void setOFSControl(OrderFromSupplierDBController oFSControl) {
		OFSControl = oFSControl;
	}

public UserController getUsercontrol() {
		return usercontrol;
	}

	public void setUsercontrol(UserController usercontrol) {
		this.usercontrol = usercontrol;
	}

	public sqlController getSqlcontrol() {
		return sqlcontrol;
	}

	public void setSqlcontrol(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
	}

protected void serverStarted()
  {
    System.out.println("Server listening for connections on port " + getPort());
  }
  


  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }
  private void init() {
	  FTControl = new FuelTypesDBController(sqlcontrol);
	  OrderControl = new OrderDBController(sqlcontrol, this);
	  HFOControl = new HomeFuelOrderDBController(sqlcontrol, this);
	  EmployeeControl = new EmployeeDBController(sqlcontrol);
	  OFSControl = new OrderFromSupplierDBController(sqlcontrol, this);
	  ReqControl = new RequestDBController(sqlcontrol, this);
	  CarControl = new CarDBController(sqlcontrol, this);
	  FTTControl = new FuelTypeTempDBController(sqlcontrol);
	  FCController = new FuelCompanyDBController(sqlcontrol);
	  ASControl = new AnalyticSystemDBController(sqlcontrol,this);
	  saleTempControl=new saleTemplateDBController(sqlcontrol);
	    //////////////////////////////////////////////////////
	    //			Initialize Server Lists & Variables		//
	    //						On start up					//
	    //////////////////////////////////////////////////////
	    FTControl.initializeList();
	    OrderControl.initializeList();
	    HFOControl.initializeList();
	    EmployeeControl.initializeList();
	    OFSControl.initializeList();
	    CarControl.initializeList();
	    FCController.initializeList();
	    ASControl.initializeList();
	    //FTTControl.initializeList();
	    saleTempControl.initializeList();
  }
  public AnalyticSystemDBController getASControl() {
	return ASControl;
}

public FuelCompanyDBController getFCController() {
	return FCController;
}

public void setFCController(FuelCompanyDBController fCController) {
	FCController = fCController;
}

public static void main(String[] args) 
  {
    int port = 0;
    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT;
    }
    EchoServer sv = new EchoServer(port);
    try 
    {
      sv.listen();
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    sv.sqlcontrol = new sqlController();
    sv.init();
  }
}
//End of EchoServer class
