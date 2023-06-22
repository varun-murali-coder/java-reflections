package security.annotations;

import security.annotations.Annotations.MethodOperations;
import security.annotations.Annotations.Permissions;

@Permissions(role=Role.CLERK,allowed=OperationType.READ)
@Permissions(role=Role.SUPPORT_ENGINEER,allowed= {OperationType.READ,OperationType.WRITE,OperationType.DELETE})
@Permissions(role=Role.MANAGER,allowed={OperationType.READ,OperationType.WRITE})
public class CompanyDataSource{
	private User user;
	public CompanyDataSource(User user) {
		this.user = user;
	}
	@MethodOperations(OperationType.READ)
	public String readAllAccounts() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		PermissionCheck.checkPermissions(this, "readAllAccounts");
		return "Read all accounts data from database";
	}
	@MethodOperations(OperationType.READ)
	public String accountSummary() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		PermissionCheck.checkPermissions(this, "accountSummary");

		return "Read all account summary from database";
	}
	@MethodOperations({OperationType.READ,OperationType.WRITE})
	public String updateAccount() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		PermissionCheck.checkPermissions(this, "updateAccount");

		return "updated account details";
	}
	
	@MethodOperations(OperationType.DELETE)
	public String deleteAccount() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		PermissionCheck.checkPermissions(this, "deleteAccount");

		return "account deleted successfully";
	}
	
	

}
