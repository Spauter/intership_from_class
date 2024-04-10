package com.yc.demo.lexicon;
/**
 *  利用滚动数组优化过的最小编辑距离算法。空间复杂度为O(2×min(lenSrc,lenDst))
 * @return
 */
public class LevenshteinDistance {
    public static int distance(String src,String dst){
        int lenSrc = src.length() + 1;
        int lenDst = dst.length() + 1;

        CharSequence newSrc = src;
        CharSequence newDst = dst;
        //如果src长度比dst的短，表示数组的列数更多，此时我们
        //交换二者的位置，使得数组的列数变为较小的值。
        if (lenSrc < lenDst){
            newSrc = dst;
            newDst = src;
            int temp = lenDst;
            lenDst = lenSrc;
            lenSrc = temp;
        }

        //创建滚动数组，此时列数为lenDst，是最小的
        int[] cost = new int[lenDst];   //当前行依赖的上一行数据
        int[] newCost = new int[lenDst];//当前行正在修改的数据

        //对第一行进行初始化
        for(int i = 0;i < lenDst;i++)
            cost[i] = i;

        for(int i = 1;i < lenSrc;i++){
            //对第一列进行初始化
            newCost[0] = i;

            for(int j = 1;j < lenDst;j++){
                int flag = (newDst.charAt(j - 1) == newSrc.charAt(i - 1)) ? 0 : 1;

                int cost_insert = cost[j] + 1;        //表示“上面”的数据，即对应d(i - 1,j)
                int cost_replace = cost[j - 1] + flag;//表示“左上方的数据”，即对应d(i - 1,j - 1)
                int cost_delete = newCost[j - 1] + 1; //表示“左边的数据”，对应d(i,j - 1)

                newCost[j] = minimum(cost_insert,cost_replace,cost_delete); //对应d(i,j)
            }

            //把当前行的数据交换到上一行内
            int[] temp = cost;
            cost = newCost;
            newCost = temp;
        }

        return cost[lenDst - 1];
    }

    private static int minimum(int a,int b,int c){
        return Math.min(Math.min(a,b),c);
    }
}
