package demo.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 使用BaseEnum 指定枚举会映射的数据
 * @param <E> BaseEnum
 * @author jian.jiang
 * 处理枚举映射
 */
@MappedTypes(BaseEnum.class)
public class EnumTypeHandler<E extends Enum<E>&BaseEnum> extends BaseTypeHandler<E> {

    private final HashMap<String, E> eMap = new HashMap<>();

    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        for (E e : type.getEnumConstants()) {
            eMap.put(e.getValue(), e);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
            throws SQLException {
        if (jdbcType == null)
            ps.setString(i, parameter.getValue());
        else
            ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return eMap.get(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return eMap.get(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return eMap.get(cs.getString(columnIndex));
    }
}
