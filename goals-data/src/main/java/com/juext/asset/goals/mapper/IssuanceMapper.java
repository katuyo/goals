package com.juext.asset.goals.mapper;

import com.juext.asset.goals.entity.IssuanceEntity;
import com.juext.asset.goals.query.IssuanceCriteria;
import org.apache.ibatis.annotations.*;
import org.featx.spec.model.PageRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:49
 */
@Mapper
public interface IssuanceMapper {

    String COLUMNS = "t.id, t.code, t.name, t.type, t.amount, t.account_code, t.comment, t.deleted, t.created_at, t.updated_at";
    String PAGE_CRITERIA = "<if test=\"query.id != null\">and t.id = #{query.id}</if>" +
            "<if test=\"query.code != null\">and t.code = #{query.code}</if>" +
            "<if test=\"query.name != null\">and t.name = #{query.name}</if>" +
            "<if test=\"query.type != null\">and t.type = #{query.type}</if>" +
            "<if test=\"query.amountMax != null\">and t.amount &lt;= #{query.amountMax}</if>" +
            "<if test=\"query.amountMin != null\">and t.amount &gt;= #{query.amountMin}</if>" +
            "<if test=\"query.accountCode != null\">and t.account_code = #{query.accountCode}</if>" +
            "<if test=\"query.comment != null\">and t.comment = #{query.comment}</if>";

    @Insert({"insert into t_issuance(code, name, type, amount, account_code, comment) ",
            "values(#{entity.code}, #{entity.name}, #{entity.type}, #{entity.amount}, #{entity.accountCode}, ",
            "#{entity.comment})"})
    @SelectKey(keyProperty = "entity.id", before = false, resultType = Long.class,
            statement = "select t.id from t_issuance t where t.code=#{entity.code}")
    int insert(@Param("entity") IssuanceEntity issuanceEntity);

    @Update({"update t_issuance set name = #{entity.name},",
            " type = #{entity.type}, amount = #{entity.amount},",
            " account_code = #{entity.accountCode},",
            " comment = #{entity.comment} ",
            " where code = #{entity.code} and deleted = 0"})
    int upsert(@Param("entity") IssuanceEntity issuanceEntity);

    @Update({"<script>update t_issuance <set>",
            "<if test=\"entity.name != null and entity.name != ''\"> name = #{entity.name},</if>",
            "<if test=\"entity.type != null\"> type = #{entity.type},</if>",
            "<if test=\"entity.amount != null\"> amount = #{entity.amount},</if>",
            "<if test=\"entity.accountCode != null\"> account_code = #{entity.accountCode},</if>",
            "<if test=\"entity.comment != null and entity.comment != ''\"> comment = #{entity.comment}, </if>",
            "</set> where code = #{entity.code} and deleted = 0",
            "</script> "})
    int update(@Param("entity") IssuanceEntity issuanceEntity);

    @Update({"update t_issuance set deleted = #{deleted} where code = #{code}"})
    int delete(@Param("code") String code, @Param("deleted") Boolean delete);

    @Select({"select", COLUMNS, "from t_issuance t where t.deleted = 0 and t.code = #{code} limit 1"})
    IssuanceEntity selectByCode(@Param("code") String code);

    @Select({"<script>select", COLUMNS, "from t_issuance t where t.deleted = 0 and t.code in",
            "<foreach collection='codes' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "</script>"})
    List<IssuanceEntity> selectByCodes(@Param("codes") List<String> codes);

    @Select({"<script>select", COLUMNS, "from t_issuance t where t.deleted = 0 ",
            PAGE_CRITERIA, "order by t.id limit #{page.offset}, #{page.size}",
            "</script>"})
    List<IssuanceEntity> selectByPage(@Param("query") IssuanceCriteria criteria, @Param("page") PageRequest page);

    @Select({"<script>select count(1) from t_issuance t where t.deleted = 0 ",
            PAGE_CRITERIA,
            "</script>"})
    long countByQuery(@Param("query") IssuanceCriteria criteria);
}
