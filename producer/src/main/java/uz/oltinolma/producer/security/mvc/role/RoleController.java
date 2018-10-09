package uz.oltinolma.producer.security.mvc.role;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.service.RoleService;

import java.util.List;

@RestController
@Api(value = "x-market", description = "Huquqlar bilan bog'liq operatsiyalar")
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleServiceH2Impl;
    @Autowired
    private RoleService roleServicePostgresImpl;


    @PreAuthorize("@SecurityPermission.hasPermission('role.update')")
    @ApiOperation(value = "Ma'lum ID lik huquqni olish", response = Role.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public Role get(@PathVariable int id) {
        return roleServiceH2Impl.get(id);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('role.update')")
    @ApiOperation(value = "Ma'lum ID lik huquqni tahrirlash", response = BaseResponse.class)
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public BaseResponse update(@Validated @RequestBody Role role) {
        return roleServicePostgresImpl.update(role);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('role.insert')")
    @ApiOperation(value = "Yangi huquq yaratish", response = BaseResponse.class)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public BaseResponse insert(@Validated @RequestBody Role role) {
        return roleServicePostgresImpl.insert(role);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('role.insert')")
    @ApiOperation(value = "Yangi huquq yaratish", response = BaseResponse.class)
    @RequestMapping(value = "/all", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public BaseResponse insertAll(@Validated @RequestBody List<Role> roles) {
        return roleServiceH2Impl.insertAll(roles);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('role.delete')")
    @ApiOperation(value = "Ma'lum ID lik huquqni o'chirish", response = BaseResponse.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
    public BaseResponse delete(@PathVariable("id") int id) {
        return roleServicePostgresImpl.delete(id);
    }

}
