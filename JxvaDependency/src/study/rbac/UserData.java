package study.rbac;

public class UserData {
	
	private java.lang.Integer userid;
	private java.lang.String username;
	private java.lang.String userroles;
	private java.lang.String usergroups;
	private java.lang.String userprivileges;
	private java.lang.String sessionid;

	public java.lang.String getSessionid() {
		return sessionid;
	}

	public void setSessionid(java.lang.String sessionid) {
		this.sessionid = sessionid;
	}

	public java.lang.Integer getUserid() {
		return userid;
	}

	public void setUserid(java.lang.Integer userid) {
		this.userid = userid;
	}

	public java.lang.String getUsername() {
		return username;
	}

	public void setUsername(java.lang.String username) {
		this.username = username;
	}

	public java.lang.String getUserroles() {
		return userroles;
	}

	public void setUserroles(java.lang.String userroles) {
		this.userroles = userroles;
	}

	public java.lang.String getUsergroups() {
		return usergroups;
	}

	public void setUsergroups(java.lang.String usergroups) {
		this.usergroups = usergroups;
	}

	public java.lang.String getUserprivileges() {
		return userprivileges;
	}

	public void setUserprivileges(java.lang.String userprivileges) {
		this.userprivileges = userprivileges;
	}
}
