package cn.net.colin.mapper.test;

import cn.net.colin.model.test.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by sxf on 2020-3-1.
 */
@Mapper
public interface UserMapper {
    public List<User> findUserList();
    int addUser(Map<String,Object> params);
}
