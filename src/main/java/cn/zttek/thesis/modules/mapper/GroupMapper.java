package cn.zttek.thesis.modules.mapper;

import cn.zttek.thesis.common.base.BaseMapper;
import cn.zttek.thesis.modules.enums.DefenseStatus;
import cn.zttek.thesis.modules.model.Group;
import cn.zttek.thesis.modules.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * 查询未分组的教师
     * @param projid
     * @param teacher
     * @return
     * @throws Exception
     */
    List<User> listNoGroupTeachers(@Param("projid") Long projid, @Param("teacher") String teacher) throws Exception;
    /**
     * 查询未分组的学生
     * @param projid
     * @param student
     * @param status
     * @return
     * @throws Exception
     */
    List<User> listNoGroupStudents(@Param("projid") Long projid, @Param("student") String student, @Param("status") DefenseStatus status) throws Exception;

}