package com.juext.shop.base.storage.mapper;

import org.apache.ibatis.annotations.*;
import org.featx.spec.model.PageRequest;
import com.juext.shop.base.storage.entity.TransferEntity;
import com.juext.shop.base.storage.query.TransferCriteria;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:49
 */
@Mapper
public interface TransferMapper {

    String COLUMUS = "id, code, name, type, status, sort, image_url, comment, deleted, created_at, updated_at";

    @Insert({"insert into t_Transfer_module(code, name, type, status, sort, image_url, comment) ",
            "value(#{entity.code}, #{entity.name}, #{entity.type}, #{entity.status}, #{entity.sort}, ",
            "#{entity.image_url}, #{entity.comment})"})
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "entity.id", before = false, resultType = Long.class)
    void insert(@Param("entity") TransferEntity TransferEntity);

    @Update({"update t_Transfer_module set name = #{entity.name},",
            " type = #{entity.type}, status = #{entity.status},",
            " sort = #{entity.sort}, image_url = #{entity.imageUrl},",
            " comment = #{entity.comment}, ",
            " where code = #{entity.code} and deleted = 0"})
    void upsert(@Param("entity") TransferEntity TransferEntity);

    @Update({"<script>update t_Transfer_module set ",
            "<if test=\"entity.name != null and entity.name != ''\"> name = #{entity.name},</if>",
            "<if test=\"entity.type != null\"> type = #{entity.type},</if>",
            "<if test=\"entity.status != null\"> status = #{entity.status},</if>",
            "<if test=\"entity.sort != null\"> sort = #{entity.sort},</if>",
            "<if test=\"entity.imageUrl != null and entity.imageUrl != ''\"> image_url = #{entity.imageUrl}, </if>",
            "<if test=\"entity.comment != null and entity.comment != ''\"> comment = #{entity.comment}, </if>",
            " where code = #{entity.code} and deleted = 0",
            "</script> "})
//    @SelectKey(statement = "select updated_at from t_Transfer_module where id = (SELECT LAST_INSERT_ID())")
    void update(@Param("entity") TransferEntity TransferEntity);

    @Update({"update t_Transfer_module set deleted = #{deleted} where code = #{code}"})
    void delete(@Param("code") String code, @Param("deleted") Boolean delete);

    @Select({"select ", COLUMUS, " from t_Transfer_module where  deleted = 0 and code = #{code} limit 1"})
    TransferEntity selectByCode(@Param("code") String code);

    @Select({"<script>select ", COLUMUS, " from t_Transfer_module where deleted = 0 and code in ",
            "<foreach collection='codes' item='code' open='(' separator=',' close=')'>#{code}</foreach>",
            "</script>"})
    List<TransferEntity> selectByCodes(@Param("codes") List<String> codes);

    @Select({"select ", COLUMUS, " from t_Transfer_module where deleted = 0 ",
            "",
            "order by id limit #{page.offset}, #{page.size}"})
    List<TransferEntity> selectByPage(@Param("query") TransferCriteria criteria, @Param("page") PageRequest page);

    @Select({"select count(1) from t_Transfer_module where deleted = 0 ",
            ""})
    long countByQuery(@Param("query") TransferCriteria criteria);
}