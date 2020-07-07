package com.juext.asset.goals.storage.mapper;

import com.juext.asset.goals.storage.entity.AccountEntity;
import com.juext.asset.goals.storage.query.AccountCriteria;
import org.apache.ibatis.annotations.*;
import org.featx.spec.model.PageRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:49
 */
@Mapper
public interface AccountMapper {

    String COLUMUS = "id, code, name, type, inventory, comment, deleted, created_at, updated_at";

    @Insert({"insert into t_account(code, inventory, comment) ",
            "values(#{entity.code}, #{entity.inventory}, #{entity.comment})"})
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "entity.id", before = false, resultType = Long.class)
    void insert(@Param("entity") AccountEntity AccountEntity);

    @Update({"update t_account set inventory = #{entity.inventory},",
            " comment = #{entity.comment}, ",
            " where code = #{entity.code} and deleted = 0"})
    void upsert(@Param("entity") AccountEntity AccountEntity);

    @Update({"<script>update t_account set ",
            "<if test=\"entity.status != null\"> status = #{entity.status},</if>",
            "<if test=\"entity.comment != null and entity.comment != ''\"> comment = #{entity.comment}, </if>",
            " where code = #{entity.code} and deleted = 0",
            "</script> "})
//    @SelectKey(statement = "select updated_at from t_Account where id = (SELECT LAST_INSERT_ID())")
    void update(@Param("entity") AccountEntity AccountEntity);

    @Update({"update t_account set deleted = #{deleted} where code = #{code}"})
    void delete(@Param("code") String code, @Param("deleted") Boolean delete);

    @Select({"select ", COLUMUS, " from t_account where  deleted = 0 and code = #{code} limit 1"})
    AccountEntity selectByCode(@Param("code") String code);

    @Select({"<script>select ", COLUMUS, " from t_account where deleted = 0 and code in ",
            "<foreach collection='codes' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "</script>"})
    List<AccountEntity> selectByCodes(@Param("codes") List<String> codes);

    @Select({"select ", COLUMUS, " from t_Account where deleted = 0 ",
            "",
            "order by id limit #{page.offset}, #{page.size}"})
    List<AccountEntity> selectByPage(@Param("query") AccountCriteria criteria, @Param("page") PageRequest page);

    @Select({"select count(1) from t_account where deleted = 0 ",
            ""})
    long countByQuery(@Param("query") AccountCriteria criteria);
}
