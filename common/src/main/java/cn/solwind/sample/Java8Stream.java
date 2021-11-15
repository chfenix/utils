package cn.solwind.sample;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.velocity.test.provider.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8Stream {

    public static void main(String[] args) throws Exception {
        List<Java8StreamDTO> listDTO = init();

        /**
         * 提取DTO某一属性，并去重
         */
        System.out.println("提取DTO某一属性");
        System.out.println(listDTO.stream().map(e ->e.getGroupKey()).distinct().collect(Collectors.toList()));
        System.out.println();

        /**
         * 按照某一属性汇总DTO，Map-List
         */
        System.out.println("按照某一属性汇总DTO");
        Map<String, List<Java8StreamDTO>> listMap = listDTO.stream().collect(Collectors.groupingBy(Java8StreamDTO::getGroupKey));
        System.out.println(listMap);
        System.out.println();

        /**
         * 按照某一属性汇总合并DTO，返回合并后List，复合key group可以使用自定义方法获取复合key
         */
        System.out.println("按照某一属性汇总");
        List<Java8StreamDTO> listGroupDTO = listDTO.stream()
                .collect(Collectors.toMap(k -> groupKey(k), a -> a, (o1, o2) -> {
                    o1.setAmount(o1.getAmount().add(o2.getAmount()));
                    o1.setBeginDate(o1.getBeginDate().isAfter(o2.getBeginDate()) ? o2.getBeginDate() : o1.getBeginDate());
                    o1.setEndDate(o1.getEndDate().isBefore(o2.getEndDate()) ? o2.getEndDate() : o1.getEndDate());
                    o1.setId(o1.getId() + " - " + o2.getId());
                    return o1;
                })).values().stream().collect(Collectors.toList());
        System.out.println(listGroupDTO);
        System.out.println();
        listDTO = init();

        /**
         * 按照某一属性过滤
         */
        System.out.println("按照某一属性过滤");
        System.out.println(listDTO.stream().filter(t -> t.getBeginDate().isAfter(LocalDate.of(2021,4,15))).collect(Collectors.toList()));
        System.out.println();

        /**
         * 按照某一属性转Map，重复的覆盖
         */
        System.out.println("按照某一属性转Map，重复的保留第一个，可以修改k1,k2更改保留逻辑");
        Map<String, Java8StreamDTO> listOneMap = listDTO.stream().collect(Collectors.toMap(Java8StreamDTO::getId, a -> a, (k1, k2) -> k1));
        System.out.println(listOneMap);
        System.out.println();

        /**
         * 按照某几个属性组合作为Key转Map，重复的覆盖
         */
        System.out.println("按照某几个属性组合作为Key转Map，重复的保留第一个");
        Map<String, Java8StreamDTO> listComboKeyOneMap = listDTO.stream().collect(Collectors.toMap(k -> k.getId() + "_" + k.getGroupKey() + "_" + k.getBeginDate(), a -> a, (k1, k2) -> k1));
        System.out.println(listComboKeyOneMap);
        System.out.println();

        /**
         * 按照某个属性转Map,Map Value为某个属性
         */
        System.out.println("按照某个属性转Map,Map Value为某个属性");
        Map<String, BigDecimal> mapProToMap = listDTO.stream().collect(Collectors.toMap(Java8StreamDTO::getId, Java8StreamDTO::getAmount));
        System.out.println(mapProToMap);
        System.out.println();

        /**
         * 某属性求和
         */
        System.out.println("某属性求和");
        BigDecimal totalRentArea = listDTO.stream().map(Java8StreamDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalRentArea);
        System.out.println();

        /**
         * 分组求和1
         */
        System.out.println("分组求和1");
        Map<Optional<String>,BigDecimal> mapGroupSum1 = listDTO.stream().collect(
                        Collectors.groupingBy(
                                dto -> Optional.ofNullable(dto.getGroupKey()),
                                Collectors.reducing(BigDecimal.ZERO, Java8StreamDTO::getAmount, BigDecimal::add)));
        System.out.println(mapGroupSum1);
        System.out.println();

        System.out.println("分组求和2");
        Map<String,BigDecimal> mapGroupSum2 = listDTO.stream().collect(
                Collectors.groupingBy(
                        Java8StreamDTO::getGroupKey,
                        Collectors.reducing(BigDecimal.ZERO, Java8StreamDTO::getAmount, BigDecimal::add)));
        System.out.println(mapGroupSum2);
        System.out.println();

        /**
         * 分组取最大值
         */
        System.out.println("分组取最大值");
        Map<String, Java8StreamDTO> mapGroupMax = listDTO.stream()
                .collect(Collectors.toMap(Java8StreamDTO::getGroupKey, Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Java8StreamDTO::getBeginDate))));
        System.out.println(mapGroupMax);
        System.out.println();

        /**
         * 获取最大日期
         */
        System.out.println("获取最大日期");
        LocalDate maxBeginDate = listDTO.stream()
                .filter(o -> o.getBeginDate() != null)
                .map(Java8StreamDTO::getBeginDate)
                .distinct().max((e1, e2) -> e1.compareTo(e2)).get();
        System.out.println(maxBeginDate);
        System.out.println();

        /**
         * 计数
         */
        System.out.println("计数");
        Map<String,Long> mapGroupCount =
            listDTO.stream().collect(Collectors.groupingBy(Java8StreamDTO::getGroupKey,Collectors.counting()));
        System.out.println(mapGroupCount);
        System.out.println();


    }

    private static List<Java8StreamDTO> init() {
        Object[][] initData = new Object[][]{
                {"1", new BigDecimal(1), LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 31), "1"},
                {"2", new BigDecimal(2), LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 28), "2"},
                {"3", new BigDecimal(3), LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 31), "1"},
                {"4", new BigDecimal(4), LocalDate.of(2021, 4, 1), LocalDate.of(2021, 4, 30), "2"},
                {"5", new BigDecimal(5), LocalDate.of(2021, 5, 1), LocalDate.of(2021, 5, 31), "1"},
                {"6", new BigDecimal(6), LocalDate.of(2021, 6, 1), LocalDate.of(2021, 6, 30), "2"},
                {"7", new BigDecimal(7), LocalDate.of(2021, 6, 1), LocalDate.of(2021, 6, 30), "1"}
        };

        List<Java8StreamDTO> listDTO = new ArrayList<>();
        for (Object[] initDatum : initData) {
            listDTO.add(new Java8StreamDTO(initDatum));
        }

        return listDTO;
    }

    private static String groupKey(Java8StreamDTO java8StreamDTO) {
        return java8StreamDTO.getGroupKey();
    }
}
