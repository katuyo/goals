package com.juext.asset.goals.mapper;

import com.juext.asset.goals.entity.AccountEntity;
import com.juext.asset.goals.query.AccountCriteria;
import org.apache.ibatis.annotations.*;
import org.featx.spec.model.PageRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:49
 */
@Mapper
public interface AccountMapper {

    String COLUMNS = "t.id, t.code, t.name, t.type, t.inventory, t.comment, t.deleted, t.created_at, t.updated_at";
    String PAGE_CRITERIA = "<if test=\"query.id != null\">and t.id = #{query.id}</if>" +
            "<if test=\"query.code != null\">and t.code = #{query.code}</if>" +
            "<if test=\"query.name != null\">and t.name = #{query.name}</if>" +
            "<if test=\"query.type != null\">and t.type = #{query.type}</if>" +
            "<if test=\"query.inventoryMax != null\">and t.inventory &lt;= #{query.inventoryMax}</if>" +
            "<if test=\"query.inventoryMin != null\">and t.inventory &gt;= #{query.inventoryMin}</if>" +
            "<if test=\"query.comment != null\">and t.comment = #{query.comment}</if>";

    @Insert({"insert into t_account(code, inventory, comment) ",
            "values(#{entity.code}, #{entity.inventory}, #{entity.comment})"})
    @SelectKey(keyProperty = "entity.id", before = false, resultType = Long.class,
            statement = "select t.id from t_account t where t.code=#{entity.code}")
    int insert(@Param("entity") AccountEntity accountEntity);

    @Update({"<script>update t_account <set> name = #{entity.name}, type = #{entity.type},",
            "inventory = #{entity.inventory}, comment = #{entity.comment},",
            "</set> where code = #{entity.code} and deleted = 0</script>"})
    int upsert(@Param("entity") AccountEntity accountEntity);

    @Update({"<script>update t_account <set> ",
            "<if test=\"entity.name != null\"> name = #{entity.name},</if>",
            "<if test=\"entity.type != null\"> type = #{entity.type},</if>",
            "<if test=\"entity.inventory != null\"> inventory = #{entity.inventory},</if>",
            "<if test=\"entity.comment != null and entity.comment != ''\"> comment = #{entity.comment},</if>",
            "</set> where code = #{entity.code} and deleted = 0",
            "</script> "})
    int update(@Param("entity") AccountEntity accountEntity);

    @Update({"update t_account set deleted = #{deleted} where code = #{code}"})
    int delete(@Param("code") String code, @Param("deleted") Boolean delete);

    @Select({"select", COLUMNS, " from t_account t where t.deleted = 0 and t.code = #{code} limit 1"})
    AccountEntity selectByCode(@Param("code") String code);

    @Select({"<script>select ", COLUMNS, " from t_account t where t.deleted = 0 and t.code in ",
            "<foreach collection='codes' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "</script>"})
    List<AccountEntity> selectByCodes(@Param("codes") List<String> codes);

    @Select({"<script>select", COLUMNS, "from t_account t where t.deleted = 0 ",
            PAGE_CRITERIA, "order by t.id limit #{page.offset}, #{page.size}",
            "</script>"})
    List<AccountEntity> selectByPage(@Param("query") AccountCriteria criteria, @Param("page") PageRequest page);

    @Select({"<script>select count(1) from t_account t where t.deleted = 0 ",
            PAGE_CRITERIA, "</script>"})
    long countByQuery(@Param("query") AccountCriteria criteria);
}
