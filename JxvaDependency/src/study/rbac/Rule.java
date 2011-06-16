package study.rbac;

public interface Rule {

	public boolean pass(Permission permission,Role role);
}
