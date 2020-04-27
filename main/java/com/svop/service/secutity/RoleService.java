package com.svop.service.secutity;

import com.svop.View.Auth.PermissionsView;
import com.svop.View.Auth.RolePermissionsView;
import com.svop.tables.Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    private @Autowired
    RolePermissionRepository rolePermissionRepository;
    private @Autowired
    RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;
    private static final Logger logger= LoggerFactory.getLogger(RoleService.class);
    public void savePermissionsForRole(RolePermissionsView rolePermissionsView)
    {
        logger.info("Начало сохранения ролей и разрешений");
        if (rolePermissionsView==null) return;
        if (rolePermissionsView.getList()==null) return;
        Optional<Role> roleOptional=roleRepository.findById(rolePermissionsView.getIdRole());
        if (!roleOptional.isPresent()) return;

        List<RolePermissions> rolePermissionsList=new ArrayList<>();

        Role role=roleOptional.get();
        for(PermissionsView permissionsView:rolePermissionsView.getList()) {
            Optional<Permissions> permissions=permissionRepository.findById(permissionsView.getPermissionId());
           RolePermissions rolePermissions =
                    rolePermissionRepository.findByRoleAndPermission(role,permissions.get());

            //Если такой записи нет тогда
            if (rolePermissions==null){
                logger.info("Запись не найдена, будет создан ее заглушка");
                //Нужно ли ее создавать
                rolePermissions=new RolePermissions();
                rolePermissions.setRole(role);
                if (!permissions.isPresent()) throw new RuntimeException();
                rolePermissions.setPermission(permissions.get());
            }else
            {
                logger.info("Запись  найдена");
            }
            if (permissionsView.getWriteAccess()) {
                logger.info("Разрешение на запись "+permissionsView.getPermissionId()+" "+rolePermissions.getId());
                rolePermissions.setAccess(Access.Write);
                rolePermissionsList.add(rolePermissions);
            } else if (permissionsView.getReadAccess()) {
                logger.info("Разрешение на чтение "+permissionsView.getPermissionId()+" "+rolePermissions.getId());
                rolePermissions.setAccess(Access.Read);
                rolePermissionsList.add(rolePermissions);
            }
            else {

                //ПРоверим, если запись присутствует
                if (rolePermissions.getId()!=null)
                {
                    logger.info("Удаление "+rolePermissions.getId());
                    rolePermissionRepository.delete(rolePermissions);
                }

            }
        }
        logger.info("Сохранение");
        rolePermissionRepository.saveAll(rolePermissionsList);
        logger.info("Конец сохранения ролей и разрешений");
    }

    /**
     * Для вывода на модальное окно. ПОлучение всех разрешений + с учетом роли
     *  @param id
     * @return
     */
    public List<PermissionsView> getPermissionsByRole(@NotNull Integer id)
    {
        List<RolePermissions> rolePermissions=rolePermissionRepository.findAllByRoleId(id);
        List<Permissions> permissions=permissionRepository.findAll();
        List<PermissionsView> response=new ArrayList<>(permissions.size());
        for(Permissions item:permissions)
        {
            RolePermissions rp=findPermission(rolePermissions,item);
            if (rp!=null)
            {
                response.add(new PermissionsView(rp));
            }else response.add(new PermissionsView(item));
        }
        return response;
    }
    private RolePermissions findPermission(@NotNull List<RolePermissions> rolePermissions,Permissions permission) {
        for (RolePermissions item : rolePermissions)
        {
            if (item.getPermission().getId().equals(permission.getId()))
            return item;
        }
        return null;
    }

}
