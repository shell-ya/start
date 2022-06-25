package com.starnft.star.common.utils;

import javassist.*;
import javassist.bytecode.DuplicateMemberException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VirtualModelUtils {

    /**
     * 虚拟实体类编译路径
     */
    private static String className = "com.starnft.star.application.process.scope.model.VirtualModel";
    /**
     * 目标类
     */
    private Class<?> clazz;
    /**
     * 可编辑目标类
     */
    private CtClass cc;
    /**
     * 目标类成员变量数组
     */
    private String[] names;
    /**
     * 目标类成员变量值数组
     */
    private Object[] values;



    /**
     * 虚拟实体封装
     *
     * @param data      类的参数及值
     * @return java.lang.Object
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2021年11月23日 16:25:40
     */
    public Object virtualEntityEncapsulation(Map<String, String[]> data) {
        Set<String> set = data.keySet();
        names = set.toArray(new String[0]);
        values = new Object[names.length];
        int i = 0;
        for (String s : set) {
            values[i++] = data.get(s)[0];
        }
        return virtualEntityEncapsulation();
    }

    /**
     * 虚拟实体封装
     * @return 返回封装好的实体集合
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月18日 09:52:23
     */
    private Object virtualEntityEncapsulation() {
        try {
            /*初始化虚拟实体*/
            initializesVirtualEntity();
            /*封装数据*/
            return encapsulateData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化虚拟实体
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月18日 09:37:44
     */
    private void initializesVirtualEntity() throws Exception {
        // 初始化可编辑目标类
        getMyClass();
        List<String> lname = Arrays.asList(names);
        StringBuilder sb = new StringBuilder();
        sb.append("public String toString(){ return \"VirtualModel:");
        for (String s : lname) {
            try {
                changeClass(s);
                // 字段重复异常
            } catch (DuplicateMemberException e) {
                e.printStackTrace();
            }
            sb.append(s).append("=\"+").append(s).append(s.equals(lname.get(lname.size() - 1)) ? "" : "+\", ");
        }
        sb.append("; }");
        try {
            // 创建toString方法
            createMethod(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*重新加载修改后的类*/
        reload(cc);
    }

    /**
     * 创建属性及get\set方法
     * @param fieldName 属性名
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月17日 17:19:52
     */
    private void changeClass(String fieldName) throws CannotCompileException, NotFoundException, IOException {
        //为cTclass对象添加一个属性
        cc.addField(CtField.make("private String " + fieldName + ";", cc));
        createMethod("public void set" + fieldName + "(String " + fieldName + "){this." + fieldName + " = " + fieldName + ";}");
        createMethod("public String get" + fieldName + "(){return this." + fieldName + ";}");

    }

    /**
     * 封装数据
     * @return 返回封装好的实体集合
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月18日 09:38:11
     */
    private Object encapsulateData() throws Exception {
        int length = names.length;
        int i;
        Object o = clazz.newInstance();
        for (i = 0; i < length; i++) {
            Method m = clazz.getDeclaredMethod("set" + names[i], String.class);
            m.invoke(o, values[i].toString());
        }
        clazz=null;
        /*类重置*/
        resetClass();
        return o;
    }

    /**
     * 获取自定义虚拟类
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月17日 16:50:55
     */
    private void getMyClass() throws NotFoundException {
        // 类池
        ClassPool pool = ClassPool.getDefault();
        try {
            // 创建虚拟实体类
            cc = pool.makeClass(className);
        } catch (Exception e) {
            // 已经存在了
            cc = pool.get(className);
        }
        cc.defrost();// 解冻
    }

    /**
     * 重新加载修改后的类
     * @param cc 修改后的类
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月17日 17:00:00
     */
    private void reload(CtClass cc) throws IOException, CannotCompileException {
        try {
            // 转换成目标类对象
            clazz = cc.toClass();
        } catch (Exception ignored) {
            byte[] bytes = cc.toBytecode();
            // 使用自定义的ClassLoader
//            this.getClass().getClassLoader()
            VirtualModelClassLoader cl = new VirtualModelClassLoader();
            // 加载我们生成的 Ling 类
             clazz = cl.defineClass(className, bytes);
        }
    }

    /**
     * 重置类
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月17日 16:29:04
     */
    public void resetClass() throws Exception {
        // 初始化可编辑目标类
        getMyClass();
        CtMethod[] methods = cc.getDeclaredMethods();
        for (CtMethod method : methods) {
            /*删除本类所有方法*/
            cc.removeMethod(method);
        }
        CtField[] fields = cc.getDeclaredFields();
        for (CtField field : fields) {
            /*删除本类所有变量*/
            cc.removeField(field);
        }
        /*重新加载修改后的类*/
        reload(cc);
    }


    /**
     * 单独创建方法
     * @param m 具体方法结构
     * @return java.lang.Class<?>
     * <br><br><b>作者: 990130556 <a class=b href="https://blog.csdn.net/lingdu_dou">lingdu</a></b><br>
     * 创建时间: 2022年02月17日 10:50:05
     */
    public Class<?> createMethod(String m) throws NotFoundException, CannotCompileException, IOException {
        boolean b=false;
        if(cc==null){
            b=true;
            // 初始化可编辑目标类
            getMyClass();
        }
        cc.addMethod(CtMethod.make(m, cc));
        if(b){
            reload(cc);
            return clazz;
        }else {
            return null;
        }
    }
}
