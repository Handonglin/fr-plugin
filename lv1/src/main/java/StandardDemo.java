import com.fr.log.FineLoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
 
public class StandardDemo {
 
    private String name;
    private boolean light;
    private int age;

    //1.常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长
    public static final int warning_age = 12;


    //2.构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在 init 方法中。
    public StandardDemo(String name) {
        this.name = name;
    }
    public StandardDemo(String name, boolean light) {
        this.name = name;
        this.light = light;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        //3.如果需要使用空字符串，不要直接使用""，用StringUtils.EMPTY
        //4.不要向构造函数和公共方法中传递null参数，null参数很难直接从方法声明上去理解该参数的意义。
        //5.Object 的 equals 方法容易抛空指针异常，应使用常量或确定有值的对象来调用 equals，
        // 在不确定的情况下，需要使用工具方法ComparatorUtils#equals进行对象比较。
        if (name == null || name.equals("")) throw new IllegalArgumentException("参数值不能为空！");    // 5 判断字符串是否为空，使用StringUtils.isEmpty(name);且出现中文要做国际化处理
        this.name = name;
    }

    //6用于设置对象属性的方法前缀必须是set，用于获取一个布尔对象属性的方法前缀必须是is/has，
    // 而用于获取其他类型属性的方法前缀必须是get。改为isLight()
    public boolean getLight() {
        return light;
    }
 
    public void setLight(boolean light) {
        this.light = light;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        //7.代码中出现中文要用国际化处理
        if (age < warning_age) throw new IllegalArgumentException("不合理的参数范围！");
        this.age = age;
    }
 
    public void print() {
        //8.在写DEBUG级别的日志的时，需要先判断是否启用了DEBUG级别的日志信息，避免无意义的日志信息计算
        //9.写INFO和DEBUG级别的日志时，不要拼接字符串，使用工具类提供的格式化方法
        FineLoggerFactory.getLogger().debug("age is " + age);

        //10.这里很奇怪，这个函数是用来打印日志的，日志不是可以设置打印到控制台或者文件里吗
        //打印内容和上面的一样，这里我认为多余了
        System.out.println("age:" + age);
    }
 
    public static StandardDemo loadFromObject(Object content) {
        //11.将一个对象转化为字符串，不能直接使用toString方法，需要使用GeneralUtils#objectToString(Object)方法，
        // 可改为：GeneralUtils.objectToString(content);
        return new StandardDemo(content.toString());
    }
 
    public static void main(String... args) throws Exception{
        //12.如果自定义对象做为Map的键，那么必须重写hashCode和equals,该类中未重写这两个方法
        Map<StandardDemo, Object> map = new HashMap<StandardDemo, Object>();
        map.put(new StandardDemo("X"), 1);
        map.put(new StandardDemo("Y"), 2);
        // 同上，因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的对象必须重写这两个方法
        Set<StandardDemo> keys = map.keySet();

        //13.在集合（List、Set、Map等）是否为空的是否，不允许使用 list.size() == 0的方式来判断，必须使用 list.isEmpty()，这里使用：keys.isEmpty()
        if (keys.size() == 0) {
            throw new Exception("Error map size");
        }
        //14.使用集合转数组的方法，必须使用集合的toArray(T[] array)，传入的是类型完全 一样的数组，大小为0即可，
        // 改为：keys.toArray(new StandardDemo[0]);
        StandardDemo[] keyArr = (StandardDemo[]) keys.toArray();
        for (StandardDemo demo : keyArr) {
            if (demo.getAge() < 10) {
                System.out.println("No good age!");
            }
        }
    }
}