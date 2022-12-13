package com.fr.plugin;

import com.fr.general.GeneralUtils;
import com.fr.intelli.record.Focus;
import com.fr.intelli.record.Original;
import com.fr.record.analyzer.EnableMetrics;
import com.fr.script.AbstractFunction;
import com.fr.stable.ArrayUtils;
import com.fr.stable.Primitive;
import com.fr.stable.exception.FormulaException;


/**
 * @author Handonglin
 * @create 2022-12-05 10:51
 */
@EnableMetrics
public class HandonglinFib extends AbstractFunction {
    private long [][] a = new long [4][4];
    private long [][] t = new long [4][4];
    private long [][] res = new long [4][4];

    @Override
    @Focus(id = "com.fr.plugin.function.lv2", text = "", source = Original.PLUGIN)
    public Object run(Object[] args) throws FormulaException {
        int len = ArrayUtils.getLength(args);
        //不是一个数报错
        if (len!=1) {
            return Primitive.ERROR_VALUE;
        }
        int num = GeneralUtils.objectToNumber(args[0]).intValue();
        //负数报错
        if (num <=0) {
            return Primitive.ERROR_VALUE;
        }

        if (num < 3) {
            return num - 1;
        }

        return Fib(num);
    }
    private long Fib(int num) {
        //初始矩阵
        a[1][1] = 1;
        a[1][2] = 0;
        a[2][1] = 0;
        a[2][2] = 0;

        //转移矩阵初始化
        t[1][1] = 1;
        t[1][2] = 1;
        t[2][1] = 1;
        t[2][2] = 0;

        pow(num - 1);
        mul(res, a);
        return res[1][1];
    }


    private void mul(long[][] a, long[][] b) {
        long[][] temp = new long[5][5];
        for (int i = 1; i <= 2; i ++) {
            for (int j = 1; j <= 2; j ++) {
                for (int k = 1; k <= 2; k ++) {
                    temp[i][j] += (a[i][k] * b[k][j]) % 10000;
                }
            }
        }
        for (int i = 1; i <= 2; i ++) {
            for (int j = 1; j <= 2; j ++) {
                a[i][j] = temp[i][j];
            }
        }
    }

    private void pow(int n) {
        for (int i = 1; i <= 2; i ++) {
            res[i][i] = 1;
        }
        while (n > 0) {
            if ((n & 1) != 0) {
                mul(res, t);
            }
            mul(t, t);
            n >>= 1;
        }
    }
}
