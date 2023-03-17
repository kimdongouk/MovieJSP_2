package user;

import java.util.ArrayList;

import common.JDBConnect;

public class UserDAO extends JDBConnect{
	
	public ArrayList<User> search(String userName) {
		String SQL = "SELECT * FROM USER WHERE userName LIKE ?";
		ArrayList<User> userList = new ArrayList<User>();
		try {
			psmt = con.prepareStatement(SQL);
			psmt.setString(1, "%" + userName + "%");
			rs = psmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString(1));
				user.setUserAge(rs.getInt(2));
				user.setUserGender(rs.getString(3));
				user.setUserEmail(rs.getString(4));
				userList.add(user);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
}