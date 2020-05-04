package com.svop.service.secutity;

import com.svop.View.Auth.PermissionsView;
import com.svop.View.Auth.RolePermissionsView;
import com.svop.View.Auth.RoleView;
import com.svop.View.Auth.UserRoleView;
import com.svop.tables.Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private @Autowired
    RolePermissionRepository rolePermissionRepository;
    private @Autowired
    RoleRepository roleRepository;
    private @Autowired
    UserService userService;
    @Autowired private PermissionRepository permissionRepository;
    private static final Logger logger= LoggerFactory.getLogger(RoleServiceImpl.class);
    @Override
    public void savePermissionsForRole(RolePermissionsView rolePermissionsView)
    {
        logger.info("****Начало сохранения ролей и разрешений****");
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
        logger.info("****Конец сохранения ролей и разрешений****");
    }

    /**
     * Для вывода на модальное окно. ПОлучение всех разрешений + с учетом роли
     *  @param id
     * @return
     */
    @Override
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

    /**
     * Найти все роли по пользователю
     */
    @Override
    public List<RoleView> getAllRoleByUser(@NotNull Integer userId) {
        logger.info("********Начало получения ролей с учетом пользователя**********");
        List<Role> roles=roleRepository.findAll();
        List<RoleView> rolesView=new ArrayList<>(roles.size());
        User user;
        Set<Role> userRoles;
        try {
             user=userService.findById(userId);
            userRoles=user.getRoles();
        } catch (Exception e) {
            e.printStackTrace();
            return rolesView;
        }
        for(Role role:roles)
        {
            if (userRoles.contains(role))
            {
                logger.info("Получена роль пользователя с id "+role.getId());
                rolesView.add(new RoleView(role.getId(), role.getName(),true));
            }else{
                logger.info(" Роль пользователя отсутствует с id "+role.getId());
                rolesView.add(new RoleView(role.getId(), role.getName(),false));
            }

        }
        logger.info("*******Конец получения ролей с учетом пользователя**********");
        return rolesView;
    }

    @Override
    public void saveRolesForUser(UserRoleView userRoleView)
    {
        logger.info("*****Начало сохранения пользователей и ролей*****");
        if (userRoleView==null) return;
        if (userRoleView.getList()==null) return;
        User user;
        try {
            user=userService.findById(userRoleView.getIdUser());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        List<Integer> ids=new ArrayList<>(userRoleView.getList().size());
        for (RoleView roleView:userRoleView.getList())
        {
            if ((roleView.getId()!=null)&&(roleView.getActive().booleanValue()))
            {
                ids.add(roleView.getId());
            }

        }
        Set<Role> roles=roleRepository.findAllByIdIn(ids);
        userService.setRoles(user,roles);
        logger.info("*****Конец сохранения пользователей и ролей*****");
    }
}
