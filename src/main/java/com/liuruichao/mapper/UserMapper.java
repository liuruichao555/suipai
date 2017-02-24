package com.liuruichao.mapper;

import com.liuruichao.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * UserMapper
 * 
 * @author liuruichao
 * Created on 2016-01-15 10:30
 */
@Repository
public interface UserMapper {
    @Insert("INSERT INTO SUIPAI_USER VALUES(null, #{mobile}, #{userName}, #{password}, #{avatarUrl}, #{gender}, #{age}, #{status}, #{photoCount}, #{collectCount}, #{followCount}, #{fansCount}, #{registerTime})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "USER_ID")
    int saveUser(User user);

    @Select("SELECT * FROM SUIPAI_USER WHERE USER_ID = #{userId}")
    User getUserById(@Param("userId") int userId);

    @Select("SELECT * FROM SUIPAI_USER WHERE MOBILE = #{mobile}")
    User getUserByMobile(@Param("mobile") String mobile);

    @Select("SELECT COUNT(1) FROM SUIPAI_USER WHERE MOBILE = #{mobile}")
    int getUserNum(String mobile);
}
