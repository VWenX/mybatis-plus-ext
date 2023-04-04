package io.github.vwenx.mpext;


import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.github.vwenx.mpext.tooltik.PageConverter;
import io.github.vwenx.mpext.tooltik.PageQueryHelper;
import io.github.vwenx.mpext.entity.User;
import io.github.vwenx.mpext.mapper.UserMapper;
import io.github.vwenx.mpext.param.UserPageParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 测试表结构见对应Mapper的xml文件
 */
@Slf4j
public class DBTest extends MybatisPlusExtTestApplicationTests {

    @Resource
    UserMapper userMapper;


    @Test
    public void insert(){
        // 软删除所有数据
        userMapper.softDeleteBy(qw -> {});

        // 插入
        User[] users = {
                User.builder().code("001").name("张三").age(10).deletedAt(0L).build(),
                User.builder().code("002").name("李四").age(16).deletedAt(0L).build(),
                User.builder().code("003").name("王五").age(18).deletedAt(0L).build(),
                User.builder().code("004").name("陈柳").age(3).deletedAt(0L).build(),
        };
        int batchInsert = userMapper.batchInsert(Arrays.asList(users));
        log.info("批量插入: {}", batchInsert);

        Long count = userMapper.selectCount(userMapper.queryWrapper());
        log.info("插入后 count: {}", count);
    }

    @Test
    public void query(){

        // 大于6岁的用户名称
        List<String> usersAgeGt6 = userMapper.querySingleFiled(User::getName, qw -> qw.gt(User::getAge, 6));
        log.info("大于6岁的用户名称: {}", usersAgeGt6);

        // 查询一批用户编号对应的名称
        String[] codes = {"001", "003", "004"};
        Map<String, String> codeNameMap = userMapper.queryMappingVal(User::getCode, Arrays.asList(codes), User::getName);
        log.info("查询一批用户编号对应的名称: {}", codeNameMap);

    }

    @Test
    public void page(){
        UserPageParam param = new UserPageParam();
        param.setMinAge(10);
        param.setPageNo(1);
        param.setPageSize(2);
        List<User> userPage = PageQueryHelper.listOrPage(param, p ->
                userMapper.query(qw -> qw.ge(p.getMinAge() != null, User::getAge, p.getMinAge()))
        );
        log.info("查询分页: 返回类型:{} \r\n ---- {}", userPage.getClass().getName(), userPage);

        param.setPageSize(null);
        param.setPageNo(null);
        List<User> users = PageQueryHelper.listOrPage(param, p ->
                userMapper.query(qw -> qw.ge(p.getMinAge() != null, User::getAge, p.getMinAge()))
        );
        log.info("不分页: 返回类型:{} \r\n ---- {}", users.getClass().getName(), users);

        // 均可使用PageConverter转换到统一返回结构
        PageDTO<User> userPageDTO = PageConverter.toMpPage(userPage);
        PageDTO<User> userPageDTO1 = PageConverter.toMpPage(users);
    }

}
