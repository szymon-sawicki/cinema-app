package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.TicketsServiceException;
import com.cinema.app.application.service.exception.UsersServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.domain.user.dto.GetUserDto;
import com.cinema.app.domain.user.dto.validator.CreateUpdateUserDtoValidator;
import com.cinema.app.infrastructure.persistence.dao.UserEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

/**
 * service used to manage users of application.
 * @Author Szymon Sawicki
 */

public class UsersService {

    private final UserEntityDao userEntityDao;

    /**
     * method used to creating new user. Username and mail must be unique
     * @param createUpdateUserDto user to create
     * @return created user
     */

    public GetUserDto createUser(CreateUpdateUserDto createUpdateUserDto) {
        Validator.validate(new CreateUpdateUserDtoValidator(),createUpdateUserDto);
        checkMailAndUsernameAvailability(createUpdateUserDto);

        if(!createUpdateUserDto.getPassword().equals(createUpdateUserDto.equals(createUpdateUserDto.getPasswordConfirmation()))) {
            throw new TicketsServiceException("password and confirmation doesn't match");
        }

        return userEntityDao.save(createUpdateUserDto
                        .toUser()
                        .withCreationDateToday()
                        .toEntity())
                .orElseThrow(() -> new UsersServiceException("cannot add new user"))
                .toUser()
                .toGetUserDto();

    }

    /**
     * method that updates existing user
     * @param userId id of user to be updated
     * @param createUpdateUserDto data to update
     * @return updated user
     */

    public GetUserDto updateUser(Long userId, CreateUpdateUserDto createUpdateUserDto) {
        if(userId == null) {
            throw new UsersServiceException("id is null");
        }
        if(userId <= 0) {
            throw new UsersServiceException("user's id is 0 or negative");
        }
        Validator.validate(new CreateUpdateUserDtoValidator(),createUpdateUserDto);

        return userEntityDao.update(userId,createUpdateUserDto.toUser().toEntity())
                .orElseThrow(() -> new UsersServiceException("cannot update user"))
                .toUser()
                .toGetUserDto();
    }

    /**
     * method used to delete user
     * @param userId id of user to be deleted
     * @return deleted user
     */

    GetUserDto deleteUser(Long userId) {
        if(userId == null) {
            throw new UsersServiceException("id is null");
        }
        if(userId <= 0) {
            throw new UsersServiceException("user's id is 0 or negative");
        }

        return userEntityDao.deleteById(userId)
                .orElseThrow(() -> new UsersServiceException("cannot delete user"))
                .toUser().toGetUserDto();
    }

    // private method checking availability of username and mail

    private void checkMailAndUsernameAvailability(CreateUpdateUserDto createUpdateUserDto) {
        var username = createUpdateUserDto.getUsername();
        var mail = createUpdateUserDto.getMail();
        if(userEntityDao.findByUsername(username).isPresent()) {
            throw new UsersServiceException("user " + username + " is already present in database");
        }
        if(userEntityDao.findByUsername(mail).isPresent()) {
            throw new UsersServiceException("user with mail " + mail + " is already present in database");
        }
    }

}
