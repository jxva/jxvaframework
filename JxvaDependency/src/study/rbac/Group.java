package study.rbac;

public interface Group {
	
	public Integer getId();

	public String getName();

	public String getDescription();

	public User[] getUsers();

	public Group[] getChilds();

	public Group[] getParents();

	public Role[] getRoles();

	public boolean isMember(Group group);

	public boolean isBelong(Group group);

}
