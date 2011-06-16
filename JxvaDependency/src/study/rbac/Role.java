package study.rbac;

public interface Role {
	
	public Integer getId();

	public String getName();

	public String getDescription();

	public User[] getUsers();

	public Group[] getGroups();

	public Role[] getChilds();
	
	public Role[] getParents();

	public boolean isMember(Role role);

	public boolean isBelong(Role role);
}
