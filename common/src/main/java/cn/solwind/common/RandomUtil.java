package cn.solwind.common;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 利用RSA算法生成伪随机数
 * 依据随机数生成种子产生对应的随机数，并保证当种子不同时产生的随机数也不相同。
 * 定义：
 * A：明文，在此处作为随机数种子，依据此数产生随机数；
 * B：密文，在此处作为产生的随机数；
 * p、q：两个素数，该算法的计算核心，此数越大则被猜出的可能越小；
 * N：pq的乘积，随机数的生成上限；
 * e：小于(p-1)×(q-1)的整数，在该算法中作为公钥，该数需要与(p-1)×(q-1)互质；
 * d：整数，在该算法中作为私钥，该数可通过解同余方程(d×e)%((p-1)×(q-1))=1计算。
 * 加密算法：
 * B=(A^e)%N
 * 解密算法：
 * A=(B^d)%N
 *
 * 由于我们是根据上述算法活动随机数，所以对加密的安全性要求并不是很高，可以取q、e为一个比较小的素数，方便计算。
 * 目前设定e=5，之所以这么取是因为p-1，q-1均为偶数，只要避开以1结尾的素数就可以保证e与(p-1)×(q-1)互质；
 * 设定q=5，之所以这么取是可以比较方便的计算出p的大致取数范围。
 *
 * 英数字随机数基础字典为去除了0,O,1,I后的英数字混合32位数组。
 * 素数种子的计算方法为
 * 1.根据要获取随机数位数，计算最大随机数范围。例：随机数6位，最大随机数范围为 32^6=1073741824
 * 2.使用最大范围除以q,此类q=5。例：1073741824/5=214748364.8，取整数214748364
 * 3.通过http://zh.numberempire.com/primenumbers.php查找小于除数结果的最近素数为素数种子。例：小于214748364的最近质数为214748357，即为素数种子.
 * （注意：为了满足互质条件，取得的素数种子不能以1结尾，如以1结尾，继续取前一个素数）
 *
 * 计算之后，随机数的最大取值范围变更为素数种子X5。例：6位随机数最大取值范围为：214748357X5=1073741785
 *
 * @author luning
 *
 */
public class RandomUtil {

    /*
     * 素数定义
     */
    // 素数种子Map
    private static Map<Integer, BigInteger> mapPrimeNumer;

    private static BigInteger primeNumer6 = new BigInteger("214748357");
    private static BigInteger primeNumer7 = new BigInteger("6871947653");
    private static BigInteger primeNumer8 = new BigInteger("219902325523");

    static {
        mapPrimeNumer = new HashMap<Integer, BigInteger>();
        mapPrimeNumer.put(6, primeNumer6);
        mapPrimeNumer.put(7, primeNumer7);
        mapPrimeNumer.put(8, primeNumer8);
    }

    /**
     * 生成随机数
     *
     * @param seed	种子
     * @param bits	位数
     * @param primeNumber	素数种子
     * @return
     */
    private static String generateRandomNum(long seed,int bits,BigInteger primeNumber) {
        // 被除数
        BigInteger dividend = BigDecimal.valueOf(seed).pow(5).toBigInteger();

        // 取得对应位数的素数
        if(primeNumber == null) {
            // 如果参数中未指明素数，则使用按照位数固定的素数
            primeNumber = mapPrimeNumer.get(bits);
        }
        // 计算除数
        BigInteger divisor = primeNumber.multiply(BigInteger.valueOf(5));

        // 返回格式
        StringBuffer sbFormat = new StringBuffer();
        for (int i = 0; i < bits; i++) {
            sbFormat.append("0");
        }
        DecimalFormat format = new DecimalFormat(sbFormat.toString());
        return format.format(dividend.remainder(divisor));
    }

    /**
     * 10进制转32进制
     *
     * @param numStr	随机数字
     * @param arrBase32 (去除0,O,1,I)	32位基础转换字典
     * @param length	长度
     * @return
     */
    private static String from10To32(String numStr, String[] arrBase32, int length){
        long to=32;
        long num = Long.parseLong(numStr);
        String jg="";
        while(num!=0){
            jg = arrBase32[new Long(num%to).intValue()] + jg;
            num=num/to;
        }
        if (jg.length() < length){
            int loop = length - jg.length();
            for (int i = 0; i < loop; i++){
                jg = arrBase32[0] + jg;
            }
        }
        return jg;
    }

    /**
     * 生成32位随机英数字字典(去除0,O,1,I)
     */
    public static void getRandomSeedArr() {
        // 32进制随机种子数组
        String[] arrTmp = {"2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};
        List<String> listTmp = new ArrayList<String>();
        for (int i = 0; i < arrTmp.length; i++) {
            listTmp.add(arrTmp[i]);
        }

        int intcount = listTmp.size();
        while (intcount > 0) {
            Random random = new Random();
            int intArr = random.nextInt(intcount);
            System.out.println(listTmp.get(intArr));
            listTmp.remove(intArr);
            intcount--;
        }
    }

    /**
     * 生成6位英数字混合随机码
     *
     * @param seed		随机种子，数字，最大值：1073741785
     * @param arrBase32		随机码基础32位英数字混合字典， (去除0,O,1,I)
     * @return
     */
    public static String generateRandomCode6(long seed,String[] arrBase32) {

        try {
            // 根据种子生成随机数
            String strRandomNum = generateRandomNum(seed, 6, null);
            // 根据随机数生成生成随机码
            return from10To32(strRandomNum, arrBase32, 6);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成7位英数字混合随机码
     *
     * @param seed		随机种子，数字，最大值：34359738265
     * @param arrBase32		随机码基础32位英数字混合字典， (去除0,O,1,I)
     * @return
     */
    public static String generateRandomCode7(long seed,String[] arrBase32) {

        try {
            // 根据种子生成随机数
            String strRandomNum = generateRandomNum(seed, 7, null);
            // 根据随机数生成生成随机码
            return from10To32(strRandomNum, arrBase32, 7);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成8位英数字混合随机码
     *
     * @param seed		随机种子，种子，最大值：1099511627615
     * @param arrBase32		随机码基础32位英数字混合字典， (去除0,O,1,I)
     * @return
     */
    public static String generateRandomCode8(long seed,String[] arrBase32) {

        try {
            // 根据种子生成随机数
            String strRandomNum = generateRandomNum(seed, 8, null);
            // 根据随机数生成生成随机码
            return from10To32(strRandomNum, arrBase32, 8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
