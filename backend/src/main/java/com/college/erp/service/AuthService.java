package com.college.erp.service;
import com.college.erp.model.entity.UserAccount; import com.college.erp.repository.UserRepository; import com.college.erp.util.PasswordUtil; import org.springframework.stereotype.Service; import java.util.Map;
@Service public class AuthService { private final UserRepository repo; public AuthService(UserRepository r){repo=r;}
 public Map<String,Object> login(String username,String password,String requestedRole){UserAccount u=repo.findByUsername(username).orElse(null); if(u==null||!PasswordUtil.verify(password,u.getPasswordHash())) throw new IllegalArgumentException("Invalid username or password"); if(!u.getRole().equalsIgnoreCase(requestedRole)) throw new IllegalArgumentException("Selected role does not match this account"); return Map.of("success",true,"username",u.getUsername(),"fullName",u.getFullName(),"role",u.getRole(),"email",u.getEmail()==null?"":u.getEmail(),"department",u.getDepartment()==null?"":u.getDepartment());}
}
