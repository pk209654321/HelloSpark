package dao.factory;


import dao.*;
import dao.impl.*;

/**
 * DAO工厂类
 * @author Administrator
 *
 */
public class DAOFactory {


	public static ITaskDAO getTaskDAO() {
		return new TaskDAOImpl();
	}

	public static IInfoDao getInfoDao() {
		return new InfoDaoImpl();
	}

	public static ITCollectDao getTCollectDao(){
		return new TCollectDaoImpl();
	}

	public static ITVideoplayDao getVideoPlayDao(){
		return new TVideoplayDaoImpl();
	}

	public static ITSpecialistDao getTSpecialistDao(){
		return new TSpecialistDaoImpl();
	}

	public static ITSingleUserDao getTSingleUserDao(){
		return new TSingleUserDaoImpl();
	}

	public static IUserActionInfoDao getUserActionInfoDao(){
		return new UserActionInfoDaoImpl();
	}

	public static IAiAccountResigterInfoDao getAccountResigterInfoDao(){
		return new AiAccountResigterInfoDaoImpl();
	}

	public static IAccountDetailDao getAccountDetailDao(){
		return new AccountDetailDaoImpl();
	}

	public static IActivitiesOperationDao getActivitiesOperationDao(){
		return new ActivitiesOperationDaoImpl();
	}

	public static IScreeningUserInfoDao getScreeningUserDao(){
		return new ScreeningUserInfoDaoImpl();
	}

	public static IScreeningAdInfoDao getScreeningAdInfoDao(){
		return new ScreeningAdInfoDaoImpl();
	}

	public static IScreeningCourseInfoDao getScreeningCourseInfoDao(){
		return new ScreeningCourseInfoDaoImpl();
	}

}
