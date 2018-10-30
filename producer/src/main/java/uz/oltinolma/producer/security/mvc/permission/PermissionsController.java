package uz.oltinolma.producer.security.mvc.permission;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.model.UserContext;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.permission.service.PermissionService;

import java.util.List;
import java.util.Set;

@RestController
@Api(value = "demo_kinoman", description = "Operations on permissions")
@RequestMapping(value = "/permissions")
public class PermissionsController {
    @Autowired
    private PermissionService permissionServicePostgresImpl;
    @Autowired
    private PermissionService permissionServiceH2Impl;
    @Autowired
    private BaseResponses baseResponses;

    @PreAuthorize("@SecurityPermission.hasPermission('permission.info')")
    @ApiOperation(value = "Get list of all permissions", response = Permission.class, responseContainer = "List")
    @GetMapping(value = "/list", produces = "application/json")
    public List getList() {
        return permissionServiceH2Impl.list();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('permission.info')")
    @ApiOperation(value = "List of permission names for a particular user.", response = Permission.class, responseContainer = "List")
    @GetMapping(value = "/list/for/current/user", produces = "application/json")
    public Set<String> getListByLogin() {
        return permissionServiceH2Impl.getByLogin(getLogin());
    }

    @PreAuthorize("@SecurityPermission.hasPermission('permission.info')")
    @ApiOperation(value = "Get permission object by id", response = Permission.class)
    @GetMapping(value = "/{id}", produces = "application/json")
    public Permission getById(@PathVariable int id) {
        return permissionServiceH2Impl.get(id);
    }


    @PreAuthorize("@SecurityPermission.hasPermission('permission.update')")
    @ApiOperation(value = "Edit permission.", response = BaseResponse.class)
    @PutMapping(consumes = "application/json", produces = "application/json")
    public BaseResponse update(@Validated @RequestBody Permission permissions) {
        return permissionServicePostgresImpl.update(permissions);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('permission.insert')")
    @ApiOperation(value = "Yangi ruxsat kiritish", response = Permission.class)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public BaseResponse insert(@Validated @RequestBody Permission permissions) {
        permissionServicePostgresImpl.insert(permissions);
        return baseResponses.successMessage();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('permission.delete')")
    @ApiOperation(value = "Ma'lum ID lik ruxsatni o'chirish", response = BaseResponse.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public BaseResponse delete(@PathVariable("id") int id) {
        return permissionServicePostgresImpl.delete(id);
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
