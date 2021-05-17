package cn.solwind.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 查询相关工具类
 *
 * @author zln
 */
public class SqlUtil {

    /**
     * 如果查询参数为空字符串，返回null
     * 如果不为空，则trim后返回
     *
     * @param param
     * @return
     */
    public static String clearEmptyStr(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        } else {
            return param.trim();
        }
    }

    /**
     * 获取全模糊查询条件
     * %param%
     *
     * @param param
     * @return
     */
    public static String getLikeParam(String param) {

        if (StringUtils.isNotBlank(param)) {
            return "%" + param + "%";
        } else {
            return null;
        }
    }

    /**
     * 获取左模糊查询条件
     * %param
     *
     * @param param
     * @return
     */
    public static String getLLikeParam(String param) {
        if (StringUtils.isNotBlank(param)) {
            return "%" + param;
        } else {
            return null;
        }
    }

    /**
     * 获取右模糊查询条件
     * param%
     *
     * @param param
     * @return
     */
    public static String getRLikeParam(String param) {
        if (StringUtils.isNotBlank(param)) {
            return param + "%";
        } else {
            return null;
        }
    }


}
