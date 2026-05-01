package com.github.houbb.heaven.response.respcode.impl;

import com.github.houbb.heaven.response.respcode.RespCode;

/* JADX INFO: loaded from: classes3.dex */
public enum CommonRespCode implements RespCode {
    SUCCESS("00000", "成功"),
    FAIL("00001", "失败"),
    TIME_OUT("00002", "超时"),
    CEHCK_PARAM_FAIL("10001", "参数校验失败"),
    CEHCK_CHECKSUM_FAIL("10002", "签名校验失败"),
    DATABASE_INSERT_FAIL("20001", "数据库插入失败"),
    DATABASE_UPDATE_FAIL("20002", "数据库更新失败"),
    DATABASE_DELETE_FAIL("20003", "数据库删除失败"),
    DATABASE_SELECT_FAIL("20004", "数据库查询失败"),
    FILE_NOT_EXISTS("30001", "指定文件不存在"),
    STREAM_HAS_BEEN_CLOSED("31001", "流已经关闭"),
    CHARSET_NOT_EXISTS("32001", "指定编码不存在"),
    REFLECT_NEW_INSTANCE_FAIL("40001", "反射新建对象失败");

    private final String code;
    private final String msg;

    CommonRespCode(String str, String str2) {
        this.code = str;
        this.msg = str2;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getCode() {
        return this.code;
    }

    @Override // com.github.houbb.heaven.response.respcode.RespCode
    public String getMsg() {
        return this.msg;
    }
}
