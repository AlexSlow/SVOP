package com.svop.service.secutity;

import com.svop.controllers.API.DTO.UserDto;
import com.svop.tables.Users.Role;
import com.svop.tables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDaoService {
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    public  void  update(@NotNull List<com.svop.controllers.API.DTO.UserDto> userDtoList){
        userService.save(usersListFactory(userDtoList));
    }
    public List<User> usersListFactory(@NotNull List<com.svop.controllers.API.DTO.UserDto> userDtoList)
    {
        List<User> users=getUsers(getIdByList(userDtoList));
        System.out.println(users);
        userDtoList.forEach(userDto -> {
            if ((users!=null)&&(userDto.getId()!=null))
            {
                User user=findInCollectionById(users,userDto.getId());
                if (user!=null)
                {
                    updateUser(user,userDto);
                }else {
                    User userNew=new User(userDto.getUsername(),userDto.getPassword(), Stream.of(roleService.getBaseRole()).collect(Collectors.toSet()));
                    users.add(userNew);
                }
            }else {
                User userNew=new User(userDto.getUsername(),userDto.getPassword(), Stream.of(roleService.getBaseRole()).collect(Collectors.toSet()));
                users.add(userNew);
            }


        });
        return users;
    }
    public List<com.svop.controllers.API.DTO.UserDto> usersDtoFactory(@NotNull Iterable<User> users)
    {
       List<com.svop.controllers.API.DTO.UserDto> userDtos=new ArrayList<>();
       users.forEach(usr->{
           userDtos.add(new UserDto(usr.getId(),usr.getUsername(),usr.getPassword()));});
        return userDtos;
    }
    private List<Integer> getIdByList(@NotNull List<com.svop.controllers.API.DTO.UserDto> userDtoList)
    {
        return userDtoList.stream().map(userDto ->userDto.getId()).collect(Collectors.toList());
    }
    private List<User> getUsers(@NotNull List<Integer> id){
       return userService.find(id);
    }
    private User findInCollectionById( List<User> userList,Integer id){

        if(userList!=null)
        for(User user:userList ){if (user.getId().equals(id)) return user;}
        return null;
    }
    private void updateUser(User user, com.svop.controllers.API.DTO.UserDto userDto)
    {
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
    }
}
