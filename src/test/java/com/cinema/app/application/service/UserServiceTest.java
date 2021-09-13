package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.UsersServiceException;
import com.cinema.app.domain.user.dto.CreateUpdateUserDto;
import com.cinema.app.domain.user.dto.CreateUserResponseDto;
import com.cinema.app.domain.user.dto.GetUserDto;
import com.cinema.app.domain.user.type.Gender;
import com.cinema.app.domain.user.type.Role;
import com.cinema.app.infrastructure.persistence.dao.UserEntityDao;
import com.cinema.app.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class UserServiceTest {

    @Mock
    private UserEntityDao userEntityDao;

    @InjectMocks
    private UsersService usersService;

    @Test
    @DisplayName("when the username already exists in database")
    public void test1() {

        var id = 5L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userToInsert = CreateUpdateUserDto.builder()
                .username(username)
                .password(password)
                .passwordConfirmation(password)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var userEntity = UserEntity.builder()
                .username(username)
                .build();

        when(userEntityDao.findByUsername(username))
                .thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> usersService.createUser(userToInsert))
                .isInstanceOf(UsersServiceException.class)
                .hasMessageContaining("is already present in database");

    }

    @Test
    @DisplayName("when mail already exists in database")
    public void test2() {

        var id = 5L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userToInsert = CreateUpdateUserDto.builder()
                .username(username)
                .password(password)
                .passwordConfirmation(password)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var userEntity = UserEntity.builder()
                .username("aaaae")
                .mail(mail)
                .build();

        when(userEntityDao.findByMail(mail))
                .thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> usersService.createUser(userToInsert))
                .isInstanceOf(UsersServiceException.class)
                .hasMessageContaining("is already present in database");

    }

    @Test
    @DisplayName("when creating user is correct")
    public void test3() {

        var id = 5L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userToInsert = CreateUpdateUserDto.builder()
                .username(username)
                .password(password)
                .passwordConfirmation(password)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .id(id)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var expectedResponse = CreateUserResponseDto.builder()
                .id(id)
                .username(username)
                .build();

        when(userEntityDao.findByMail(mail))
                .thenReturn(Optional.empty());

        when(userEntityDao.findByUsername(username))
                .thenReturn(Optional.empty());

        when(userEntityDao.save(any()))
                .thenReturn(Optional.of(userEntity));

        assertThat(usersService.createUser(userToInsert))
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("when user is updated")
    public void test4() {

        var id = 5L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userToUpdate = CreateUpdateUserDto.builder()
                .username(username)
                .password(password)
                .passwordConfirmation(password)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var userEntity = userToUpdate.toUser().withCreationDate(LocalDate.now()).toEntity();

        var expectedDto = GetUserDto.builder()
                .username(username)
                .mail(mail)
                .name(name)
                .role(role)
                .creationDate(LocalDate.now())
                .birthDate(birtDate)
                .gender(gender)
                .build();

        when(userEntityDao.findById(id))
                .thenReturn(Optional.of(userEntity));


        when(userEntityDao.update(id, userEntity))
                .thenReturn(Optional.of(userEntity));

        assertThat(usersService.updateUser(id, userToUpdate))
                .isEqualTo(expectedDto);


    }

    @Test
    @DisplayName("when user is deleted")
    public void test5() {

        var id = 5L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .id(id)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var deletedUser = GetUserDto.builder()
                .id(id)
                .username(username)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        when(userEntityDao.deleteById(id))
                .thenReturn(Optional.of(userEntity));

        assertThat(usersService.deleteUser(id))
                .isEqualTo(deletedUser);

    }

    @Test
    @DisplayName("when user is searched by username")
    public void test6() {

        var id = 3L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .id(id)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var expectedUser = GetUserDto.builder()
                .username(username)
                .id(id)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        when(userEntityDao.findByUsername(username))
                .thenReturn(Optional.of(userEntity));

        assertThat(usersService.findByUsername(username))
                .isEqualTo(expectedUser);

    }

    @Test
    @DisplayName("when user is searched by mail")
    public void test7() {

        var id = 3L;
        var username = "Alfred";
        var mail = "alfred@alfredo.pl";
        var password = "123456";
        var name = "Alfredo Bueno";
        var role = Role.USER;
        var birtDate = LocalDate.now().minusYears(30);
        var gender = Gender.MALE;

        var userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .id(id)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        var expectedUser = GetUserDto.builder()
                .username(username)
                .id(id)
                .mail(mail)
                .name(name)
                .role(role)
                .birthDate(birtDate)
                .gender(gender)
                .build();

        when(userEntityDao.findByMail(mail))
                .thenReturn(Optional.of(userEntity));

        assertThat(usersService.findByMail(mail))
                .isEqualTo(expectedUser);

    }


}
