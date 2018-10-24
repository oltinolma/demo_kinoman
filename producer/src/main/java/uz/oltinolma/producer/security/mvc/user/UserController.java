package uz.oltinolma.producer.security.mvc.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.security.mvc.user.service.UserService;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.model.UserContext;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employee")
@Api(value = "demo_kinoman", description = "Foydalanuvchilar bilan bog'liq operatsiyalar")
public class UserController {
    @Autowired
    @Qualifier("userServiceH2Impl")
    private UserService userService;
    @Autowired
    private UserService userServicePostgresImpl;

    @PreAuthorize("@SecurityPermission.hasPermission('user.info')")
    @ApiOperation(value = "Login orqali foydalanuvchi ma'lumotlarini olish", response = User.class, responseContainer = "List")
    @GetMapping(value = "/getEmployeeInfoByLogin", produces = "application/json")
    public List list() {
        return userService.getEmployeeInfoByLogin(getLogin());
    }

    @PreAuthorize("@SecurityPermission.hasPermission('user.info')")
    @ApiOperation(value = "Mavjud foydalanuvchilar ro'yxatini ko'rish", response = User.class, responseContainer = "List")
    @GetMapping(value = "/list", produces = "application/json")
    public List getList() {
        return userService.list();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('user.update')")
    @ApiOperation(value = "ID orqali foydalanuvchini olish", response = User.class)
    @GetMapping(value = "/{id}", produces = "application/json")
    public User get(@PathVariable UUID id) {
        return userService.get(id);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('user.update')")
    @ApiOperation(value = "Foydalanuvchini tahrirlash", response = BaseResponse.class)
    @PutMapping(consumes = "application/json", produces = "application/json")
    public BaseResponse update(@Validated @RequestBody User employee) {
        return userServicePostgresImpl.update(employee);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('user.insert')")
    @ApiOperation(value = "Foydalanuvchi qo'shish", response = BaseResponse.class)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public BaseResponse insert(@Validated @RequestBody User employee) {
        return userServicePostgresImpl.insert(employee);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('user.delete')")
    @ApiOperation(value = "Foydalanuvchini o'chirish", response = BaseResponse.class)
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public BaseResponse delete(@PathVariable("id") int id) {
        return userServicePostgresImpl.delete(id);
    }

    @RequestMapping(value = "/myinfo", produces = "application/json")
    public String getMyInfo() {
        User employee = userService.findByLogin(getLogin());
        String res = "{\"status\": 200,\t\"name\": \"" + employee.getName() + "\", \"role\":\"" + employee.getRole()  + "\"}";
        return res;
    }

    public String getLogin() {
        String login;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserContext) {
            login = ((UserContext) principal).getLogin();
        } else {
            login = principal.toString();
        }
        return login;
    }
}
