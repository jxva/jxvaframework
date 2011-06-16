package study.rbac;

public interface User {

	public Integer getId();

	public String getName();

	public String getDescription();

	public Group[] getGroups();

	public Role[] getRoles();
}
