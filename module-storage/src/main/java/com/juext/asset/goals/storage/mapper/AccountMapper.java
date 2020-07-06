package com.juext.shop.base.storage.mapper;

import org.apache.ibatis.annotations.*;
import org.featx.spec.model.PageRequest;
import com.juext.shop.base.storage.entity.AccountEntity;
import com.juext.shop.base.storage.query.AccountCriteria;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:49
 */
@Mapper
public interface AccountMapper {

    String COLUMUS = "id, code, inventory, comment, deleted, created_at, updated_at";

    @Insert({"insert into t_account_module(code, inventory, comment) ",
            "value(#{entity.code}, #{entity.inventory}, #{entity.comment})"})
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "entity.id", before = false, resultType = Long.class)
    void insert(@Param("entity") AccountEntity AccountEntity);

    @Update({"update t_account_module set inventory = #{entity.inventory},",
            " comment = #{entity.comment}, ",
            " where code = #{entity.code} and deleted = 0"})
    void upsert(@Param("entity") AccountEntity AccountEntity);

    @Update({"<script>update t_account_module set ",
            "<if test=\"entity.status != null\"> status = #{entity.status},</if>",
            "<if test=\"entity.comment != null and entity.comment != ''\"> comment = #{entity.comment}, </if>",
            " where code = #{entity.code} and deleted = 0",
            "</script> "})
//    @SelectKey(statement = "select updated_at from t_Account_module where id = (SELECT LAST_INSERT_ID())")
    void update(@Param("entity") AccountEntity AccountEntity);

    @Update({"update t_Account_module set deleted = #{deleted} where code = #{code}"})
    void delete(@Param("code") String code, @Param("deleted") Boolean delete);

    @Select({"select ", COLUMUS, " from t_Account_module where  deleted = 0 and code = #{code} limit 1"})
    AccountEntity selectByCode(@Param("code") String code);

    @Select({"<script>select ", COLUMUS, " from t_Account_module where deleted = 0 and code in ",
            "<foreach collection='codes' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "</script>"})
    List<AccountEntity> selectByCodes(@Param("codes") List<String> codes);

    @Select({"select ", COLUMUS, " from t_Account_module where deleted = 0 ",
            "",
            "order by id limit #{page.offset}, #{page.size}"})
    List<AccountEntity> selectByPage(@Param("query") AccountCriteria criteria, @Param("page") PageRequest page);

    @Select({"select count(1) from t_Account_module where deleted = 0 ",
            ""})
    long countByQuery(@Param("query") AccountCriteria criteria);
}
