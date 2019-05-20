package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Auther: lss
 * @Date: 2019/4/21 18:59
 * @Description:
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    CATEGORY_NOT_FOUND(404, "商品分类没查到"),
    SPEC_GROUP_NOT_FOUND(404, "商品规格组不存在"),
    SPEC_PARAM_NOT_FOUND(404, "商品规格参数不存在"),
    GOODS_NOT_FOUND(404, "商品不存在"),
    GOODS_SAVE_ERROR(500, "新增商品失败"),
    GOODS_UPDATE_ERROR(500, "更新商品失败"),
    GOODS_DETAIL_NOT_FOUND(404, "商品详情没查到"),
    GOODS_SKU_NOT_FOUND(404, "商品 SKU 没查到"),
    GOODS_ID_CANNOT_BE_NULL(400, "商品 id 不能为空"),
    GOODS_STOCK_NOT_FOUND(404, "商品库存没查到"),
    GOODS_NOT_SALEABLE(400, "商品未上架"),
    BRAND_NOT_FOUND(404, "品牌没查到"),
    BRAND_SAVE_ERROR(500, "新增品牌失败"),
    SPEC_GROUP_SAVE_ERROR(500, "新增商品规格组失败"),
    SPEC_GROUP_UPDATE_ERROR(500, "修改商品规格组失败"),
    SPEC_GROUP_DELETE_ERROR(500, "删除商品规格组失败"),
    SPEC_PARAM_SAVE_ERROR(500, "新增商品规格参数失败"),
    SPEC_PARAM_UPDATE_ERROR(500, "修改商品规格参数失败"),
    SPEC_PARAM_DELETE_ERROR(500, "删除商品规格参数失败"),
    UPLOAD_FILE_ERROR(500, "文件上传失败"),
    INVALID_FILE_TYPE(500, "无效的文件类型"),
    INVALID_USER_DATA_TYPE(400, "用户数据类型无效"),
    INVALID_USERNAME_PASSWORD(400, "用户名或密码错误"),
    INVALID_VERIFY_CODE(400, "无效的验证码"),
    CREATE_TOKEN_ERROR(500, "用户凭证生成失败"),
    UNAUTHORIZED(403, "未授权"),
    CART_NOT_FOUND(404, "购物车为空"),
    ORDER_NOT_FOUND(404, "订单不存在"),
    ORDER_DETAIL_NOT_FOUND(404, "订单详情不存在"),
    ORDER_STATUS_NOT_FOUND(404, "订单状态不存在"),
    CREATE_ORDER_ERROR(500, "创建订单失败"),
    STOCK_NOT_ENOUGH(500, "库存不足"),
    WX_PAY_ORDER_FAIL(500, "微信下单失败"),
    ORDER_STATUS_ERROR(400, "订单状态不正确"),
    ;

    private int code;
    private String msg;

}
