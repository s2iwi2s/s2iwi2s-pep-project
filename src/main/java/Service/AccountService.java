package Service;

import java.sql.SQLException;

import DAO.AccountDao;
import Model.Account;

public class AccountService {

    private AccountDao accountDao;
    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public Account createAccount(Account account) throws Exception {
        if(account.getUsername() == null || account.getUsername().length() <= 0 || 
                account.getPassword() == null || account.getPassword().length() < 4){
            throw new Exception("create account error");
        }
        if(this.getAccountByUsername(account.getUsername()) != null) {
            throw new Exception("create account error");
        }

        return this.accountDao.createAccount(account);
    }
    
    public Account getAccountByUsername(String string) throws SQLException {
        return this.accountDao.getAccountByUsername(string);
    }

    public Account login(Account account) throws Exception {
        Account ret = this.accountDao.getAccountByUsername(account.getUsername());
        if(account.getPassword() == null || !account.getPassword().equals(ret.getPassword())) {
            throw new Exception("Account not found");
        }
        return ret;
    }

}
