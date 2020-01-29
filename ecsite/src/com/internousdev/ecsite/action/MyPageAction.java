package com.internousdev.ecsite.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.internousdev.ecsite.dao.MyPageDAO;
import com.internousdev.ecsite.dto.MyPageDTO;


public class MyPageAction extends ActionSupport implements SessionAware {
	public Map<String,Object> session;
	private MyPageDAO myPageDAO=new MyPageDAO();
	private List<MyPageDTO> myPageList=new ArrayList<MyPageDTO>();
	private String deleteFlg;
	private String message;

	public String execute() throws SQLException {
		if(!session.containsKey("login_user_id")){
			return ERROR;
		}
		if(deleteFlg ==null){
			String item_transaction_id =session.get("id").toString();
			String user_master_id = session.get("login_user_id").toString();
			myPageList = myPageDAO.getMyPageUserInfo(item_transaction_id,user_master_id);
		}else if(deleteFlg.equals("1")){
			delete();
		}
		String result=SUCCESS;
		return result;
	}
	public void delete() throws SQLException {
		String item_transaction_id =session.get("id").toString();
		String user_master_id = session.get("login_user_id").toString();

		int res = myPageDAO.buyItemHistoryDelete(item_transaction_id,user_master_id);

		if(res>0){
			myPageList = null;
			setMessage("商品情報を正しく削除しました。");
		}else if(res==0){
			setMessage("商品情報の削除に失敗しました。");
		}
	}

	public void setDeleteFlg(String deleteFlg){
		this.deleteFlg =deleteFlg;
	}
	@Override
	public void setSession(Map<String,Object>session){
		this.session=session;
	}
	public List<MyPageDTO>getMyPageList(){
		return this.myPageList;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message){
		this.message=message;
	}

}