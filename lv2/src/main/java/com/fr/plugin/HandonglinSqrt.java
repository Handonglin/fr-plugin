package com.fr.plugin;


import com.fr.general.GeneralUtils;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.script.AbstractFunction;
import com.fr.stable.ArrayUtils;
import com.fr.stable.Primitive;
import com.fr.stable.exception.FormulaException;
import com.fr.third.ibm.icu.text.DecimalFormat;


/**
 * @author Handonglin
 * @create 2022-12-05 10:49
 */

@EnableMetrics
public class HandonglinSqrt extends AbstractFunction{
    //精确到小数点后4位
    public static final double PRECISION = 0.0001;

    @Override
    @Focus(id = "com.fr.plugin.function.lv2", text = "", source = Original.PLUGIN)
    public Object run(Object[] args) throws FormulaException {
        int len = ArrayUtils.getLength(args);
        //不是一个数报错
        if (len!=1) {
            return Primitive.ERROR_VALUE;
        }

        double num = GeneralUtils.objectToNumber(args[0]).doubleValue();
        //负数报错
        if (num < 0) {
            return Primitive.ERROR_VALUE;
        }
        //特殊0返回
        if (num==0){
            return "0.0000";
        }

        double ret = Sqrt(num);
        DecimalFormat df = new DecimalFormat("#.0000");

        return df.format(ret);
    }

    // 使用牛顿迭代法求平方根
    private double getSqrt(double num) {
        double curValue = 1.0;
        double checkValue = 0.0;

        do {
            curValue = (num / curValue + curValue) / 2.0;
            checkValue = curValue * curValue - num;
        } while ((checkValue >= 0 ? checkValue : -checkValue) > PRECISION);

        return curValue;
    }
    private double Sqrt(double input){
        double x = 1;
        double x1 = x - (x*x - input)/(2*x);
        while(x - x1 > PRECISION || x - x1 < -PRECISION){
            x = x1;
            x1 = x - (x*x - input)/(2*x);
        }
        return x1;
    }
}
