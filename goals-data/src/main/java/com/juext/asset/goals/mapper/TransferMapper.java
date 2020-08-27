package com.juext.asset.goals.mapper;

import com.juext.asset.goals.entity.TransferEntity;
import com.juext.asset.goals.query.TransferCriteria;
import org.apache.ibatis.annotations.*;
import org.featx.spec.model.PageRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:49
 */
@Mapper
public interface TransferMapper {

    String COLUMNS = "t.id, t.code, t.name, t.type, t.from_account_code, t.to_account_code, t.amount, t.matter," +
            " t.reference, t.reference_code, t.comment, t.deleted, t.created_at, t.updated_at";
    String PAGE_CRITERIA = "<if test=\"query.id != null\">and t.id = #{query.id}</if>" +
            "<if test=\"query.code != null\">and t.code = #{query.code}</if>" +
            "<if test=\"query.name != null\">and t.name = #{query.name}</if>" +
            "<if test=\"query.type != null\">and t.type = #{query.type}</if>" +
            "<if test=\"query.amountMax != null\">and t.amount &lt;= #{query.amountMax}</if>" +
            "<if test=\"query.amountMin != null\">and t.amount &gt;= #{query.amountMin}</if>" +
            "<if test=\"query.reference != null\">and t.reference = #{query.reference}</if>" +
            "<if test=\"query.referenceCode != null\">and t.reference_code = #{query.referenceCode}</if>" +
            "<if test=\"query.matter != null\">and t.matter = #{query.matter}</if>" +
            "<if test=\"query.fromAccountCode != null\">and t.from_account_code = #{query.fromAccountCode}</if>" +
            "<if test=\"query.toAccountCode != null\">and t.to_account_code = #{query.toAccountCode}</if>" +
            "<if test=\"query.comment != null\">and t.comment = #{query.comment}</if>";

    @Insert({"insert into t_transfer(code, name, type, from_account_code, to_account_code, amount, matter, ",
            "reference, reference_code, comment) ",
            "values(#{entity.code}, #{entity.name}, #{entity.type}, #{entity.fromAccountCode}, #{entity.toAccountCode}, ",
            "#{entity.amount}, #{entity.matter}, #{entity.reference}, #{entity.referenceCode}, #{entity.comment})"})
    @SelectKey(keyProperty = "entity.id", before = false, resultType = Long.class,
            statement = "select t.id from t_transfer t where t.code=#{entity.code}")
    int insert(@Param("entity") TransferEntity transferEntity);

    @Update({"update t_transfer set deleted = #{deleted} where code = #{code}"})
    int delete(@Param("code") String code, @Param("deleted") Boolean delete);

    @Select({"select", COLUMNS, "from t_transfer t where t.deleted = 0 and t.code = #{code} limit 1"})
    TransferEntity selectByCode(@Param("code") String code);

    @Select({"<script>select", COLUMNS, "from t_transfer t where t.deleted = 0 and t.code in",
            "<foreach collection='codes' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "</script>"})
    List<TransferEntity> selectByCodes(@Param("codes") List<String> codes);

    @Select({"<script>select", COLUMNS, "from t_transfer t where t.deleted = 0 ",
            "and t.reference=#{ref} and t.reference_code=#{refCode}</script>"})
    TransferEntity selectByReference(@Param("ref")String ref, @Param("refCode") String refCode);

    @Select({"<script>select", COLUMNS, "from t_transfer t where t.deleted = 0 ",
            PAGE_CRITERIA, "order by t.id limit #{page.offset}, #{page.size}</script>"})
    List<TransferEntity> selectByPage(@Param("query") TransferCriteria criteria, @Param("page") PageRequest page);

    @Select({"<script>select count(1) from t_transfer t where t.deleted = 0 ",
            PAGE_CRITERIA, "</script>"})
    long countByQuery(@Param("query") TransferCriteria criteria);
}
