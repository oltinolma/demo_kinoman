package uz.oltinolma.producer.security.mvc.rolepermissions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.security.common.BaseResponses;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
import uz.oltinolma.producer.security.mvc.role.Role;
import uz.oltinolma.producer.security.mvc.rolepermissions.service.RolesPermissionsService;

import java.util.List;

@RestController
@PreAuthorize("@SecurityPermission.hasPermission('role_permission')")
@Api(value = "demo_kinoman", description = "Huqularga mos ruhsatlar bilan bog'liq operatsiyalar")
@RequestMapping(value = "/rolesPermissions")
public class RolesPermissionsController {
    @Autowired
    private RolesPermissionsService rolesPermissionsServiceH2Impl;
    @Autowired
    private RolesPermissionsService rolesPermissionsServicePostgresImpl;
    @Autowired
    private BaseResponses baseResponses;

    @PreAuthorize("@SecurityPermission.hasPermission('rp.info')")
    @ApiOperation(value = "Huquqlarga mos ruhsatlar ro'yxatini olish", response = RolesPermissions.class, responseContainer = "List")
    @GetMapping(value = "/list", produces = "application/json")
    public List getList() {
        return rolesPermissionsServiceH2Impl.list();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('rp.info')")
    @ApiOperation(value = "Huquqlarga mos ruhsatlar juftligini, ruhsatlar sonini ro'yxatini olish", response = Role.class, responseContainer = "List")
    @GetMapping(value = "/count_list", produces = "application/json")
    public List getCountList() {
        return rolesPermissionsServiceH2Impl.countList();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('rp.info')")
    @ApiOperation(value = "Ma'lum ID lik huquqga mos ruhsatni olish", response = RolesPermissions.class)
    @GetMapping(value = "/{id}",consumes = "application/json", produces = "application/json")
    public RolesPermissions get(@PathVariable int id) {
        return rolesPermissionsServiceH2Impl.get(id);
    }

    @PreAuthorize("@SecurityPermission.hasPermission('rp.update')")
    @ApiOperation(value = "Ma'lum ID lik huquqga mos ruhsatni tahrirlash", response = BaseResponse.class)
    @PutMapping(consumes = "application/json", produces = "application/json")
    public BaseResponse update(@Validated @RequestBody RolesPermissions RolesPermissions) {
        return rolesPermissionsServicePostgresImpl.update(RolesPermissions);
    }


    @PreAuthorize("@SecurityPermission.hasPermission('rp.insert')")
    @ApiOperation(value = "Yangi huquqga mos ruhsat kiritish", response = BaseResponse.class)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public BaseResponse insert(@Validated @RequestBody RolesPermissions rolesPermissions) {
        rolesPermissionsServicePostgresImpl.insert(rolesPermissions);
        return baseResponses.successMessage();
    }

    @PreAuthorize("@SecurityPermission.hasPermission('rp.delete')")
    @ApiOperation(value = "Ma'lum ID lik huquqga mos ruhsatni o'chirish", response = BaseResponse.class)
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public BaseResponse delete(@PathVariable("id") int id) {
        return rolesPermissionsServicePostgresImpl.delete(id);
    }
}
